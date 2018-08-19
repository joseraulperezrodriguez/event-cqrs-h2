package com.bussiness.events.data.repository.query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bussiness.events.domain.query.Shipment;

@Repository
public interface IShipmentRepository extends CrudRepository<Shipment, String>, PagingAndSortingRepository<Shipment, String>  {

}
