package com.meli.couponapi.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.meli.couponapi.models.Item;
import com.meli.couponapi.models.ItemSet;

/**
 * 
 * @author Wilson Forero Rocha
 *
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CouponServiceImplTest {
	
	@InjectMocks
	private CouponServiceImpl couponService;
	
	private static final Map<String,Float> FAV_ITEMS = new HashMap<String, Float>();
	private static final Map<String,Float> FAV_ITEM = new HashMap<String, Float>();
	private static final Map<String,Float> FAV_ITEMS_REPEAT_PRICE = new HashMap<String, Float>();
	
	private static final Float LESS_AMOUNT=40f;
	private static final Float AMOUNT_ZERO=0f;
	private static final Float AMOUNT=500f;
	
	private static final Float ITEM_TOTAL_AMOUNT=480f;
	private static final List<Item> ITEMS_TO_BUY = new ArrayList<>();
	
		
	@BeforeEach
	void setUp() {
		FAV_ITEM.put("MLA1", 100f);
		
		FAV_ITEMS.put("MLA1", 100f);
        FAV_ITEMS.put("MLA2", 210f);
        FAV_ITEMS.put("MLA3", 260f);
        FAV_ITEMS.put("MLA4", 80f);
        FAV_ITEMS.put("MLA5", 90f);
        
        FAV_ITEMS_REPEAT_PRICE.put("MLA1", 100f);
        FAV_ITEMS_REPEAT_PRICE.put("MLA11", 100f);
        FAV_ITEMS_REPEAT_PRICE.put("MLA2", 210f);
        FAV_ITEMS_REPEAT_PRICE.put("MLA3", 260f);
        FAV_ITEMS_REPEAT_PRICE.put("MLA4", 80f);
        FAV_ITEMS_REPEAT_PRICE.put("MLA5", 90f);		
        
        ITEMS_TO_BUY.add(new Item("MLA2", 210f));
        ITEMS_TO_BUY.add(new Item("MLA4", 80f));
        ITEMS_TO_BUY.add(new Item("MLA5", 90f));
        ITEMS_TO_BUY.add(new Item("MLA1", 100f));
	}

    
    @Test
    @DisplayName("Should be return example case from interview documentation")
    void testCalculate() { 	    	    	
        List<String> expected = Arrays.asList("MLA1", "MLA2", "MLA4", "MLA5");        
        List<String> actual = couponService.calculate(FAV_ITEMS, AMOUNT);        
        assertEquals(expected,actual);
    }
    
    @Test
    @DisplayName("Should be return one item when is send one favorite item")
    void testCalculate_oneItem() { 	    	    	
        List<String> expected = Arrays.asList("MLA1");        
        List<String> actual = couponService.calculate(FAV_ITEM, AMOUNT);        
        assertEquals(expected,actual);
    }
        

    @Test
    @DisplayName("Should be return ItemSet Successfully")
    void getItemsToBuyTest() {    	
    	ItemSet actual = couponService.getItemsToBuy(FAV_ITEMS, AMOUNT);
    	ItemSet expected = new ItemSet();
    	
    	expected.setPrice(ITEM_TOTAL_AMOUNT);    	
    	expected.addItem(new Item("MLA2", 210f));
    	expected.addItem(new Item("MLA4", 80f));
    	expected.addItem(new Item("MLA5", 90f));
    	expected.addItem(new Item("MLA1", 100f));   	    	
    	assertTrue(actual.equals(expected));    	
    }
    
    
    
    @Test
    @DisplayName("Should be return true if an item price is greater than amount")
    void getIsEnabledToBuyTest() {
    	
    	Boolean actual = couponService.isEnabledToBuy(FAV_ITEMS, AMOUNT);
    	Boolean expected = true;    	
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Should be return false if an item price is less than amount")
    void getIsEnabledToBuyTest_noteneable() {    	
    	Boolean actual = couponService.isEnabledToBuy(FAV_ITEMS, LESS_AMOUNT);
    	Boolean expected = false;    	
    	assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Should be return false if a amount is zero")
    void getIsEnabledToBuyTest_zeroamount() {    	
    	Boolean actual = couponService.isEnabledToBuy(FAV_ITEMS, AMOUNT_ZERO);
    	Boolean expected = false;    	
    	assertEquals(expected, actual);
    }
    
    
}
