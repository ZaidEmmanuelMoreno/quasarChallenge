package com.quasar.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.quasar.entity.ImperialCargoShip;

/**
 * @author emmanuel
 *
 */
public interface ImperialCargoShipDao extends CrudRepository<ImperialCargoShip, Long> {

	public ImperialCargoShip findByNameAndIpAddress(String name, String ipAddress);

	public List<ImperialCargoShip> findAllByIpAddress(String ipAddress);
	
}
