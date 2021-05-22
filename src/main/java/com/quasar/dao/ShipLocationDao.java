package com.quasar.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.quasar.entity.ShipLocation;

/**
 * @author emmanuel
 *
 */
public interface ShipLocationDao extends CrudRepository<ShipLocation, Long> {
	
	public Optional<ShipLocation> findByName(String name);
	
}
