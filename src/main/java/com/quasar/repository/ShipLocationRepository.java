package com.quasar.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quasar.dao.ShipLocationDao;
import com.quasar.entity.ShipLocation;

/**
 * @author emmanuel
 *
 */
@Repository
public class ShipLocationRepository {

	@Autowired
	private ShipLocationDao shipLocationDao;

	/**
	 * Method that queries information from the ship_location table, by name.
	 * 
	 * @param name					Name of the satellite.
	 * @return						An ShipLocation object, containing the name, x_coordinate and y_coordinate.
	 */
	@Transactional(readOnly = true)
	public ShipLocation findByName(String name) {
		return shipLocationDao.findByName(name);
	}
	
	/**
	 * Method that queries all of the ship_location table.
	 * 
	 * @return						An ShipLocation list, containing the name, x_coordinate and y_coordinate.
	 */
	@Transactional(readOnly = true)
	public List<ShipLocation> findAll() {
		return (List<ShipLocation>) shipLocationDao.findAll();
	}
	
	/**
	 * Save a record in the ship_location table.
	 * 
	 * @param shipLocation			ShipLocation object that contains the name, x_coordinate, and y_coordinate.
	 * @return						ShipLocation object, the one that was stored in the database.
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public ShipLocation save(ShipLocation shipLocation) {
		return shipLocationDao.save(shipLocation);
	}
}
