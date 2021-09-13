package com.meli.couponapi.models;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Wilson Forero Rocha
 *
 */
public class CouponRequest {

    @JsonProperty("item_ids")
    @Valid
    @NotNull
    private List<String> itemsIds;

    @Min(1)
    @NotNull
    private Float amount;

    public CouponRequest() {
    }

    public CouponRequest(List<String> itemsIds, Float amount) {
        this.itemsIds = itemsIds;
        this.amount = amount;
    }

    public List<String> getItemsIds() {
        return itemsIds;
    }

    public void setItemsIds(List<String> itemsIds) {
        this.itemsIds = itemsIds;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }


    
    

}
