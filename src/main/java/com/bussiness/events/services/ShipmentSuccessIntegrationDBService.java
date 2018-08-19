package com.bussiness.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bussiness.events.data.repository.commands.IShipmentSuccessIntegrationRepository;
import com.bussiness.events.data.repository.query.ICarrierRepository;
import com.bussiness.events.data.repository.query.IParcelRepository;
import com.bussiness.events.data.repository.query.IParcelStatusRepository;
import com.bussiness.events.data.repository.query.IShipmentRepository;
import com.bussiness.events.domain.commands.ShipmentSuccessIntegration;
import com.bussiness.events.domain.query.Carrier;
import com.bussiness.events.domain.query.Parcel;
import com.bussiness.events.domain.query.ParcelStatus;
import com.bussiness.events.domain.query.Shipment;
import com.bussiness.events.util.Constants;
import com.bussiness.events.util.Tuple;

@Service
public class ShipmentSuccessIntegrationDBService {

	@Autowired
	private IShipmentSuccessIntegrationRepository shipmentSuccessIntegrationRepository;
	
	@Autowired
	private IShipmentRepository shipmentRepository;
	
	@Autowired
	private IParcelStatusRepository parcelStatusRepository;
	
	@Autowired
	private IParcelRepository parcelRepository;
	
	@Autowired
	private ICarrierRepository carrierRepository;
	
	
	public boolean insertShipmentSuccessIntegrationEvent(ShipmentSuccessIntegration shipmentSuccessIntegration) {
		try {
			shipmentSuccessIntegrationRepository.save(shipmentSuccessIntegration);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Tuple<Integer, Double> getShipmentWeight(String reference) {
		try {
			ShipmentSuccessIntegration shipment = shipmentSuccessIntegrationRepository.findOne(reference);
			
			int parcels = shipment.getParcels().length;
			double weight = 0;
			for(Parcel p : shipment.getParcels()) {
				weight += p.getWeight();
			}
			
			return new Tuple<Integer, Double>(parcels, weight);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional
	public void registerShipmentEntities(ShipmentSuccessIntegration shipmentSuccessIntegration, 
			Shipment shipment, Parcel[] parcels, ParcelStatus[] parcelSatuses) {
		
		shipmentRepository.save(shipment);
		
		Carrier carrier = carrierRepository.getByName(shipmentSuccessIntegration.getCarrier());
		String parcelFinalStatusToCheck = Constants.PARCEL_DEFAULT_FINAL_STATUS;
		if(carrier != null) {
			parcelFinalStatusToCheck = carrier.getFinal_status();
		}
		
		for(Parcel p : parcels) {
			p.setFinal_status(parcelFinalStatusToCheck);
			parcelRepository.save(p);
		}
		
		for(ParcelStatus ps : parcelSatuses) {
			parcelStatusRepository.save(ps);
		}
		
	}
	
	
}
