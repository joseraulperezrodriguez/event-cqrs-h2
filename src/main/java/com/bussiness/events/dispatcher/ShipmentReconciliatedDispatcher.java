package com.bussiness.events.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.core.IEventDispatcher;
import com.bussiness.events.domain.commands.ShipmentReconciliated;
import com.bussiness.events.impl.MemoryMessageQueue;
import com.bussiness.events.impl.MessageQueueProvider;
import com.bussiness.events.impl.StoutLoggingMessageStrategy;
import com.bussiness.events.services.ShipmentReconciliatedDBService;
import com.bussiness.events.util.Constants;

@Service
public class ShipmentReconciliatedDispatcher implements IEventDispatcher<ShipmentReconciliated> {

	@Autowired
	private ShipmentReconciliatedDBService shipmentReconciliatedDBService;
	
	private MemoryMessageQueue<ShipmentReconciliated> shipmentReconciliatedQueue;
	
	@Autowired
	private StoutLoggingMessageStrategy<ShipmentReconciliated> logginFailMessageStrategy;
	
	
	public ShipmentReconciliatedDispatcher() {
		shipmentReconciliatedQueue = MessageQueueProvider.<ShipmentReconciliated>getOrRegisterQueue(Constants.EVENT_SHIPMENT_RECONCILIATED);
	}
	
	@Override
	public boolean dispatchEvent(ShipmentReconciliated event) {
		boolean successEventStore = shipmentReconciliatedDBService.insertShipmentReconciliatedEvent(event);
		if(successEventStore) {
			boolean success = shipmentReconciliatedQueue.sendBussinessEvent(event);
			if(success) return true;
			logginFailMessageStrategy.doStrategy(event);
			
		} else {
			logginFailMessageStrategy.doStrategy(event);
		}
		return false;

	}

	
}
