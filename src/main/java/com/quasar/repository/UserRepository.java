package com.quasar.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
	private UserDao usersDao;

	/**
	 * Method that queries information from the users table, by username and password.
	 * 
	 * @param username				Username of the user.
	 * @param password				Password of the user.
	 * @return						An ShipLocation object, containing the name, x_coordinate and y_coordinate.
	 */
	@Transactional(readOnly = true)
	public User findByUsernameAndPassword(String username, String password) {
		return usersDao.findByUsernameAndPassword(username, password);
	}

	/**
	 * Method that queries information from the users table, by username.
	 * 
	 * @param username				Username of the user.
	 * @param password				Password of the user.
	 * @return						An ShipLocation object, containing the name, x_coordinate and y_coordinate.
	 */
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return usersDao.findByUsername(username);
	}

}
