package com.quasar.service;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.http.ResponseEntity;

import com.quasar.entity.ShipLocation;
import com.quasar.model.request.Satellite;
import com.quasar.model.request.TopSecretRequest;
import com.quasar.repository.ShipLocationRepository;
import com.quasar.util.Constants;
import com.quasar.util.MathOperations;
import com.quasar.util.Validator;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class GolangTest {

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private GolangService golangService;
	
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private Validator validator;
	
	@Mock
	private ShipLocationRepository shipLocationRepository;
	
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private MathOperations mathOperations;
	
	@BeforeEach
	void setUp() throws Exception {
		golangService.setTestsObjects(validator, shipLocationRepository, mathOperations);
	}
	
	@Test
	public void topSecret() {

		when(shipLocationRepository.findByName(Constants.SHIP_KENOBI)).thenReturn(new ShipLocation((long) 1, "kenobi", -500, -200));
		when(shipLocationRepository.findByName(Constants.SHIP_SKYWALKER)).thenReturn(new ShipLocation((long) 2, "skywalker", 100, -100));
		when(shipLocationRepository.findByName(Constants.SHIP_SATO)).thenReturn(new ShipLocation((long) 3, "sato", 500, 100));
		
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		List<Satellite> satellites = new LinkedList<>();
		satellites.add(new Satellite("kenobi", 509.9019513592785, Arrays.asList("este","","","mensaje","")));
		satellites.add(new Satellite("skywalker", 640.3124237432849, Arrays.asList("","es","","","secreto")));
		satellites.add(new Satellite("sato", 921.9544457292888, Arrays.asList("este","","un","","")));
		topSecretRequest.setSatellites(satellites);
		
		ResponseEntity<?> response = golangService.topSecret(topSecretRequest);
		
		System.out.println(response.toString());
	}
	
}
