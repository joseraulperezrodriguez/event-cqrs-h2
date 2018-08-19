package com.bussiness.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.data.repository.query.IParcelRepository;
import com.bussiness.events.domain.query.Parcel;

@Service
public class ParcelDBService {

	@Autowired
	private IParcelRepository parcelRepository;

	public Parcel getById(String id) {
		try {
			return parcelRepository.findOne(id);
		} catch(Exception e) {
			return null;
		}
	}
	
	
}
