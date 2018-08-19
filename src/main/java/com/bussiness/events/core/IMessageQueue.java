package com.bussiness.events.core;

public interface IMessageQueue<E extends IBussinessEvent<?>> {
	
	public boolean sendBussinessEvent(E event);

}
