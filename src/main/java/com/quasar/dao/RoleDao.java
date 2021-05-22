package com.quasar.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.quasar.entity.Role;

/**
 * @author emmanuel
 *
 */
public interface RoleDao extends CrudRepository<Role, Long> {

	@Query("from Role r inner join UserRole ur on r.id = ur.roleId where ur.userId = ?1")
	public Optional<Role> findByIdUserId(Long userId);
	
	public Optional<Role> findByName(String name);
}
