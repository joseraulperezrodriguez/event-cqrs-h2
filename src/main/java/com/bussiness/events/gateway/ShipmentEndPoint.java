package com.bussiness.events.gateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bussiness.events.domain.query.Shipment;
import com.bussiness.events.services.ShipmentDBService;

@RestController
@RequestMapping("/shipments")
public class ShipmentEndPoint {

	
	@Autowired
	private ShipmentDBService shipmentService;
	
	@RequestMapping(value="{page}/{size}", method=RequestMethod.GET)
	public List<Shipment> getShipments(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
		return shipmentService.getShipmentPage(page, size);
	
	}
	
	
}
