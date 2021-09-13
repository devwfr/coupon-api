package com.meli.couponapi.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * 
 * @author Wilson Forero Rocha
 *
 */
@JsonPropertyOrder({"favoriteItems", "total" })
public class CouponResponse {

    private Float total;

    @JsonProperty("item_ids")
    private List<String> favoriteItems;

    public CouponResponse() {
        favoriteItems = new ArrayList<String>();
    }

    public CouponResponse(Float total, List<String> favoriteItems) {
        this.total = total;
        this.favoriteItems = favoriteItems;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public List<String> getFavoriteItems() {
        return favoriteItems;
    }

    public void setFavoriteItems(List<String> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    public void addFavoriteItem(String item) {
        favoriteItems.add(item);
    }

	@Override
	public int hashCode() {
		return Objects.hash(favoriteItems, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CouponResponse other = (CouponResponse) obj;
		return Objects.equals(favoriteItems, other.favoriteItems) && Objects.equals(total, other.total);
	}
    
    

}
