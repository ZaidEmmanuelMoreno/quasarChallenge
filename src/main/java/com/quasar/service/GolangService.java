package com.quasar.service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.quasar.entity.ImperialCargoShip;
import com.quasar.entity.ShipLocation;
import com.quasar.model.Coordinates;
import com.quasar.model.Equation;
import com.quasar.model.Error;
import com.quasar.model.Validation;
import com.quasar.model.request.Satellite;
import com.quasar.model.request.TopSecretRequest;
import com.quasar.model.response.ErrorResponse;
import com.quasar.model.response.TopSecretResponse;
import com.quasar.repository.ImperialCargoShipRepository;
import com.quasar.repository.ShipLocationRepository;
import com.quasar.util.Constants;
import com.quasar.util.MathOperations;
import com.quasar.util.Validator;

import lombok.extern.log4j.Log4j2;

/**
 * @author emmanuel
 *
 */
@Log4j2
@Service
public class GolangService {
	
	private final int SHIP_NUMBER = 3;
	
	@Autowired
	private ShipLocationRepository shipLocationRepository;
	
	@Autowired
	private ImperialCargoShipRepository imperialCargoShipRepository;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private MathOperations mathOperations;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * Method that by means of a list of satellites, mathematically calculates the coordinates and the secret message.
	 * 
	 * @param 	topSecretRequest	Object that contains a list of satellites, which contain the name of the satellite to which it is directed, the distance 
	 * 								to the satellite, and the incomplete string arrangement of the message.
	 * @return						ResponseEntity with the coordinates and the constructed message.
	 */
	@HystrixCommand(fallbackMethod = "topSecretDefault")
	public ResponseEntity<?> topSecret(TopSecretRequest topSecretRequest) {
		long startTime = System.currentTimeMillis();
		TopSecretResponse topSecretResponse = new TopSecretResponse();
		double distanceKenobi = -100;
		double distanceSkywalker = -100;
		double distanceSato = -100;
		
		Validation validation = validator.ValidateFields(topSecretRequest);
		
		if(!validation.isValid()) {
			log.error("Total execution time topSecret {} Error: {}", System.currentTimeMillis()-startTime, validation.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(validation.getMessage())), HttpStatus.BAD_REQUEST);
		}
		
