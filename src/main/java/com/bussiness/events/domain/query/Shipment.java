package com.bussiness.events.domain.query;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="shipment")
@Table(name="shipment")
public class Shipment {

	@Id
	private String reference;
	
	private int parcels;
	
	private String state;	
	
	public Shipment(String reference, int parcels, String state) {
		super();
		this.reference = reference;
		this.parcels = parcels;
		this.state = state;
	}
	
	public Shipment() {
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

	public void setParcels(int parcels) {
		this.parcels = parcels;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
