package com.bussiness.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.data.repository.commands.IShipmentReconciliatedRepository;
import com.bussiness.events.domain.commands.ShipmentReconciliated;

@Service
public class ShipmentReconciliatedDBService {

	@Autowired
	private IShipmentReconciliatedRepository shipmentReconciliatedRepository;
	
	public boolean insertShipmentReconciliatedEvent(ShipmentReconciliated shipmentReconciliated) {
		try {
			shipmentReconciliatedRepository.save(shipmentReconciliated);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}


