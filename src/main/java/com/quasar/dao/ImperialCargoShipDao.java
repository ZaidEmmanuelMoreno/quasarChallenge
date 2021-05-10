package com.quasar.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.quasar.entity.ImperialCargoShip;

public interface ImperialCargoShipDao extends CrudRepository<ImperialCargoShip, Long> {

	public ImperialCargoShip findByNameAndIp(String name, String ip);

	public List<ImperialCargoShip> findAllByIp(String ip);
	
}
