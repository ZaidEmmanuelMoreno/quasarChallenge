package com.quasar.model.request;

import java.io.Serializable;
import java.util.List;

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
public class Satellite implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private double distance;
	private List<String> message;
	
}
