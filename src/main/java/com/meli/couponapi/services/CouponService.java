package com.meli.couponapi.services;

import java.util.List;
import java.util.Map;

import com.meli.couponapi.models.ItemSet;

/**
 * 
 * @author Wilson Forero Rocha
 *
 */
public interface CouponService {
    
    public List<String> calculate(Map<String, Float> items, Float amount);

    public ItemSet getItemsToBuy(Map<String, Float> items, Float amount);

    public boolean isEnabledToBuy(Map<String, Float> items, Float amount);

}
