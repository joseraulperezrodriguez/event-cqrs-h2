package com.bussiness.events.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bussiness.events.core.IEventDispatcher;
import com.bussiness.events.domain.commands.ParcelTracked;
import com.bussiness.events.domain.commands.ShipmentSuccessIntegration;
import com.bussiness.events.util.RESTPonse;

@RestController
@RequestMapping("/events")
public class EventsEndPoint {
	
	@Autowired
	private IEventDispatcher<ShipmentSuccessIntegration> shipmentSuccessIntegrationDispatcher;
	
	@Autowired
	private IEventDispatcher<ParcelTracked> parcelTrackednDispatcher;
		
	@RequestMapping(value="carrier_success", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public RESTPonse shipmentsIntegrated(@RequestBody ShipmentSuccessIntegration shipmentCreated) {
		try {
			boolean ans = shipmentSuccessIntegrationDispatcher.dispatchEvent(shipmentCreated);
			return new RESTPonse(ans, shipmentCreated);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTPonse(false, "Failed registering the event");
		}
		
	}
	
	@RequestMapping(value="parcel_tracked", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public RESTPonse parcelTracked(@RequestBody ParcelTracked parcelTracked) {
		try {
			boolean ans = parcelTrackednDispatcher.dispatchEvent(parcelTracked);
			return new RESTPonse(ans, parcelTracked);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTPonse(false, "Failed registering the event");
		}
		
	}
	
	
}
