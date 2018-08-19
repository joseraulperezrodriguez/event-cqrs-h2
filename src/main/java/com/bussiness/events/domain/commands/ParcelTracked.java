package com.bussiness.events.domain.commands;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bussiness.events.core.IBussinessEvent;
import com.bussiness.events.util.Constants;

@Entity(name="parcel_tracked")
@Table(name="parcel_tracked")
public class ParcelTracked implements IBussinessEvent<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id_parcel_tracked;
	
	private String tracking_number;
	
	private String status;	
	private double weight;
	
	public ParcelTracked(String tracking_number, String status, double weight) {
		super();
		this.tracking_number = tracking_number;
		this.status = status;
		this.weight = weight;
	}
	
	public ParcelTracked() {
		super();
	}

	public String getTracking_number() {
		return tracking_number;
	}

	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public Integer getPartitionKey() {
		return this.id_parcel_tracked;
	}

	@Override
	public String getTopic() {
		return Constants.EVENT_PARCEL_TRACKED;
	}
	
	
	
	
}
