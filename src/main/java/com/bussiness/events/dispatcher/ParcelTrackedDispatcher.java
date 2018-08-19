package com.bussiness.events.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.events.core.IEventDispatcher;
import com.bussiness.events.domain.commands.ParcelTracked;
import com.bussiness.events.impl.MemoryMessageQueue;
import com.bussiness.events.impl.MessageQueueProvider;
import com.bussiness.events.impl.StoutLoggingMessageStrategy;
import com.bussiness.events.services.ParcelTrackedDBService;
import com.bussiness.events.util.Constants;


@Service
public class ParcelTrackedDispatcher implements IEventDispatcher<ParcelTracked> {
	
	@Autowired
	private ParcelTrackedDBService parcelTrackedDBService;
	
	private MemoryMessageQueue<ParcelTracked> parcelTrackedQueue;
	
	@Autowired
	private StoutLoggingMessageStrategy<ParcelTracked> logginFailMessageStrategy;
	
	public ParcelTrackedDispatcher() {
		parcelTrackedQueue = MessageQueueProvider.<ParcelTracked>getOrRegisterQueue(Constants.EVENT_PARCEL_TRACKED);
	}
	
	
	@Override	
	public boolean dispatchEvent(ParcelTracked event) {

		boolean successEventStore = parcelTrackedDBService.insertParcelTrackedEvent(event);
		if(successEventStore) {
			boolean success = parcelTrackedQueue.sendBussinessEvent(event);
			if(success) return true;
			logginFailMessageStrategy.doStrategy(event);
			
		} else {
			logginFailMessageStrategy.doStrategy(event);
		}
		return false;
		
	}

}
