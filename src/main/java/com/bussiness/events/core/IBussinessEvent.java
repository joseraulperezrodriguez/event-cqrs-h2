package com.bussiness.events.core;

import java.io.Serializable;

public interface IBussinessEvent<C> extends Serializable {
	
	public C getPartitionKey();
	
	public String getTopic();
	
}
