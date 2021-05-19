package com.quasar.util;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.quasar.exception.CustomErrorValidationException;
import com.quasar.model.Validation;
import com.quasar.model.request.Satellite;
import com.quasar.model.request.TopSecretRequest;

/**
 * @author emmanuel
 *
 */
@Component
public class Validator {

	/**
	 * Method that validates the fields of the endpoint / top_secret, when it finds an incorrect field, it throws an exception and then stores it 
	 * in a Validation type object, with which it is possible to decide if the process continues or should be stopped.
	 * 
	 * @param topSecretRequest		Object that contains a list of satellites, which contain the name of the satellite to which it is directed, the distance 
	 * 								to the satellite, and the incomplete string arrangement of the message.
	 * @return						Validation type object, which contains a boolean that indicates if it is valid or not and the message if it is not valid.
	 */
	public Validation ValidateFields(TopSecretRequest topSecretRequest) {
		Validation validation = new Validation(true, "");
		List<List<String>> messagesList = new LinkedList<>();
		int messageSize = 0;
		
		try {
			if(topSecretRequest.getSatellites() == null || topSecretRequest.getSatellites().isEmpty()) {
				throw new CustomErrorValidationException(false, Constants.EMPTY_SATELLITES);
			}
			else {
				for(Satellite satellite : topSecretRequest.getSatellites()) {
					if(satellite.getMessage() == null || satellite.getMessage().isEmpty()) {
						throw new CustomErrorValidationException(false, Constants.EMPTY_MESSAGE);
					}
					else {
						messagesList.add(satellite.getMessage());
						messageSize = satellite.getMessage().size();
					}
					if(satellite.getName() == null || satellite.getName().isEmpty()) {
						throw new CustomErrorValidationException(false, Constants.EMPTY_NAME);
					}
					else {
						if( !satellite.getName().equalsIgnoreCase(Constants.SHIP_KENOBI) && 
							!satellite.getName().equalsIgnoreCase(Constants.SHIP_SKYWALKER) && 
							!satellite.getName().equalsIgnoreCase(Constants.SHIP_SATO)) {
							throw new CustomErrorValidationException(false, Constants.INCORRECT_SHIP);
						}
					}
				}
			}
			
			for(List<String> message : messagesList) {
				if(messageSize != message.size()) {
					throw new CustomErrorValidationException(false, Constants.INCORRECT_MESSAGE_SIZE);
				}
			}
		}
		catch (CustomErrorValidationException ceve) {
			validation = new Validation(ceve.isFlag(), ceve.getMessage());
		}
		
		return validation;
	}

	/**
	 * Method that validates the fields of the endpoint / top_secret, when it finds an incorrect field, it throws an exception and then stores it 
	 * in a Validation type object, with which it is possible to decide if the process continues or should be stopped.
	 * 
	 * @param satelliteName			Name of the satellite to which it is directed.
	 * @param satellite				Object containing the distance to the satellite, and the incomplete string array of the message.
	 * @return						Validation type object, which contains a boolean that indicates if it is valid or not and the message if it is not valid.
	 */
	public Validation ValidateFields(String satelliteName, Satellite satellite) {
		Validation validation = new Validation(true, "");
		
		try {
			if(satellite == null) {
				throw new CustomErrorValidationException(false, Constants.EMPTY_SATELLITE);
			}
			else {
				if(satellite.getMessage() == null || satellite.getMessage().isEmpty()) {
					throw new CustomErrorValidationException(false, Constants.EMPTY_MESSAGE);
				}
				if( !satelliteName.equalsIgnoreCase(Constants.SHIP_KENOBI) && 
						!satelliteName.equalsIgnoreCase(Constants.SHIP_SKYWALKER) && 
						!satelliteName.equalsIgnoreCase(Constants.SHIP_SATO)) {
						throw new CustomErrorValidationException(false, Constants.INCORRECT_SHIP);
				}
			}
		}
		catch (CustomErrorValidationException ceve) {
			validation = new Validation(ceve.isFlag(), ceve.getMessage());
		}
		
		return validation;
	}
	
}
