package com.quasar.model;

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
public class Equation {
	
	private double coefficientX;
	private double coefficientY;
	private double independentTermC;
	private double secondMember;
	
}
