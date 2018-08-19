package com.bussiness.events.impl;

import java.util.HashMap;
import java.util.Map;

import com.bussiness.events.core.IBussinessEvent;

public class MessageQueueProvider {
	
	public static Map<String, MemoryMessageQueue<? extends IBussinessEvent<?>> > availableQueues;
	
	static {
		availableQueues = new HashMap<String, MemoryMessageQueue<? extends IBussinessEvent<?>>>();
	}
	
	
	@SuppressWarnings("unchecked")
	public static <E extends IBussinessEvent<?>> MemoryMessageQueue<E> getOrRegisterQueue(String topic) {
		MemoryMessageQueue<? extends IBussinessEvent<?>> queue = availableQueues.get(topic);
		if(queue == null) {
			queue = new MemoryMessageQueue<E>();
			availableQueues.put(topic, queue);
		}
		
		return (MemoryMessageQueue<E>) queue;
	}
	
}
