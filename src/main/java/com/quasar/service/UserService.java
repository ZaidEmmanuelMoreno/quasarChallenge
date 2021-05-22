package com.quasar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.quasar.entity.Role;
import com.quasar.entity.User;
import com.quasar.entity.UserRole;
import com.quasar.enums.RolesEnum;
import com.quasar.model.Error;
import com.quasar.model.request.CreateUserRequest;
import com.quasar.model.response.ErrorResponse;
import com.quasar.repository.RoleRepository;
import com.quasar.repository.UserRoleRepository;
import com.quasar.util.Constants;

import lombok.extern.log4j.Log4j2;

/**
 * @author emmanuel
 *
 */
@Log4j2
@Service
public class UserService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/**
	 * Create a user that could acces to the resourses of the system, with the role USER.
	 * 
	 * @param createUserRequest		Object that contains the username and password of the new user.
	 * @return						ResponseEntity with HttpStatus.
	 */
	public ResponseEntity<?> create(CreateUserRequest createUserRequest) {
		long startTime = System.currentTimeMillis();
		
		try {
			User user = new User();
			user.setUsername(createUserRequest.getUsername());
			user.setPassword(bcryptPasswordEncoder.encode(createUserRequest.getPassword()));
//			user = userRepository.save(user);
			
			Optional<Role> role = roleRepository.findByName(RolesEnum.USER.getValue());
			UserRole userRole = new UserRole();
			userRole.setUserId(user.getId());
			userRole.setRoleId(role.get().getId());
			
			userRoleRepository.saveNewUser(user,userRole);
			
		}
		catch(Exception e) {
			log.error("Total execution time userCreate {} Error: {}", System.currentTimeMillis()-startTime, e.getMessage());
			
			String errorMessage = e.getMessage();
			
			if(errorMessage.contains(Constants.CONSTRAINT)) {
				errorMessage = Constants.ERROR_CONSTRAINT;
			}
			
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(new Error(errorMessage)), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Total execution time userCreate {} ", System.currentTimeMillis()-startTime);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
