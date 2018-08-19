package com.bussiness.events.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.core.IBussinessEventProcessor;
import com.bussiness.events.domain.commands.ShipmentReconciliated;
import com.bussiness.events.impl.EventConsumerOrquestator;
import com.bussiness.events.impl.StoutLoggingMessageStrategy;
import com.bussiness.events.services.ShipmentDBService;
import com.bussiness.events.util.Constants;

@Service
public class ShipmentReconciliatedProcessor implements IBussinessEventProcessor<ShipmentReconciliated> {

	@Autowired
	private ShipmentDBService shipmentDBService;
	
	@Autowired
	private StoutLoggingMessageStrategy<ShipmentReconciliated> loggingFailMessageStrategy;
	
	
	@Autowired
	public ShipmentReconciliatedProcessor() {
		EventConsumerOrquestator.<ShipmentReconciliated>registerEventProcessor(this, Constants.EVENT_SHIPMENT_RECONCILIATED);
	}
	
	public boolean processEvent(ShipmentReconciliated shipmentReconciliated) {
		loggingFailMessageStrategy.doStrategy(shipmentReconciliated);
		return shipmentDBService.updateState(shipmentReconciliated.getReference(), Constants.SHIPMENT_END_STATUS);
	}
	
}
