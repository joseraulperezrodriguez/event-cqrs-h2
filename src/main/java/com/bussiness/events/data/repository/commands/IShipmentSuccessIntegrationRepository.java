package com.bussiness.events.data.repository.commands;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bussiness.events.domain.commands.ShipmentSuccessIntegration;

@Repository
public interface IShipmentSuccessIntegrationRepository extends CrudRepository<ShipmentSuccessIntegration, String>, PagingAndSortingRepository<ShipmentSuccessIntegration, String>  {

	
}
