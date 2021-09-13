package com.meli.couponapi.exceptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Wilson Forero Rocha
 *
 */
public class MeliApiException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7519463360206731600L;

	
	private HttpStatus status;

	public MeliApiException(String message,HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	
	
}
