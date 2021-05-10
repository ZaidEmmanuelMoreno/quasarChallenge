package com.quasar.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.quasar.dao.ShipLocationDao;
import com.quasar.entity.ShipLocation;

@Repository
public class ShipLocationRepository {

	@Autowired
	private ShipLocationDao shipLocationDao;
	
	@Transactional(readOnly = true)
	public ShipLocation findByName(String name) {
		return shipLocationDao.findByName(name);
	}
	
	@Transactional(readOnly = true)
	public List<ShipLocation> findAll() {
		return (List<ShipLocation>) shipLocationDao.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public ShipLocation save(ShipLocation shipLocation) {
		return shipLocationDao.save(shipLocation);
	}
}
