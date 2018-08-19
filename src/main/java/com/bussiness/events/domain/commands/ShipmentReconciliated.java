package com.bussiness.events.domain.commands;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bussiness.events.core.IBussinessEvent;
import com.bussiness.events.util.Constants;

@Entity(name="shipment_reconciliated")
@Table(name="shipment_reconciliated")
public class ShipmentReconciliated implements IBussinessEvent<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String reference;
	
	private int parcels;
	
	private double weight;

	
	public ShipmentReconciliated(String reference, int parcels, double weight) {
		super();
		this.reference = reference;
		this.parcels = parcels;
		this.weight = weight;
	}
	
	public ShipmentReconciliated() {
		super();
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public int getParcels() {
		return parcels;
	}

	public void setParcels(int parces) {
		this.parcels = parces;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String getPartitionKey() {
		return this.reference;
	}

	@Override
	public String getTopic() {
		return Constants.EVENT_SHIPMENT_RECONCILIATED;
	}
	
	
	

}
