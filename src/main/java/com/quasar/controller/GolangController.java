package com.quasar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quasar.model.request.Satellite;
import com.quasar.model.request.TopSecretRequest;
import com.quasar.service.GolangService;
import com.quasar.util.HttpUtil;

/**
 * @author emmanuel
 *
 */
@RestController
public class GolangController {
	
	@Autowired
	private GolangService golangService;

	/**
	 * Endpoint that receives a list of satellites and mathematically calculates the coordinates and the secret message sent by the imperial cargo carrier, 
	 * in case the position cannot be calculated, it returns an http status 404.
	 * 
	 * @param 	topSecretRequest	Object that contains a list of satellites, which contain the name of the satellite to which it is directed, the distance 
	 * 								to the satellite, and the incomplete string arrangement of the message.
	 * @return						ResponseEntity with the coordinates and the constructed message.
	 */
	@PostMapping("/topsecret")
	public ResponseEntity<?> topsecret(@RequestBody TopSecretRequest topSecretRequest) {
		return golangService.topSecret(topSecretRequest);
	}

	/**
	 * Endpoint that receives only one satellite and stores it in the database, in case of success, it returns an http status 200, in case of error, it 
	 * returns an http status 404.
	 * 
	 * @param satelliteName			Name of the satellite to which it is directed.
	 * @param satellite				Object containing the distance to the satellite, and the incomplete string array of the message.
	 * @return						Empty ResponseEntity with http status.
	 */
	@PostMapping("/topsecret_split/{satellite_name}")
	public ResponseEntity<?> topsecretSplit(@PathVariable("satellite_name") String satelliteName, @RequestBody Satellite satellite) {
		return golangService.topsecretSplit(satelliteName, satellite, HttpUtil.getClientIp());
	}

	/**
	 * Endpoint that mathematically calculates the coordinates and the secret message sent by the imperial cargo carrier, only if you have the information 
	 * of the 3 satellites [Kenobi, Skywalker and Sato], registered in the endpoint "/topsecret_split/{satellite_name}".
	 * 
	 * @return						ResponseEntity with the coordinates and the constructed message.
	 */
	@GetMapping("/topsecret_split")
	public ResponseEntity<?> topsecretSplit() {
		return golangService.topsecretSplit(HttpUtil.getClientIp());
	}
	
	/**
	 * Endpoint that encode a text string
	 * 
	 * @param text					Text to encode.
	 * @return						ResponseEntity with the encoded text.
	 */
	@PostMapping("/encrypt")
	public ResponseEntity<?> encrypt(@RequestParam String text) {
		return golangService.encrypt(text);
	}
	
}
