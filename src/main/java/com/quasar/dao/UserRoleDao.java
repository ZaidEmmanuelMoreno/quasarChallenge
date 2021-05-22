package com.quasar.dao;

import org.springframework.data.repository.CrudRepository;

import com.quasar.entity.UserRole;

/**
 * @author emmanuel
 *
 */
public interface UserRoleDao extends CrudRepository<UserRole, Long> {

}
