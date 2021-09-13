package com.meli.couponapi.restclients;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.meli.couponapi.exceptions.MeliApiException;
import com.meli.couponapi.models.MeliApiResponse;

/**
 * 
 * @author Wilson Forero Rocha
 *
 */

@ExtendWith(MockitoExtension.class)
public class MeliRestClientImplTest {
	@InjectMocks
	private MeliRestClientImpl clientMeli;

	@Mock
	private RestTemplate restTemplate;
	

	private static final String FAKE_ITEM_ID = "MLA111";
	private static final String ITEM_ID = "MLA1";
	private static final Float PRICE = 29990f;

	@Test
	@DisplayName("Should return successfully item price")
	void getPriceByItemTest_success() {		

		MeliApiResponse response = new MeliApiResponse(ITEM_ID, PRICE);

		when(restTemplate.getForEntity( anyString(), any()))
				.thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

		Float expected = 29990f;
		Float actual = clientMeli.getPriceByItem(ITEM_ID);

		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Should throw MeliApiException when item not found")
	void getPriceByItemTest_error() {

		when(restTemplate.getForEntity(anyString(),any()))
				.thenReturn(new ResponseEntity<>(new MeliApiResponse() ,HttpStatus.NOT_FOUND));

		try {
			clientMeli.getPriceByItem(FAKE_ITEM_ID);
		} catch (Exception e) {
			assertEquals(MeliApiException.class, e.getClass());
			assertEquals("Error in Melis Api", e.getMessage());
		}

	}

	@Test
	@DisplayName("Should throw MeliApiException when itemid is null")
	void getPriceByItemTest_null() {
				
		try {
			clientMeli.getPriceByItem(null);
		} catch (Exception e) {
			assertEquals(MeliApiException.class, e.getClass());
			assertEquals("ItemId is NUll", e.getMessage());
		}

	}

}
