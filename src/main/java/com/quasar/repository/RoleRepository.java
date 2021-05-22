package com.quasar.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quasar.dao.RoleDao;
import com.quasar.entity.Role;

/**
 * @author emmanuel
 *
 */
@Repository
public class RoleRepository {

	@Autowired
	private RoleDao roleDao;

	/**
	 * Method that queries information from the roles and users_roles tables, by user_id.
	 * 
	 * @param userId				user_id of the role.
	 * @return						A Role object, that contains the name of the role.
	 */
	@Transactional(readOnly = true)
	public Optional<Role> findByIdUserId(Long userId) {
		return roleDao.findByIdUserId(userId);
	}

	/**
	 * Method that queries information from the roles by name.
	 * 
	 * @param name					Name of the role.
	 * @return						A Role object, that contains the name of the role.
	 */
	@Transactional(readOnly = true)
	public Optional<Role> findByName(String name) {
		return roleDao.findByName(name);
	}
}
