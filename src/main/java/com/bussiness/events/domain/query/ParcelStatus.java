package com.bussiness.events.domain.query;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="parcel_status")
@Table(name="parcel_status")
public class ParcelStatus {
	
	@Id
	private String tracking_number;//parcel id

	private String bussiness_reference;
	
	private double weight;
	
	private String status;

	
	public ParcelStatus(String tracking_number, String bussiness_reference, String status, double weight) {
		super();
		this.status = status;
		this.bussiness_reference = bussiness_reference;
		this.tracking_number = tracking_number;
		this.weight = weight;
	}
	
	public ParcelStatus() {
		super();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBussiness_reference() {
		return bussiness_reference;
	}

	public void setBussiness_reference(String bussiness_reference) {
		this.bussiness_reference = bussiness_reference;
	}

	public String getTracking_number() {
		return tracking_number;
	}

	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	
}
