package com.quasar.dao;

import org.springframework.data.repository.CrudRepository;

import com.quasar.entity.ShipLocation;

public interface ShipLocationDao extends CrudRepository<ShipLocation, Long> {
	
	public ShipLocation findByName(String name);
	
}
