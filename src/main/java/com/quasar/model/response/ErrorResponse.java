package com.quasar.model.response;

import com.quasar.model.Error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author emmanuel
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	
	private Error error;
	
}
