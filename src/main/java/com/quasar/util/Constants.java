package com.quasar.util;

/**
 * @author emmanuel
 *
 */
public class Constants {
	public static final String SHIP_KENOBI					= "kenobi";
	public static final String SHIP_SKYWALKER				= "skywalker";
	public static final String SHIP_SATO					= "sato";
	

	public static final String EMPTY_SATELLITES				= "The field 'satellites' must be present";
	public static final String EMPTY_SATELLITE				= "The field 'satellite' must be present";
	public static final String EMPTY_NAME					= "The field 'name' must be present";
	public static final String EMPTY_MESSAGE				= "The field 'message' must be present";
	public static final String INCORRECT_SHIP				= "The field 'name' must be ['kenobi','skywalker','sato']";
	public static final String INCORRECT_MESSAGE_SIZE		= "The message array does not have the correct size";
	public static final String INCORRECT_DISTANCES			= "The distances provided are not correct";
	
	
	public static final String INSUFFICIENT_INFORMATION		= "Insufficent information, please send all ships";
	public static final String INTERNAL_SERVER_ERROR		= "Internal server error, please retry later";

	public static final String HEADER_AUTHORIZACION_KEY		= "Authorization";
	public static final String TOKEN_BEARER_PREFIX			= "Bearer ";
	public static final String SUPER_SECRET_KEY				= "qpalkd8283bu";
	public static final long TOKEN_EXPIRATION_TIME			= 900000;
	
}
