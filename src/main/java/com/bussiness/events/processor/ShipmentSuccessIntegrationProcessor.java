package com.bussiness.events.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.core.IBussinessEventProcessor;
import com.bussiness.events.domain.commands.ShipmentSuccessIntegration;
import com.bussiness.events.domain.query.Parcel;
import com.bussiness.events.domain.query.ParcelStatus;
import com.bussiness.events.domain.query.Shipment;
import com.bussiness.events.impl.EventConsumerOrquestator;
import com.bussiness.events.impl.StoutLoggingMessageStrategy;
import com.bussiness.events.services.ShipmentSuccessIntegrationDBService;
import com.bussiness.events.util.Constants;

@Service
public class ShipmentSuccessIntegrationProcessor implements IBussinessEventProcessor<ShipmentSuccessIntegration> {

	@Autowired
	private ShipmentSuccessIntegrationDBService shipmentSuccessIntegrationDBService;
	
	@Autowired
	private StoutLoggingMessageStrategy<ShipmentSuccessIntegration> loggingFailMessageStrategy;
	
	@Autowired
	public ShipmentSuccessIntegrationProcessor() {
		EventConsumerOrquestator.<ShipmentSuccessIntegration>registerEventProcessor(this, Constants.EVENT_SHIPMENT_SUCCESS_INTEGRATION);
	}
	
	public boolean processEvent(ShipmentSuccessIntegration event) {
		
		Shipment shipment = new Shipment(event.getBussiness_reference(), 
				event.getParcels().length, Constants.SHIPMENT_START_STATUS);
		
		ParcelStatus[] parcelStatuses = new ParcelStatus[event.getParcels().length];
		
		for(int i = 0; i < event.getParcels().length ; i++) {
			Parcel p = event.getParcels()[i];
			p.setTracking_number(event.getTracking_numbers()[i]);
			p.setBussiness_reference(event.getBussiness_reference());
			
			ParcelStatus parcelStatus = new ParcelStatus(p.getTracking_number(), p.getBussiness_reference(),
					Constants.PARCEL_REGISTERED, 0);
			parcelStatuses[i] = parcelStatus;
		}
		
		try {
			shipmentSuccessIntegrationDBService.registerShipmentEntities(event, shipment, event.getParcels(),
					parcelStatuses);
			return true;
		} catch(Exception e) {
			loggingFailMessageStrategy.doStrategy(event);
			return false;
		}
	}
	
}
