package com.bussiness.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.data.repository.commands.IParcelTrackedRepository;
import com.bussiness.events.domain.commands.ParcelTracked;

@Service
public class ParcelTrackedDBService {

	@Autowired
	private IParcelTrackedRepository parcelTrackedRepository;

	public boolean insertParcelTrackedEvent(ParcelTracked parcelTracked) {
		try {
			parcelTrackedRepository.save(parcelTracked);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
