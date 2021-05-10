package com.quasar.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

	public static double roundingDecimals(double number, int numberDecimals) {
	    BigDecimal rounded = new BigDecimal(number).setScale(numberDecimals, RoundingMode.HALF_EVEN);
	    return rounded.doubleValue();
	}
	
}
