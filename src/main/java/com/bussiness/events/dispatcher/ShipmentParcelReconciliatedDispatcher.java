package com.bussiness.events.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.core.IEventDispatcher;
import com.bussiness.events.domain.commands.ShipmentParcelReconciliated;
import com.bussiness.events.impl.MemoryMessageQueue;
import com.bussiness.events.impl.MessageQueueProvider;
import com.bussiness.events.impl.StoutLoggingMessageStrategy;
import com.bussiness.events.services.ShipmentParcelReconciliatedDBService;
import com.bussiness.events.util.Constants;

@Service
public class ShipmentParcelReconciliatedDispatcher implements IEventDispatcher<ShipmentParcelReconciliated> {

	@Autowired
	private ShipmentParcelReconciliatedDBService shipmentParcelReconciliatedDBService;
	
	private MemoryMessageQueue<ShipmentParcelReconciliated> shipmentParcelReconciliatedQueue;
	
	@Autowired
	private StoutLoggingMessageStrategy<ShipmentParcelReconciliated> logginFailMessageStrategy;
	
	public ShipmentParcelReconciliatedDispatcher() {
		shipmentParcelReconciliatedQueue = MessageQueueProvider.<ShipmentParcelReconciliated>getOrRegisterQueue
				(Constants.EVENT_SHIPMENT_PARCEL_RECONCILIATED);
	}
	
	@Override
	public boolean dispatchEvent(ShipmentParcelReconciliated event) {
		boolean successEventStore = shipmentParcelReconciliatedDBService.insertShipmentParcelReconciliatedEvent(event);
		if(successEventStore) {
			boolean success = shipmentParcelReconciliatedQueue.sendBussinessEvent(event);
			if(success) return true;
			logginFailMessageStrategy.doStrategy(event);
			
		} else {
			logginFailMessageStrategy.doStrategy(event);
		}
		return false;

	}

	
}
