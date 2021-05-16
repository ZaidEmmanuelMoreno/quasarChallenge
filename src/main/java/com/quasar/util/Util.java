package com.quasar.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author emmanuel
 *
 */
public class Util {

	/**
	 * Method that rounds any number to any number of decimal places.
	 * 
	 * @param number				Number with complete decimals.
	 * @param numberDecimals		Number of decimal places to round.
	 * @return						Rounded number.
	 */
	public static double roundingDecimals(double number, int numberDecimals) {
	    BigDecimal rounded = new BigDecimal(number).setScale(numberDecimals, RoundingMode.HALF_EVEN);
	    return rounded.doubleValue();
	}
	
}
