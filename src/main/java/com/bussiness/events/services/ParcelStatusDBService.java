package com.bussiness.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.data.repository.query.IParcelStatusRepository;
import com.bussiness.events.domain.commands.ParcelTracked;
import com.bussiness.events.domain.query.ParcelStatus;

@Service
public class ParcelStatusDBService {

	@Autowired
	private IParcelStatusRepository parcelStatusRepository;

	public boolean parcelStatusUpdate(ParcelTracked parcelTracked) {		
		try {
			ParcelStatus parcelStatus = parcelStatusRepository.findOne(parcelTracked.getTracking_number());
			if(parcelStatus == null) return false;
			
			parcelStatus.setStatus(parcelTracked.getStatus());
			parcelStatus.setWeight(parcelTracked.getWeight());
			
			parcelStatusRepository.save(parcelStatus);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
				
	}
	
	public boolean insertParcelStatus(ParcelStatus parcelStatus) {
		parcelStatusRepository.save(parcelStatus);
		return true;
	}
	
}
