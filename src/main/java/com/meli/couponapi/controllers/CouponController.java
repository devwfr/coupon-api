package com.meli.couponapi.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.meli.couponapi.exceptions.MeliApiException;
import com.meli.couponapi.models.CouponRequest;
import com.meli.couponapi.models.CouponResponse;
import com.meli.couponapi.models.Item;
import com.meli.couponapi.models.ItemSet;
import com.meli.couponapi.restclients.MeliRestClientImpl;
import com.meli.couponapi.services.CouponService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Wilson Forero Rocha
 *
 */

@RestController
@RequestMapping("/api/v1/coupon/")  
@Validated
public class CouponController {

    CouponService couponService;
    MeliRestClientImpl meliApiClient;

    @Autowired
    public CouponController(CouponService couponService,MeliRestClientImpl meliApiClient) {
        this.couponService = couponService;
        this.meliApiClient = meliApiClient;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> getCouponscalculateCoupon(@Valid @RequestBody CouponRequest couponReq){
    	   	        
        Map<String,Float> favoriteItems = new HashMap<String,Float>(); 

        Float amount=couponReq.getAmount();

        for (String itemId : couponReq.getItemsIds()) {        	
            try {
				favoriteItems.put(itemId, meliApiClient.getPriceByItem(itemId));
			} catch (MeliApiException e) {				
				continue;
			}            
        }
        
        if( favoriteItems.isEmpty() ) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        
        if(couponService.isEnabledToBuy(favoriteItems, amount)){
            ItemSet itemSet = couponService.getItemsToBuy(favoriteItems,amount) ;
            CouponResponse response = new CouponResponse();
            response.setTotal(itemSet.getPrice());
    
            for (Item item : itemSet.getItems()) {
                response.addFavoriteItem(item.getDescription());
            }
    
            Collections.sort(response.getFavoriteItems());
    
            return new ResponseEntity<CouponResponse>(response,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }     
    }
    
}
