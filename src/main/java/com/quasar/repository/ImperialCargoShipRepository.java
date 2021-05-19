package com.quasar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quasar.dao.ImperialCargoShipDao;
import com.quasar.entity.ImperialCargoShip;

/**
 * @author emmanuel
 *
 */
@Repository
public class ImperialCargoShipRepository {

	@Autowired
	private ImperialCargoShipDao imperialCargoShipDao;

	/**
	 * Method that queries information from the imperial_cargo_ship table, by name and ip.
	 * 
	 * @param name					Name of the satellite.
	 * @param IpAddress				IP address of the client.
	 * @return						An ImperialCargoShip object, containing the name, distance, and string array of the incomplete message.
	 */
	@Transactional(readOnly = true)
	public Optional<ImperialCargoShip> findByNameAndIpAddress(String name, String ipAddress) {
		return Optional.ofNullable(imperialCargoShipDao.findByNameAndIpAddress(name, ipAddress));
	}
	
	/**
	 * Method that queries all of the imperial_cargo_ship table by ip.
	 * 
	 * @param ipAddress				IP address of the client.
	 * @return						An ImperialCargoShip list, containing the name, distance, and string array of the incomplete message.
	 */
	@Transactional(readOnly = true)
	public Optional<List<ImperialCargoShip>> findAllByIpAddress(String ipAddress) {
		return Optional.ofNullable((List<ImperialCargoShip>) imperialCargoShipDao.findAllByIpAddress(ipAddress));
	}
	
	/**
	 * Save a record in the imperial_cargo_ship table.
	 * 
	 * @param imperialCargoShip		ImperialCargoShip object that contains the name, distance, and string array of the incomplete message.
	 * @return						ImperialCargoShip object, the one that was stored in the database.
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public ImperialCargoShip save(ImperialCargoShip imperialCargoShip) {
		return imperialCargoShipDao.save(imperialCargoShip);
	}
}