		try {
			for(Satellite satellite : topSecretRequest.getSatellites()) {
				if(satellite.getName().equalsIgnoreCase(Constants.SHIP_KENOBI)) { distanceKenobi = satellite.getDistance(); }
				else if(satellite.getName().equalsIgnoreCase(Constants.SHIP_SKYWALKER)) { distanceSkywalker = satellite.getDistance(); }
				else if(satellite.getName().equalsIgnoreCase(Constants.SHIP_SATO)) { distanceSato = satellite.getDistance(); }
			}
			
			if(distanceKenobi == -100 || distanceSkywalker == -100 ||distanceSato == -100) {
				log.error("Total execution time topSecret {} Error: {}", System.currentTimeMillis()-startTime, Constants.INCORRECT_SHIP);
				return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(Constants.INCORRECT_SHIP)), HttpStatus.BAD_REQUEST);
			}
			List<List<String>> listMessages = topSecretRequest.getSatellites().stream().map(satellite -> satellite.getMessage()).collect(Collectors.toList());
			
			topSecretResponse.setMessage(this.getMessage(listMessages));
			topSecretResponse.setPosition(this.getLocation(distanceKenobi, distanceSkywalker, distanceSato));
		}
		catch(Exception e) {
			log.error("Total execution time topSecret {} Error: {}", System.currentTimeMillis()-startTime, e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Total execution time topSecret {} ", System.currentTimeMillis()-startTime);
		return new ResponseEntity<TopSecretResponse>(topSecretResponse, HttpStatus.OK);
	}
	
	/**
	 * Default method, in case the topSecret method fails, it returns a generic error response.
	 * 
	 * @param 	topSecretRequest	Object that contains a list of satellites, which contain the name of the satellite to which it is directed, the distance 
	 * 								to the satellite, and the incomplete string arrangement of the message.
	 * @return						Empty ResponseEntity with error http status.
	 */
	public ResponseEntity<?> topSecretDefault(TopSecretRequest topSecretRequest) {
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(Constants.INTERNAL_SERVER_ERROR)), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Method that queries a record in the imperial_cargo_ship table, if the record does not exist, it is created, if it already exists, is edited.
	 * 
	 * @param satelliteName			Name of the satellite to which it is directed.
	 * @param satellite				Object containing the distance to the satellite, and the incomplete string array of the message.
	 * @param ipAddress				IP address of the client.
	 * @return						Empty ResponseEntity with http status.
	 */
	@HystrixCommand(fallbackMethod = "topSecretDefault")
	public ResponseEntity<?> topsecretSplit(String satelliteName, Satellite satellite, String ipAddress) {
		long startTime = System.currentTimeMillis();

		Validation validation = validator.ValidateFields(satelliteName, satellite);

		if(!validation.isValid()) {
			log.error("Total execution time topsecretSplit {} Error: {}", System.currentTimeMillis()-startTime, validation.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(validation.getMessage())), HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			ImperialCargoShip imperialCargoShip = imperialCargoShipRepository.findByNameAndIpAddress(satelliteName.trim(), ipAddress).orElse(new ImperialCargoShip());
			
			if(imperialCargoShip.getName() == null) {
				imperialCargoShip.setName(satelliteName.trim());
				imperialCargoShip.setDistance(satellite.getDistance());
				imperialCargoShip.setMessage(String.join(",", satellite.getMessage()));
				imperialCargoShip.setIpAddress(ipAddress);
			}
			else {
				imperialCargoShip.setDistance(satellite.getDistance());
				imperialCargoShip.setMessage(String.join(",", satellite.getMessage()));
			}
			imperialCargoShipRepository.save(imperialCargoShip);
		}
		catch(Exception e) {
			log.error("Total execution time topsecretSplit POST{} Error: {}", System.currentTimeMillis()-startTime, e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Total execution time topsecretSplit POST {} ", System.currentTimeMillis()-startTime);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Default method, in case the topSecret method fails, it returns a generic error response.
	 * 
	 * @param satelliteName			Name of the satellite to which it is directed.
	 * @param satellite				Object containing the distance to the satellite, and the incomplete string array of the message.
	 * @param ipAddress				IP address of the client.
	 * @return						Empty ResponseEntity with error http status.
	 */
	public ResponseEntity<?> topSecretDefault(String satelliteName, Satellite satellite, String ipAddress) {
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(Constants.INTERNAL_SERVER_ERROR)), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Method that consults the imperial_cargo_ship table through the ip address, and with the kenobi, skywalker and sato points, calculates the point
	 * (coordinates) of origin
	 * 
	 * @param ipAddress				IP address of the client.
	 * @return						ResponseEntity with the coordinates and the constructed message.
	 */
	@HystrixCommand(fallbackMethod = "topSecretDefault")
	public ResponseEntity<?> topsecretSplit(String ipAddress) {
		long startTime = System.currentTimeMillis();
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		List<Satellite> listSatellites = new LinkedList<>();
		ResponseEntity<?> response;
		
		try {
			List<ImperialCargoShip> listImperialCargoShip = imperialCargoShipRepository.findAllByIpAddress(ipAddress).orElse(new LinkedList<>());
	
			if(listImperialCargoShip.size() != SHIP_NUMBER) {
				log.error("Total execution time topsecretSplit GET {} Error: {}", System.currentTimeMillis()-startTime, Constants.INSUFFICIENT_INFORMATION);
				return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(Constants.INSUFFICIENT_INFORMATION)), HttpStatus.BAD_REQUEST);
			}
			
			listImperialCargoShip.stream().forEach(imperialCargoShip -> {
				listSatellites.add(new Satellite(imperialCargoShip.getName(), imperialCargoShip.getDistance(), Arrays.asList(imperialCargoShip.getMessage().split(",", -1))));
			});
			
			topSecretRequest.setSatellites(listSatellites);
			response = this.topSecret(topSecretRequest);
			log.info("Total execution time topsecretSplit GET {} ", System.currentTimeMillis()-startTime);
		}
		catch(Exception e) {
			log.error("Total execution time topsecretSplit GET {} Error: {}", System.currentTimeMillis()-startTime, e.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	/**
	 * Default method, in case the topSecret method fails, it returns a generic error response.
	 * 
	 * @param ipAddress				IP address of the client.
	 * @return						ResponseEntity with the coordinates and the constructed message.
	 */
	public ResponseEntity<?> topSecretDefault(String ipAddress) {
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(Constants.INTERNAL_SERVER_ERROR)), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Method that from the distances, obtains the point (coordinates) of origin.
	 * 
	 * @param distanceKenobi		Distance to Kenobi point.
	 * @param distanceSkywalker		Distance to Skywalker point.
	 * @param distanceSato			Distance to Sasto point.
	 * @return						Coordinates.
	 */
	public Coordinates getLocation(double distanceKenobi, double distanceSkywalker, double distanceSato) {
		List<Coordinates> coordinatesList = new LinkedList<Coordinates>();

		ShipLocation shipLocationKenobi = shipLocationRepository.findByName(Constants.SHIP_KENOBI);
		ShipLocation shipLocationSkywalker = shipLocationRepository.findByName(Constants.SHIP_SKYWALKER);
		ShipLocation shipLocationSato = shipLocationRepository.findByName(Constants.SHIP_SATO);
		
		Equation circleEquationKenobi = mathOperations.getCircumferenceEquation(new Coordinates(shipLocationKenobi.getXCoordinate(), shipLocationKenobi.getYCoordinate()), distanceKenobi);
		Equation circleEquationSkywalker = mathOperations.getCircumferenceEquation(new Coordinates(shipLocationSkywalker.getXCoordinate(), shipLocationSkywalker.getYCoordinate()), distanceSkywalker);
		Equation circleEquationSato = mathOperations.getCircumferenceEquation(new Coordinates(shipLocationSato.getXCoordinate(), shipLocationSato.getYCoordinate()), distanceSato);
		
		coordinatesList = mathOperations.getTwoCirclesIntersection(coordinatesList, circleEquationKenobi, circleEquationSkywalker);
		coordinatesList = mathOperations.getTwoCirclesIntersection(coordinatesList, circleEquationSkywalker, circleEquationSato);
		coordinatesList = mathOperations.getTwoCirclesIntersection(coordinatesList, circleEquationKenobi, circleEquationSato);
		
		return mathOperations.getCoordinates(coordinatesList);
	}
	
	/**
	 * Method that from the list of incomplete messages, generates a string with the constructed message.
	 * 
	 * @param listMessages			Incomplete message list.
	 * @return						Full message.
	 */
	public String getMessage(List<List<String>> listMessages) {
		StringBuilder stringBuilder = new StringBuilder();
		
//		Kenobi: 	[“este”, 	“”, 	“un”, 	“mensaje”]
//		Skywalker: 	[“”, 		“es”, 	“”, 	“mensaje”]
//		Sato: 		[””, 		””, 	”un”, 	””]
		
		List<String> listOne = listMessages.get(0);
		List<String> listTwo = listMessages.get(1);
		List<String> listThree = listMessages.get(2);
		
		for(int x = 0; x < listOne.size(); x++) {
			List<String> wordList = new LinkedList<String>();
			
			if(!listOne.get(x).isEmpty()) {
				wordList.add(listOne.get(x));
			}
			if(!listTwo.get(x).isEmpty()) {
				wordList.add(listTwo.get(x));
			}
			if(!listThree.get(x).isEmpty()) {
				wordList.add(listThree.get(x));
			}
			
			if(wordList.size() == 1) {
				stringBuilder.append(wordList.get(0));
			}
			else if(wordList.size() > 1) {
				List<String> duplicateList = wordList.stream().collect(Collectors.groupingBy(s -> s)).entrySet().stream()
			            .filter(e -> e.getValue().size() > 1).map(e -> e.getKey()).collect(Collectors.toList());
				if(duplicateList.size() > 1) {
					//error
				}
				else {
					stringBuilder.append(duplicateList.get(0));
				}
			}
			if(x != listOne.size() - 1) {
				stringBuilder.append(" ");
			}
		}
		
		return stringBuilder.toString();
	}

	/**
	 * Method that encode a string, generally, a good encoding algorithm applies a SHA-1 or greater hash 
	 * combined with an 8-byte or greater randomly generated salt.
	 * 
	 * @param text					Text to encode.
	 * @return						ResponseEntity with the encoded text.
	 */
	public ResponseEntity<?> encrypt(String text) {
		return new ResponseEntity<String>(bCryptPasswordEncoder.encode(text), HttpStatus.OK);
	}
	
}
