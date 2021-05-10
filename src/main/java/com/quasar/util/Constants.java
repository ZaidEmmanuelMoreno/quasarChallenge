package com.quasar.util;

import java.util.Arrays;
import java.util.List;

public class Constants {
	public static final String SHIP_KENOBI					= "kenobi";
	public static final String SHIP_SKYWALKER				= "skywalker";
	public static final String SHIP_SATO					= "sato";
	
	
	public static final String EMPTY_SATELLITES				= "The field 'satellites' must be present";
	public static final String EMPTY_NAME					= "The field 'name' must be present";
	public static final String EMPTY_MESSAGE				= "The field 'message' must be present";
	public static final String INCORECT_SHIP				= "The field 'name' must be ['kenobi','skywalker','sato']";
	public static final String INSUFFICIENT_INFORMATION		= "Insufficent information, please send all ships";
	
	public List<String> getShipList() {
		return Arrays.asList(SHIP_KENOBI, SHIP_SKYWALKER, SHIP_SATO);
	}
}
