package com.bussiness.events.domain.query;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="parcel")
@Table(name="parcel")
public class Parcel implements Serializable	 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String tracking_number;
	
	private double weight;
	
	private int width;
	
	private int height;
	
	@Column(name="length")
	private int length;
	
	private String final_status;
	
	private String bussiness_reference;
	
	public Parcel(String tracking_number, double weight, int width, int height, int length, String bussiness_reference) {
		super();
		this.tracking_number = tracking_number;
		this.weight = weight;
		this.width = width;
		this.height = height;
		this.length = length;
		this.bussiness_reference = bussiness_reference;
	}
	
	public Parcel() {
		super();
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getBussiness_reference() {
		return bussiness_reference;
	}

	public void setBussiness_reference(String bussiness_reference) {
		this.bussiness_reference = bussiness_reference;
	}

	public String getFinal_status() {
		return final_status;
	}

	public void setFinal_status(String final_status) {
		this.final_status = final_status;
	}
	
	
	
}
