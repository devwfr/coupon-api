package com.meli.couponapi.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.meli.couponapi.exceptions.MeliApiException;
import com.meli.couponapi.models.CouponRequest;
import com.meli.couponapi.models.CouponResponse;
import com.meli.couponapi.models.Item;
import com.meli.couponapi.models.ItemSet;
import com.meli.couponapi.restclients.MeliRestClientImpl;
import com.meli.couponapi.services.CouponServiceImpl;

/**
 * 
 * @author Wilson Forero Rocha
 *
 *
 */

@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

	@InjectMocks
	private CouponController couponController;

	@Mock
	private CouponServiceImpl couponService;

	@Mock
	private MeliRestClientImpl meliClient;

	private static final List<String> FAV_ITEMS = new ArrayList<>();
	private static final Float AMOUNT = 500f;

	private static final List<String> ITEMS_TO_BUY = new ArrayList<>();
	private static final Float AMOUNT_TOTAL = 480f;

	private static final Map<String, Float> ITEMS_TO_VALIDATE = new HashMap<>();

	private static final ItemSet ITEMS_VALIDATED = new ItemSet();

	@BeforeEach
	void setUp() {

		FAV_ITEMS.add("MLA1");
		FAV_ITEMS.add("MLA2");
		FAV_ITEMS.add("MLA3");
		FAV_ITEMS.add("MLA4");
		FAV_ITEMS.add("MLA5");

		ITEMS_TO_BUY.add("MLA1");
		ITEMS_TO_BUY.add("MLA2");
		ITEMS_TO_BUY.add("MLA4");
		ITEMS_TO_BUY.add("MLA5");

		ITEMS_TO_VALIDATE.put("MLA1", 100f);
		ITEMS_TO_VALIDATE.put("MLA2", 210f);
		ITEMS_TO_VALIDATE.put("MLA3", 260f);
		ITEMS_TO_VALIDATE.put("MLA4", 80f);
		ITEMS_TO_VALIDATE.put("MLA5", 90f);

		ITEMS_VALIDATED.setPrice(AMOUNT_TOTAL);
		ITEMS_VALIDATED.addItem(new Item("MLA2", 210f));
		ITEMS_VALIDATED.addItem(new Item("MLA4", 80f));
		ITEMS_VALIDATED.addItem(new Item("MLA5", 90f));
		ITEMS_VALIDATED.addItem(new Item("MLA1", 100f));


	}

	@Test
	@DisplayName("Should return best list of items to buy from a favorite list items")
	void getCouponscalculateCouponTest() {
		when(meliClient.getPriceByItem("MLA1")).thenReturn(100f);
		when(meliClient.getPriceByItem("MLA2")).thenReturn(210f);
		when(meliClient.getPriceByItem("MLA3")).thenReturn(260f);
		when(meliClient.getPriceByItem("MLA4")).thenReturn(80f);
		when(meliClient.getPriceByItem("MLA5")).thenReturn(90f);

		when(couponService.isEnabledToBuy(ITEMS_TO_VALIDATE, AMOUNT)).thenReturn(true);

		when(couponService.getItemsToBuy(ITEMS_TO_VALIDATE, AMOUNT)).thenReturn(ITEMS_VALIDATED);

		CouponRequest request = new CouponRequest(FAV_ITEMS, AMOUNT);

		CouponResponse couponResponse = new CouponResponse(AMOUNT_TOTAL, ITEMS_TO_BUY);

		ResponseEntity<CouponResponse> actualResponse = couponController.getCouponscalculateCoupon(request);

		assertNotNull(actualResponse);
		assertEquals(couponResponse, actualResponse.getBody());
		assertEquals(couponResponse.getTotal(), actualResponse.getBody().getTotal());
		assertEquals(couponResponse.getFavoriteItems(), actualResponse.getBody().getFavoriteItems());

	}

	@Test
	@DisplayName("Should return NOT FOUND status when amount is less than a max price from at least one item")
	void getCouponscalculateCouponTest_lessAmount() {
		when(meliClient.getPriceByItem("MLA1")).thenReturn(100f);
		when(meliClient.getPriceByItem("MLA2")).thenReturn(210f);
		when(meliClient.getPriceByItem("MLA3")).thenReturn(260f);
		when(meliClient.getPriceByItem("MLA4")).thenReturn(80f);
		when(meliClient.getPriceByItem("MLA5")).thenReturn(90f);

		when(couponService.isEnabledToBuy(ITEMS_TO_VALIDATE, AMOUNT)).thenReturn(false);

		CouponRequest request = new CouponRequest(FAV_ITEMS, AMOUNT);

		ResponseEntity<CouponResponse> actualResponse = couponController.getCouponscalculateCoupon(request);

		assertNotNull(actualResponse);
		assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());

	}

	@Test
	@DisplayName("Should return NOT FOUND when a item is not found in Meli API")
	void getCouponscalculateCouponTest_itemnotfound() {

		when(meliClient.getPriceByItem(anyString())).thenThrow(new MeliApiException(null, null));

		CouponRequest request = new CouponRequest(FAV_ITEMS, AMOUNT);

		ResponseEntity<CouponResponse> actualResponse = couponController.getCouponscalculateCoupon(request);

		assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());

	}

}
