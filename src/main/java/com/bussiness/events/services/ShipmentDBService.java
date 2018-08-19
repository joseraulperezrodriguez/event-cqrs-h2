package com.bussiness.events.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bussiness.events.data.repository.query.IShipmentRepository;
import com.bussiness.events.domain.query.Shipment;

@Service
public class ShipmentDBService {

	@Autowired
	private IShipmentRepository shipmentRepository;
	
	public List<Shipment> getShipmentPage(int page, int size) {
		try {
			List<Shipment> shipments = shipmentRepository.findAll(new PageRequest(page, size)).getContent();
			return shipments;
		} catch (Exception e) {
			return new ArrayList<Shipment>();
		}
		
	}
	
	public int getShipmentParcels(String reference) {
		try {
			Shipment shipment = shipmentRepository.findOne(reference);
			return shipment.getParcels();
		} catch (Exception e) {
			return -1;
		}
	}
	
	public boolean updateState(String reference, String state) {
		try {
			Shipment shipment = shipmentRepository.findOne(reference);
			shipment.setState(state);
			shipmentRepository.save(shipment);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
}
