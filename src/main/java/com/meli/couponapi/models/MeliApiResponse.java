package com.meli.couponapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 
 * @author Wilson Forero Rocha
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeliApiResponse {

    private String id;

    private Float price;
    
    public MeliApiResponse() {
    }

    public MeliApiResponse(String id, Float price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MeliApiResponse [id=" + id + ", price=" + price + "]";
    }
    
    

}
