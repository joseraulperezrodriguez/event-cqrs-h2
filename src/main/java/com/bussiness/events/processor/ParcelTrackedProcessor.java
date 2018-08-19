package com.bussiness.events.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.core.IEventDispatcher;
import com.bussiness.events.core.IBussinessEvent;
import com.bussiness.events.core.IBussinessEventProcessor;
import com.bussiness.events.domain.commands.ParcelTracked;
import com.bussiness.events.domain.commands.ShipmentParcelReconciliated;
import com.bussiness.events.domain.query.Parcel;
import com.bussiness.events.impl.EventConsumerOrquestator;
import com.bussiness.events.impl.StoutLoggingMessageStrategy;
import com.bussiness.events.services.ParcelDBService;
import com.bussiness.events.services.ParcelStatusDBService;
import com.bussiness.events.util.Constants;
import com.bussiness.events.util.StaticOps;

@Service
public class ParcelTrackedProcessor implements IBussinessEventProcessor<ParcelTracked> {

	@Autowired
	private ParcelDBService parcelDBService;
	
	@Autowired
	private ParcelStatusDBService parcelStatusDBService;
	
	@Autowired
	private IEventDispatcher<ShipmentParcelReconciliated> shipmentParcelReconciliatedDispatcher; 
	
	@Autowired
	private StoutLoggingMessageStrategy<IBussinessEvent<?>> logginFailMessageStrategy;
	
	public ParcelTrackedProcessor() {
		EventConsumerOrquestator.<ParcelTracked>registerEventProcessor(this, Constants.EVENT_PARCEL_TRACKED);
	}
	
	
	public boolean processEvent(ParcelTracked parcelTracked) {
		boolean success = parcelStatusDBService.parcelStatusUpdate(parcelTracked);
		if(success) {
			Parcel parcel = parcelDBService.getById(parcelTracked.getTracking_number());
			if(parcel == null) {
				logginFailMessageStrategy.doStrategy(parcelTracked);
				return false;
			}			
			
			if(StaticOps.compareDouble(parcel.getWeight(),parcelTracked.getWeight()) && 
					parcelTracked.getStatus().equals(parcel.getFinal_status())) {
				
				ShipmentParcelReconciliated shipmentParcelReconciliated = 
						new ShipmentParcelReconciliated(parcel.getTracking_number(), parcel.getBussiness_reference());
				
				boolean dispatched = shipmentParcelReconciliatedDispatcher.dispatchEvent(
						shipmentParcelReconciliated);
				
				if(dispatched) {
					return true;
				} else {
					logginFailMessageStrategy.doStrategy(shipmentParcelReconciliated);
					return false;
				}
			} else {
				return true;
			}
		} else {
			logginFailMessageStrategy.doStrategy(parcelTracked);
			return false;
		}
	}
	
}
