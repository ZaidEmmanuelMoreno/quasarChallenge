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

@Repository
public class ImperialCargoShipRepository {

	@Autowired
	private ImperialCargoShipDao imperialCargoShipDao;
	
	@Transactional(readOnly = true)
	public Optional<ImperialCargoShip> findByNameAndIp(String name, String ip) {
		return Optional.of(imperialCargoShipDao.findByNameAndIp(name, ip));
	}
	
	@Transactional(readOnly = true)
	public Optional<List<ImperialCargoShip>> findAllByIp(String ip) {
		return Optional.of((List<ImperialCargoShip>) imperialCargoShipDao.findAllByIp(ip));
	}
	
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
	public ImperialCargoShip save(ImperialCargoShip imperialCargoShip) {
		return imperialCargoShipDao.save(imperialCargoShip);
	}
}
