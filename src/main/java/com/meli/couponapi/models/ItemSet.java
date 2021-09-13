package com.meli.couponapi.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Wilson Forero Rocha
 *
 */
public class ItemSet implements Comparable<ItemSet> {

    @JsonProperty("item_ids")
    private List<Item> items;

    @JsonProperty("total")
    private Float price;

    public ItemSet() {
        items = new ArrayList<>();
    }

    public ItemSet(List<Item> items, Float price) {
        this.items = items;
        this.price = price;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * This method is based on the backtracking algorithm, an answer found in stackoverflow 
     * about the Knapsack problem was used as a basis, and it was modified to meet the required need
     * 
     * @param itemList
     * @param amount
     * @param combination
     * @param bestItemsCombinations
     * @param accumulatedBestAmounts
     * 
     * @see https://stackoverflow.com/a/58443376
     * 
     * @return HashMap with the best items combination 
     */
    public static Map<Float, List<Item>> findBestsItemsSubSets(List<Item> itemList, Float amount,
            List<Item> combination, Map<Float, List<Item>> bestItemsCombinations,
            ArrayList<Float> accumulatedBestAmounts) {

        Float pricesSum = 0f;
        Float bestAmmount = 0f;

        if (!accumulatedBestAmounts.isEmpty()) {
            Collections.sort(accumulatedBestAmounts, Collections.reverseOrder());
            bestAmmount = accumulatedBestAmounts.get(0);
        }

        for (Item item : combination) {
            pricesSum += item.getPrice();
        }
        
        if (pricesSum <= amount && pricesSum != 0) {
            if (pricesSum > bestAmmount) {
                bestItemsCombinations.put(pricesSum, combination);
                accumulatedBestAmounts.add(pricesSum);
            }
        }

        for (int i = 0; i < itemList.size(); i++) {
            List<Item> remainingItems = new ArrayList<>();
            Item pointingItem = itemList.get(i);

            for (int j = i + 1; j < itemList.size(); j++) {
                remainingItems.add(itemList.get(j));
            }

            List<Item> combinationRecord = new ArrayList<>(combination);
            combinationRecord.add(pointingItem);
            Map<Float, List<Item>> retrievedItemsCombination = findBestsItemsSubSets(remainingItems, amount,
                    combinationRecord, bestItemsCombinations, accumulatedBestAmounts);
            bestItemsCombinations = retrievedItemsCombination;
        }
        return bestItemsCombinations;
    }

    @Override
    public int compareTo(ItemSet o) {
        return this.getPrice().compareTo(o.getPrice());
    }

	@Override
	public int hashCode() {
		return Objects.hash(items, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemSet other = (ItemSet) obj;
		return Objects.equals(items, other.items) && Objects.equals(price, other.price);
	}

	@Override
	public String toString() {
		return "ItemSet [items=" + items + ", price=" + price + "]";
	}
        

}
