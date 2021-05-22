package com.quasar.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quasar.dao.UserRoleDao;
import com.quasar.entity.Role;
import com.quasar.entity.User;
import com.quasar.entity.UserRole;

/**
 * @author emmanuel
 *
 */
@Repository
public class UserRoleRepository {

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private UserRepository userRepository;

	/**
	 * Save a record in the users_roles table.
	 * 
	 * @param user					Object that contains relations between users and roles.
	 * @return						UserRole object, the one that was stored in the database.
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public void saveNewUser(User user, Role role) {
		user = userRepository.save(user);
		UserRole userRole = new UserRole();
		userRole.setUserId(user.getId());
		userRole.setRoleId(role.getId());
		userRoleDao.save(userRole);
	}

	/**
	 * Save a record in the users_roles table.
	 * 
	 * @param user					Object that contains relations between users and roles.
	 * @return						UserRole object, the one that was stored in the database.
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public UserRole save(UserRole userRole) {
		return userRoleDao.save(userRole);
	}
	
}
