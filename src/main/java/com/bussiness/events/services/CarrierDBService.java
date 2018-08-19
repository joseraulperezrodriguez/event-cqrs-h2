package com.bussiness.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.data.repository.query.ICarrierRepository;
import com.bussiness.events.domain.query.Carrier;

@Service
public class CarrierDBService {

	@Autowired
	private ICarrierRepository carrierRepository;

	public Carrier insertCarrier(Carrier c) {
		try {
			return carrierRepository.save(c);
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getFinalStatus(String carrierName) {
		try {
			Carrier c = carrierRepository.getByName(carrierName);
			return c.getFinal_status();
		} catch (Exception e) {
			return null;
		}

	}
	
	
	
}
