package com.quasar.exception;

import lombok.Getter;

/**
 * @author emmanuel
 *
 */
@Getter
public class CustomErrorValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private boolean flag;
	private String message;
	
	/**
	 * Exception that is thrown when doing a validation.
	 * 
	 * @param flag					Boolean that indicates whether it is valid or not.
	 * @param message				Error message.
	 */
	public CustomErrorValidationException(boolean flag, String message) {
		this.flag = flag;
		this.message = message;
	}
	
}
