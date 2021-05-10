package com.quasar.util;

import org.springframework.stereotype.Component;

import com.quasar.exception.CustomErrorValidationException;
import com.quasar.model.Validation;
import com.quasar.model.request.Satellite;
import com.quasar.model.request.TopSecretRequest;

@Component
public class Validator {

	public Validation validateFields(TopSecretRequest topSecretRequest) {
		Validation validation = new Validation(true, "");
		
		try {
			if(topSecretRequest.getSatellites() == null || topSecretRequest.getSatellites().isEmpty()) {
				throw new CustomErrorValidationException(false, Constants.EMPTY_SATELLITES);
			}
			else {
				for(Satellite satellite : topSecretRequest.getSatellites()) {
					if(satellite.getMessage() == null || satellite.getMessage().isEmpty()) {
						throw new CustomErrorValidationException(false, Constants.EMPTY_MESSAGE);
					}
					if(satellite.getName() == null || satellite.getName().isEmpty()) {
						throw new CustomErrorValidationException(false, Constants.EMPTY_NAME);
					}
					else {
						if( !satellite.getName().equalsIgnoreCase(Constants.SHIP_KENOBI) && 
							!satellite.getName().equalsIgnoreCase(Constants.SHIP_SKYWALKER) && 
							!satellite.getName().equalsIgnoreCase(Constants.SHIP_SATO)) {
							throw new CustomErrorValidationException(false, Constants.INCORECT_SHIP);
						}
					}
				}
			}
		}
		catch (CustomErrorValidationException ceve) {
			validation = new Validation(ceve.isFlag(), ceve.getMessage());
		}
		
		return validation;
	}

}
