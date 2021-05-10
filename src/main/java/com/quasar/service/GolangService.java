package com.quasar.service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
	
	public ResponseEntity<?> topSecret(TopSecretRequest topSecretRequest) {
		long startTime = System.currentTimeMillis();
		TopSecretResponse topSecretResponse = new TopSecretResponse();
		double distanceKenobi = -100;
		double distanceSkywalker = -100;
		double distanceSato = -100;
		
		Validation validation = validator.validateFields(topSecretRequest);
		
		if(!validation.isValid()) {
			log.error("Total execution time topSecret {} Error: {}", System.currentTimeMillis()-startTime, validation.getMessage());
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(validation.getMessage())), HttpStatus.BAD_REQUEST);
		}
		
		for(Satellite satellite : topSecretRequest.getSatellites()) {
			if(satellite.getName().equalsIgnoreCase(Constants.SHIP_KENOBI)) { distanceKenobi = satellite.getDistance(); }
			else if(satellite.getName().equalsIgnoreCase(Constants.SHIP_SKYWALKER)) { distanceSkywalker = satellite.getDistance(); }
			else if(satellite.getName().equalsIgnoreCase(Constants.SHIP_SATO)) { distanceSato = satellite.getDistance(); }
		}
		
		if(distanceKenobi == -100 || distanceSkywalker == -100 ||distanceSato == -100) {
			log.error("Total execution time topSecret {} Error: {}", System.currentTimeMillis()-startTime, Constants.INCORECT_SHIP);
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(Constants.INCORECT_SHIP)), HttpStatus.BAD_REQUEST);
		}
		List<List<String>> listMessages = topSecretRequest.getSatellites().stream().map(satellite -> satellite.getMessage()).collect(Collectors.toList());
		
		topSecretResponse.setMessage(this.getMessage(listMessages));
		topSecretResponse.setPosition(this.getLocation(distanceKenobi, distanceSkywalker, distanceSato));
		
		log.info("Total execution time topSecret {} ", System.currentTimeMillis()-startTime);
		return new ResponseEntity<TopSecretResponse>(topSecretResponse, HttpStatus.OK);
	}

	public ResponseEntity<?> topsecretSplit(String satelliteName, Satellite satellite, String ip) {
		long startTime = System.currentTimeMillis();
		
		ImperialCargoShip imperialCargoShip = imperialCargoShipRepository.findByNameAndIp(satelliteName.trim(), ip).orElse(new ImperialCargoShip());
		
		if(imperialCargoShip.getName() == null) {
			imperialCargoShip.setName(satelliteName.trim());
			imperialCargoShip.setDistance(satellite.getDistance());
			imperialCargoShip.setMessage(String.join(",", satellite.getMessage()));
			imperialCargoShip.setIp(ip);
			imperialCargoShipRepository.save(imperialCargoShip);
		}
		
		log.info("Total execution time topsecretSplit POST {} ", System.currentTimeMillis()-startTime);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<?> topsecretSplit(String ip) {
		long startTime = System.currentTimeMillis();
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		List<Satellite> listSatellites = new LinkedList<>();
		ResponseEntity<?> response;
		
		List<ImperialCargoShip> listImperialCargoShip = imperialCargoShipRepository.findAllByIp(ip).orElse(new LinkedList<>());

		if(listImperialCargoShip.size() != SHIP_NUMBER) {
			log.error("Total execution time topsecretSplit GET {} Error: {}", System.currentTimeMillis()-startTime, Constants.INSUFFICIENT_INFORMATION);
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(Constants.INSUFFICIENT_INFORMATION)), HttpStatus.BAD_REQUEST);
		}
		
		listImperialCargoShip.stream().forEach(imperialCargoShip -> {
			listSatellites.add(new Satellite(imperialCargoShip.getName(), imperialCargoShip.getDistance(), Arrays.asList(imperialCargoShip.getMessage().split(","))));
		});
		
		topSecretRequest.setSatellites(listSatellites);
		response = this.topSecret(topSecretRequest);
		log.info("Total execution time topsecretSplit GET {} ", System.currentTimeMillis()-startTime);
		return response;
	}
	
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
	
}
