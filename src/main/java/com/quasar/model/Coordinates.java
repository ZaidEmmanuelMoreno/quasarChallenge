package com.quasar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
	
	private double x;
	private double y;
	
	@JsonIgnore(value = true)
	public String getConcatString() {
		return Double.toString(this.x).concat(",").concat(Double.toString(this.y));
	}
	
}
