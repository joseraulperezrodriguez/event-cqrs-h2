package com.bussiness.events.data.repository.commands;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bussiness.events.domain.commands.ShipmentParcelReconciliated;

public interface IShipmentParcelReconciliatedRepository extends CrudRepository<ShipmentParcelReconciliated, String>, PagingAndSortingRepository<ShipmentParcelReconciliated, String> {
	
	public int countByReference(String reference); 
}
