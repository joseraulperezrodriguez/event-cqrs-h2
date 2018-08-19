package com.bussiness.events.core;

public interface IFailEventDispatchStrategy<E extends IBussinessEvent<?>> {

	public void doStrategy(E event);
	
}
