package com.bussiness.events.util;

public class Constants {
	
	public static final double EPSILON = 0.001;
	
	public static final String SHIPMENT_START_STATUS = "pending";
	
	public static final String SHIPMENT_END_STATUS = "sent_for_concilliation";
	
	
	public static final String PARCEL_WAITING_IN_HUB = "WAITING IN HUB";
	
	public static final String PARCEL_REGISTERED = "REGISTERED";
	
	public static final String PARCEL_DEFAULT_FINAL_STATUS = "DELIVERED";

	public static final String EVENT_PARCEL_TRACKED = "parcel_tracked";
	
	public static final String EVENT_SHIPMENT_PARCEL_RECONCILIATED = "shipment_parcel_reconciliated";
	
	public static final String EVENT_SHIPMENT_RECONCILIATED = "shipment_reconciliated";
	
	public static final String EVENT_SHIPMENT_SUCCESS_INTEGRATION = "shipment_integration_success";
	

}
