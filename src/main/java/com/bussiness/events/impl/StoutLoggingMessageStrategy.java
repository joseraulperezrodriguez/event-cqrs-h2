package com.bussiness.events.impl;

import org.springframework.stereotype.Service;

import com.bussiness.events.core.IFailEventDispatchStrategy;
import com.bussiness.events.core.IBussinessEvent;

@Service
public class StoutLoggingMessageStrategy<E extends IBussinessEvent<?>> implements IFailEventDispatchStrategy<E> {

	@Override
	public void doStrategy(E bussinessEvent) {
		System.out.println("Could not send message for id, " +
				bussinessEvent.getPartitionKey());
	}

}
