package com.bussiness.events.domain.commands;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bussiness.events.core.IBussinessEvent;
import com.bussiness.events.domain.query.Parcel;
import com.bussiness.events.util.Constants;
import com.bussiness.events.util.ParcelConverter;

@Entity(name="shipment_success_integration")
@Table(name="shipment_success_integration")
public class ShipmentSuccessIntegration implements IBussinessEvent<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String bussiness_reference;
	
	@Column(columnDefinition="CLOB")
	@Convert(converter = ParcelConverter.class)
	private Parcel[] parcels;
	
	private String carrier;
	
	private String service_id;
	
	private String integration_code;
	
	@Column(columnDefinition="CLOB")
	private String[] tracking_numbers;
	
	public ShipmentSuccessIntegration(String bussiness_reference, Parcel[] parcels, String carrier, String service_id,
			String integration_code, String[] tracking_numbers) {
		super();
		this.bussiness_reference = bussiness_reference;
		this.parcels = parcels;
		this.carrier = carrier;
		this.service_id = service_id;
		this.integration_code = integration_code;
		this.tracking_numbers = tracking_numbers;
	}
	
	public ShipmentSuccessIntegration() {
		super();
	}

	public String getBussiness_reference() {
		return bussiness_reference;
	}

	public void setBussiness_reference(String bussiness_reference) {
		this.bussiness_reference = bussiness_reference;
	}

	public Parcel[] getParcels() {
		return parcels;
	}

	public void setParcels(Parcel[] parcels) {
		this.parcels = parcels;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getIntegration_code() {
		return integration_code;
	}

	public void setIntegration_code(String integration_code) {
		this.integration_code = integration_code;
	}

	public String[] getTracking_numbers() {
		return tracking_numbers;
	}

	public void setTracking_numbers(String[] tracking_numbers) {
		this.tracking_numbers = tracking_numbers;
	}

	@Override
	public String getPartitionKey() {
		return this.bussiness_reference;
	}

	@Override
	public String getTopic() {
		return Constants.EVENT_SHIPMENT_SUCCESS_INTEGRATION;
	}
	
	
	
}
