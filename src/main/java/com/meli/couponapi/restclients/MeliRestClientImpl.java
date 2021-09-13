package com.meli.couponapi.restclients;

import com.meli.couponapi.exceptions.MeliApiException;
import com.meli.couponapi.models.MeliApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


/**
 * 
 * @author Wilson Forero Rocha
 *
 */
@Service
public class MeliRestClientImpl implements MeliRestClient {
	
	RestTemplate restTemplate;		
	
	@Autowired
	public MeliRestClientImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


	@Override
	public Float getPriceByItem(String itemId) {				 
		
		if(itemId == null) {
			throw new MeliApiException("ItemId is NUll", null);
		}

		try {
			String meliApiUrl = "https://api.mercadolibre.com/items";
			ResponseEntity<MeliApiResponse> response = restTemplate.getForEntity(meliApiUrl + "/" + itemId,
					MeliApiResponse.class);
			return response.getBody().getPrice();
		} catch (HttpStatusCodeException e) {
			throw new MeliApiException("Error in Melis Api", e.getStatusCode());
		}
	}

}
