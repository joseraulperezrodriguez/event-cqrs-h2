package com.bussiness.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.data.repository.commands.IShipmentParcelReconciliatedRepository;
import com.bussiness.events.domain.commands.ShipmentParcelReconciliated;

@Service
public class ShipmentParcelReconciliatedDBService {

	@Autowired
	private IShipmentParcelReconciliatedRepository shipmentParcelReconciliatedRepository;
	
	
	public boolean insertShipmentParcelReconciliatedEvent(ShipmentParcelReconciliated shipmentParcelReconciliated) {
		try {
			shipmentParcelReconciliatedRepository.save(shipmentParcelReconciliated);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int countByReference(String reference) {
		try {
			int countByReference = shipmentParcelReconciliatedRepository.countByReference(reference);
			return countByReference;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
}
