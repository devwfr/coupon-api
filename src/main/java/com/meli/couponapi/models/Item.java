package com.meli.couponapi.models;

import java.util.Objects;

/**
 * 
 * @author Wilson Forero Rocha
 *
 */
public class Item {
    String description;
    Float price;


    public Item(String description, Float price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }   

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item [description=" + description + ", price=" + price + "]";
    }

	@Override
	public int hashCode() {
		return Objects.hash(description, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(description, other.description) && Objects.equals(price, other.price);
	}
    
    
}
