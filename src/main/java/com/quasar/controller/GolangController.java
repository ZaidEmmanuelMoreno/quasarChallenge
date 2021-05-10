package com.quasar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quasar.model.request.Satellite;
import com.quasar.model.request.TopSecretRequest;
import com.quasar.service.GolangService;

@RestController
public class GolangController {
	
	@Autowired
	private GolangService golangService;

	@PostMapping("/topsecret")
	public ResponseEntity<?> topsecret(@RequestBody TopSecretRequest topSecretRequest) {
		return golangService.topSecret(topSecretRequest);
	}

	@PostMapping("/topsecret_split/{satellite_name}")
	public ResponseEntity<?> topsecretSplit(@PathVariable("satellite_name") String satelliteName, @RequestBody Satellite satellite) {
		String ip = "172.16.4.205";
		return golangService.topsecretSplit(satelliteName, satellite, ip);
	}

	@GetMapping("/topsecret_split")
	public ResponseEntity<?> topsecretSplit() {
		String ip = "172.16.4.205";
		return golangService.topsecretSplit(ip);
	}
	
}
