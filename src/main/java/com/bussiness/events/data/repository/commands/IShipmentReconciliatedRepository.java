package com.bussiness.events.data.repository.commands;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bussiness.events.domain.commands.ShipmentReconciliated;

@Repository
public interface IShipmentReconciliatedRepository extends CrudRepository<ShipmentReconciliated, String>, PagingAndSortingRepository<ShipmentReconciliated, String>  {

	
}
