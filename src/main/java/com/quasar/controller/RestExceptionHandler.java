package com.quasar.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.quasar.model.Error;

/**
 * @author emmanuel
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

   @Override
   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
       return buildResponseEntity(new Error(HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
   }
   
   /**
    * Method that builds a responseEntity with error message.
    * 
	* @param error					Error object with the detail of the error.
	* @param httpStatus				Status http of the response.
	 * @return						ResponseEntity with error object.
	*/
	private ResponseEntity<Object> buildResponseEntity(Error error, HttpStatus httpStatus) {
       return new ResponseEntity<>(error, httpStatus);
   }

}