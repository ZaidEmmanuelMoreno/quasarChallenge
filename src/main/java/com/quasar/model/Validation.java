package com.quasar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author emmanuel
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Validation {

	private boolean valid;
	private String message;
}
