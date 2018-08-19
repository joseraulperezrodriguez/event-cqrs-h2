package com.bussiness.events.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bussiness.events.domain.query.Carrier;
import com.bussiness.events.services.CarrierDBService;

@Component
public class RunAtStartup implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private CarrierDBService carrierDBService;

	 @Override
	  public void onApplicationEvent(final ApplicationReadyEvent event) {
		 
		 Carrier carrier = new Carrier(null, "UPS", Constants.PARCEL_DEFAULT_FINAL_STATUS);
		 carrierDBService.insertCarrier(carrier);
		 
		 System.out.println("STARTED -----------------------------------");
	    
	  }
	
}
