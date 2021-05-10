package com.quasar.exception;

import lombok.Getter;

@Getter
public class CustomErrorValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private boolean flag;
	private String message;
	
	public CustomErrorValidationException(boolean flag, String message) {
		this.flag = flag;
		this.message = message;
	}
	
}
