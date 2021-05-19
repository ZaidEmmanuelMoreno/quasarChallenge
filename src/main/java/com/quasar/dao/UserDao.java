package com.quasar.dao;

import org.springframework.data.repository.CrudRepository;

import com.quasar.entity.User;

/**
 * @author emmanuel
 *
 */
public interface UserDao extends CrudRepository<User, Long> {

	public User findByUsernameAndPassword(String username, String password);
	public User findByUsername(String username);
	
}
