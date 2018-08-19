package com.bussiness.events.domain.commands;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.bussiness.events.core.IBussinessEvent;
import com.bussiness.events.util.Constants;

@Entity(name="shipment_parcel_reconciliated")
@Table(name="shipment_parcel_reconciliated", 
indexes = { @Index(name = "bussiness_reference_idx", columnList = "bussiness_reference") } )
public class ShipmentParcelReconciliated implements IBussinessEvent<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id_shipment_parcel;*/
	
	@Id
	private String tracking_number;
	
	@Column(name="bussiness_reference")
	private String reference;
	
	public ShipmentParcelReconciliated(String tracking_number, String bussiness_reference) {
		super();
		this.tracking_number = tracking_number;
		this.reference = bussiness_reference;
	}

	public ShipmentParcelReconciliated() {
		super();
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getTracking_number() {
		return tracking_number;
	}

	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}
	
	/*public Integer getId_shipment_parcel() {
		return id_shipment_parcel;
	}

	public void setId_shipment_parcel(Integer id_shipment_parcel) {
		this.id_shipment_parcel = id_shipment_parcel;
	}*/

	@Override
	public String getPartitionKey() {
		return this.tracking_number;
	}

	@Override
	public String getTopic() {
		return Constants.EVENT_SHIPMENT_PARCEL_RECONCILIATED;
	}
	
	
}
