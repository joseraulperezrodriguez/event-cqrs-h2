package com.bussiness.events.core;

public interface IEventDispatcher<E> {
	
	public boolean dispatchEvent(E event);

	
}
