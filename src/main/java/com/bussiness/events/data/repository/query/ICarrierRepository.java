package com.bussiness.events.data.repository.query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bussiness.events.domain.query.Carrier;

public interface ICarrierRepository extends CrudRepository<Carrier, Integer>, 
	PagingAndSortingRepository<Carrier, Integer> {
	
	public Carrier getByName(String name);

}
