package com.bussiness.events.gateway;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bussiness.events.data.repository.commands.IParcelTrackedRepository;
import com.bussiness.events.data.repository.commands.IShipmentParcelReconciliatedRepository;
import com.bussiness.events.data.repository.commands.IShipmentReconciliatedRepository;
import com.bussiness.events.data.repository.commands.IShipmentSuccessIntegrationRepository;
import com.bussiness.events.data.repository.query.IParcelRepository;
import com.bussiness.events.data.repository.query.IParcelStatusRepository;
import com.bussiness.events.data.repository.query.IShipmentRepository;
import com.bussiness.events.domain.commands.ParcelTracked;
import com.bussiness.events.domain.commands.ShipmentParcelReconciliated;
import com.bussiness.events.domain.commands.ShipmentReconciliated;
import com.bussiness.events.domain.commands.ShipmentSuccessIntegration;
import com.bussiness.events.domain.query.Parcel;
import com.bussiness.events.domain.query.ParcelStatus;
import com.bussiness.events.domain.query.Shipment;
import com.bussiness.events.util.RESTPonse;

@RestController
@RequestMapping("/testing")
public class Testing {

	@Autowired
	private IShipmentSuccessIntegrationRepository shipmentSuccessIntegrationRepository;

	@Autowired
	private IShipmentParcelReconciliatedRepository shipmentParcelReconciliatedRepository;
	
	@Autowired
	private IShipmentReconciliatedRepository shipmentReconciliatedRepository;
	
	@Autowired
	private IParcelTrackedRepository parcelTrackedRepository;

	
	@Autowired
	private IShipmentRepository shipmentRepository;
	
	@Autowired
	private IParcelRepository parcelRepository;
	
	@Autowired
	private IParcelStatusRepository parcelStatusRepository;
	
	
	
	@RequestMapping(value="get-shipments-reconciliated", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public RESTPonse getShipmentReconciliated() {
		try {
			Iterable<ShipmentReconciliated> iterable = shipmentReconciliatedRepository.findAll();
			List<ShipmentReconciliated> list = new ArrayList<ShipmentReconciliated>();
			iterable.forEach(sh -> list.add(sh));
			
			return new RESTPonse(true, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTPonse(false, "Failed registering the event");
		}
		
	}
	
	@RequestMapping(value="get-shipments-parcel-reconciliated", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public RESTPonse getShipmentParcelReconciliated() {
		try {
			Iterable<ShipmentParcelReconciliated> iterable = shipmentParcelReconciliatedRepository.findAll();
			List<ShipmentParcelReconciliated> list = new ArrayList<ShipmentParcelReconciliated>();
			iterable.forEach(sh -> list.add(sh));
			
			return new RESTPonse(true, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTPonse(false, "Failed registering the event");
		}
		
	}
	
	@RequestMapping(value="get-shipments-success-integration", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public RESTPonse getShipmentSuccessIntegration() {
		try {
			Iterable<ShipmentSuccessIntegration> iterable = shipmentSuccessIntegrationRepository.findAll();
			List<ShipmentSuccessIntegration> list = new ArrayList<ShipmentSuccessIntegration>();
			iterable.forEach(sh -> list.add(sh));
			
			return new RESTPonse(true, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTPonse(false, "Failed registering the event");
		}
		
	}

		
	@RequestMapping(value="get-shipments", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public RESTPonse getShipments() {
		try {
			Iterable<Shipment> iterable = shipmentRepository.findAll();
			List<Shipment> list = new ArrayList<Shipment>();
			iterable.forEach(sh -> list.add(sh));
			
			return new RESTPonse(true, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTPonse(false, "Failed registering the event");
		}
		
	}
	
	@RequestMapping(value="get-parcels", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public RESTPonse getParcels() {
		try {
			Iterable<Parcel> iterable = parcelRepository.findAll();
			List<Parcel> list = new ArrayList<Parcel>();
			iterable.forEach(sh -> list.add(sh));
			
			return new RESTPonse(true, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTPonse(false, "Failed registering the event");
		}
		
	}
	
	@RequestMapping(value="get-parcels-status", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public RESTPonse getParcelsStatus() {
		try {
			Iterable<ParcelStatus> iterable = parcelStatusRepository.findAll();
			List<ParcelStatus> list = new ArrayList<ParcelStatus>();
			iterable.forEach(sh -> list.add(sh));
			
			return new RESTPonse(true, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTPonse(false, "Failed registering the event");
		}
		
	}
	
	@RequestMapping(value="get-parcels-tracked", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public RESTPonse getParcelsTracked() {
		try {
			Iterable<ParcelTracked> iterable = parcelTrackedRepository.findAll();
			List<ParcelTracked> list = new ArrayList<ParcelTracked>();
			iterable.forEach(sh -> list.add(sh));
			
			return new RESTPonse(true, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new RESTPonse(false, "Failed registering the event");
		}
		
	}
		
	
}
