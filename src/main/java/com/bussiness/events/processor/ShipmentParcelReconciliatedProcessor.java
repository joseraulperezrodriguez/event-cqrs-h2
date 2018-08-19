package com.bussiness.events.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.core.IEventDispatcher;
import com.bussiness.events.core.IBussinessEventProcessor;
import com.bussiness.events.domain.commands.ShipmentParcelReconciliated;
import com.bussiness.events.domain.commands.ShipmentReconciliated;
import com.bussiness.events.impl.EventConsumerOrquestator;
import com.bussiness.events.impl.StoutLoggingMessageStrategy;
import com.bussiness.events.services.ShipmentParcelReconciliatedDBService;
import com.bussiness.events.services.ShipmentSuccessIntegrationDBService;
import com.bussiness.events.util.Constants;
import com.bussiness.events.util.Tuple;

@Service
public class ShipmentParcelReconciliatedProcessor implements IBussinessEventProcessor<ShipmentParcelReconciliated> {
	
	@Autowired
	private ShipmentParcelReconciliatedDBService shipmentParcelReconciliatedDBService;
	
	@Autowired
	private ShipmentSuccessIntegrationDBService shipmentSuccessIntegrationDBService;

	@Autowired
	private IEventDispatcher<ShipmentReconciliated> shipmentReconciliatedDispatcher;

	@Autowired
	private StoutLoggingMessageStrategy<ShipmentParcelReconciliated> logginFailMessageStrategy;

	
	@Autowired
	public ShipmentParcelReconciliatedProcessor() {
		EventConsumerOrquestator.<ShipmentParcelReconciliated>registerEventProcessor(this, Constants.EVENT_SHIPMENT_PARCEL_RECONCILIATED);
	}
	
	
	public boolean processEvent(ShipmentParcelReconciliated event) {
		try {
			int countByReference = shipmentParcelReconciliatedDBService.countByReference(event.getReference());
			if(countByReference == -1) {
				logginFailMessageStrategy.doStrategy(event);
				return false;
			} else {
				Tuple<Integer, Double> data = shipmentSuccessIntegrationDBService.getShipmentWeight(event.getReference()); 
				
				if(data == null) {
					logginFailMessageStrategy.doStrategy(event);
					return false;
				}
				
				if(countByReference == data.getA()) {
					ShipmentReconciliated shipmentReconciliated = new ShipmentReconciliated(event.getReference(), 
							data.getA(), data.getB());
					boolean dispatched = shipmentReconciliatedDispatcher.dispatchEvent(shipmentReconciliated);
					
					if(dispatched) {
						return true;
					} else {
						logginFailMessageStrategy.doStrategy(event);
						return false;
					}
				} else {
					return true;
				}
			}
			
		} catch(Exception e) {
			logginFailMessageStrategy.doStrategy(event);
			return false;
		}
	}
	
}
