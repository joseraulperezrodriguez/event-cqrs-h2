package com.bussiness.events.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.bussiness.events.core.IMessageQueue;
import com.bussiness.events.core.IBussinessEvent;


public class MemoryMessageQueue<E extends IBussinessEvent<?>> implements IMessageQueue<E> {

	private Queue<E> queue;
		
	public MemoryMessageQueue() {
		this.queue = new ConcurrentLinkedQueue<E>();
	}
			
	@Override
	public boolean sendBussinessEvent(E bussinessEvent) {
		return this.queue.add(bussinessEvent);
	}
	
	public E poll() {
		return queue.poll();
	}	
	
	
}
