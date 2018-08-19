package com.bussiness.events.impl;

import java.util.HashMap;
import java.util.Map;

import com.bussiness.events.core.IBussinessEvent;
import com.bussiness.events.core.IBussinessEventProcessor;

public class EventConsumerOrquestator {

	private static Map<String, EventConsumer<? extends IBussinessEvent<?>>> consumerTopics;
	
	static {
		consumerTopics = new HashMap<String, EventConsumer<? extends IBussinessEvent<?>>>();
	}
		
	
	@SuppressWarnings("unchecked")
	public static <E extends IBussinessEvent<?>> void registerEventProcessor(IBussinessEventProcessor<E> eventProcessor, String topic) {
		
		MemoryMessageQueue<E> queue = MessageQueueProvider.getOrRegisterQueue(topic);
		
		EventConsumer<E> eventConsumer = (EventConsumer<E>) consumerTopics.get(topic);
		
		if(eventConsumer == null) {
			eventConsumer = new EventConsumer<E>(queue, eventProcessor);
			consumerTopics.put(topic, eventConsumer);
		} else {
			eventConsumer.addEventProcessor(eventProcessor);
		}
		
	}
	
}
