package com.meli.couponapi.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meli.couponapi.models.Item;
import com.meli.couponapi.models.ItemSet;

import org.springframework.stereotype.Service;
/**
 * 
 * @author Wilson Forero Rocha
 * 
 *
 */
@Service
public class CouponServiceImpl implements CouponService {	

	
    @Override
    public List<String> calculate(Map<String, Float> items, Float amount) {

        List<String> itemsToBuy = new ArrayList<>();

        ItemSet itemSet = this.getItemsToBuy(items, amount);

        for (Item item : itemSet.getItems()) {  
            itemsToBuy.add(item.getDescription());
        }

        Collections.sort(itemsToBuy);

        return itemsToBuy;
    }

    @Override
    public ItemSet getItemsToBuy(Map<String, Float> favoritesItems, Float amount) {
        ItemSet itemSet = new ItemSet();

        List<Item> itemList = new ArrayList<>();

        for (Map.Entry<String, Float> entry : favoritesItems.entrySet()) {
            Item item = new Item(entry.getKey(), entry.getValue());
            itemList.add(item);
        }

        Map<Float, List<Item>> qualifyItemsCombination;

        qualifyItemsCombination = ItemSet.findBestsItemsSubSets(itemList, amount, new ArrayList<Item>(),
                new HashMap<>(), new ArrayList<Float>());

        List<ItemSet> qualifyItemsCombinationList = new ArrayList<>();

        if (!qualifyItemsCombination.entrySet().isEmpty()) {
            for (Map.Entry<Float, List<Item>> entry : qualifyItemsCombination.entrySet()) {
                ItemSet is = new ItemSet();
                is.setPrice(entry.getKey());
                is.setItems(entry.getValue());
                qualifyItemsCombinationList.add(is);
            }

            if (qualifyItemsCombinationList.size() > 0) {
                Collections.sort(qualifyItemsCombinationList, Collections.reverseOrder());
                itemSet.setItems(qualifyItemsCombinationList.get(0).getItems());
                itemSet.setPrice(qualifyItemsCombinationList.get(0).getPrice());
            }
        }
        return itemSet;
    }

    @Override
    public boolean isEnabledToBuy(Map<String, Float> items, Float amount) {

        Float maxPrice = Collections.max(items.values());

        return maxPrice < amount;
    }

}
