package com.quasar.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.quasar.entity.User;

/**
 * @author emmanuel
 *
 */
public interface UserDao extends CrudRepository<User, Long> {

	public Optional<User> findByUsernameAndPassword(String username, String password);
	public Optional<User> findByUsername(String username);
	
}
