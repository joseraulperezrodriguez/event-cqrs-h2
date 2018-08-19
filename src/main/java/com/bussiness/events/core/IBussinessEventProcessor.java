package com.bussiness.events.core;

public interface IBussinessEventProcessor<E extends IBussinessEvent<?>>  {
	public boolean processEvent(E event);
}
