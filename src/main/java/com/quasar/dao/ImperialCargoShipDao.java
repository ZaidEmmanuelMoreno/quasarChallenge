package com.quasar.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.quasar.entity.ImperialCargoShip;

/**
 * @author emmanuel
 *
 */
public interface ImperialCargoShipDao extends CrudRepository<ImperialCargoShip, Long> {

	public Optional<ImperialCargoShip> findByNameAndIpAddress(String name, String ipAddress);

	public Optional<List<ImperialCargoShip>> findAllByIpAddress(String ipAddress);
	
}
