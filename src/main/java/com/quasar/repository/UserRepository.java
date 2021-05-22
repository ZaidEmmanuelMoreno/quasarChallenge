package com.quasar.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quasar.dao.UserDao;
import com.quasar.entity.User;

/**
 * @author emmanuel
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private UserDao userDao;

	/**
	 * Method that queries information from the users table, by username and password.
	 * 
	 * @param username				Username of the user.
	 * @param password				Password of the user.
	 * @return						An ShipLocation object, containing the name, x_coordinate and y_coordinate.
	 */
	@Transactional(readOnly = true)
	public Optional<User> findByUsernameAndPassword(String username, String password) {
		return userDao.findByUsernameAndPassword(username, password);
	}

	/**
	 * Method that queries information from the users table, by username.
	 * 
	 * @param username				Username of the user.
	 * @param password				Password of the user.
	 * @return						An ShipLocation object, containing the name, x_coordinate and y_coordinate.
	 */
	@Transactional(readOnly = true)
	public Optional<User> findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	/**
	 * Save a record in the users table.
	 * 
	 * @param user					Object that contains the user's data
	 * @return						User object, the one that was stored in the database.
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public User save(User user) {
		return userDao.save(user);
	}

}
