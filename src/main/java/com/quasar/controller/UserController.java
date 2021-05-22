package com.quasar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quasar.model.request.CreateUserRequest;
import com.quasar.service.UserService;

import io.swagger.annotations.ApiOperation;

/**
 * @author emmanuel
 *
 */
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	/**
	 * Create a user that could acces to the resourses of the system, with the role USER.
	 * 
	 * @param createUserRequest		Object that contains the username and password of the new user.
	 * @return						ResponseEntity with HttpStatus.
	 */
	@PostMapping("/user/create")
	@ApiOperation(value = "Create a user that could acces to the resourses of the system, with the role USER.")
	public ResponseEntity<?> create(@RequestBody CreateUserRequest createUserRequest) {
		return userService.create(createUserRequest);
	}
	
}
