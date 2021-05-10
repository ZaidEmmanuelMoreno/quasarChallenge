package com.quasar.model.response;

import java.io.Serializable;

import com.quasar.model.Coordinates;

import lombok.Data;

@Data
public class TopSecretResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Coordinates position;
	private String message;
	
}
