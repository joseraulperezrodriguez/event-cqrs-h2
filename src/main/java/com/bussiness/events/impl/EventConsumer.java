package com.bussiness.events.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.bussiness.events.core.IBussinessEvent;
import com.bussiness.events.core.IBussinessEventProcessor;


public class EventConsumer<E extends IBussinessEvent<?>> extends Thread {

	
	private MemoryMessageQueue<E> memoryMessageQueue;
	
	private List<IBussinessEventProcessor<E>> eventProcessors;
	
	public EventConsumer(MemoryMessageQueue<E> memoryMessageQueue, IBussinessEventProcessor<E> eventProcessor) {
		this.memoryMessageQueue = memoryMessageQueue;
		this.eventProcessors = new CopyOnWriteArrayList<IBussinessEventProcessor<E>>();
		this.eventProcessors.add(eventProcessor);
		this.start();
	}
	
	public void addEventProcessor(IBussinessEventProcessor<E> eventProcessor) {
		eventProcessors.add(eventProcessor);
	}
	
	@Override
	public void run() {
		while(true) {
			E element =  memoryMessageQueue.poll();
			if(element == null) continue;
			
			for(IBussinessEventProcessor<E> processor : eventProcessors) {
				processor.processEvent(element);
			}
			
		}
	}

	public MemoryMessageQueue<E> getMemoryMessageQueue() {
		return memoryMessageQueue;
	}

	public void setMemoryMessageQueue(MemoryMessageQueue<E> memoryMessageQueue) {
		this.memoryMessageQueue = memoryMessageQueue;
	}

}
