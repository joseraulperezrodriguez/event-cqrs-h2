package com.bussiness.events.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.core.IEventDispatcher;
import com.bussiness.events.domain.commands.ShipmentSuccessIntegration;
import com.bussiness.events.impl.MemoryMessageQueue;
import com.bussiness.events.impl.MessageQueueProvider;
import com.bussiness.events.impl.StoutLoggingMessageStrategy;
import com.bussiness.events.services.ShipmentSuccessIntegrationDBService;
import com.bussiness.events.util.Constants;


@Service
public class ShipmentSuccessIntegrationDispatcher implements IEventDispatcher<ShipmentSuccessIntegration> {
	
	@Autowired
	private ShipmentSuccessIntegrationDBService shipmentSuccessIntegrationDBService;
	
	private MemoryMessageQueue<ShipmentSuccessIntegration> shipmentSuccessIntegrationQueue;
	
	@Autowired
	private StoutLoggingMessageStrategy<ShipmentSuccessIntegration> logginFailMessageStrategy;


	public ShipmentSuccessIntegrationDispatcher() {
		shipmentSuccessIntegrationQueue = MessageQueueProvider.<ShipmentSuccessIntegration>getOrRegisterQueue(Constants.EVENT_SHIPMENT_SUCCESS_INTEGRATION);
	}
	
	@Override
	public boolean dispatchEvent(ShipmentSuccessIntegration event) {

		boolean successEventStore = shipmentSuccessIntegrationDBService.insertShipmentSuccessIntegrationEvent(event);
		//System.out.println(successEventStore + " STORE");
		if(successEventStore) {
			boolean success = shipmentSuccessIntegrationQueue.sendBussinessEvent(event);
			//System.out.println(success + " EVENT");
			if(success) return true;
			logginFailMessageStrategy.doStrategy(event);
			
		} else {
			logginFailMessageStrategy.doStrategy(event);
		}
		return false;
		
	}

}
