package com.bussiness.events.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bussiness.events.data.repository.commands.IParcelTrackedRepository;
import com.bussiness.events.data.repository.query.IParcelRepository;
import com.bussiness.events.data.repository.query.IParcelStatusRepository;
import com.bussiness.events.data.repository.query.IShipmentRepository;
import com.bussiness.events.domain.commands.ParcelTracked;
import com.bussiness.events.domain.commands.ShipmentParcelReconciliated;
import com.bussiness.events.domain.commands.ShipmentReconciliated;
import com.bussiness.events.domain.commands.ShipmentSuccessIntegration;
import com.bussiness.events.domain.query.Carrier;
import com.bussiness.events.domain.query.Parcel;
import com.bussiness.events.domain.query.ParcelStatus;
import com.bussiness.events.domain.query.Shipment;
import com.bussiness.events.services.CarrierDBService;
import com.bussiness.events.services.ParcelDBService;
import com.bussiness.events.services.ParcelStatusDBService;
import com.bussiness.events.services.ParcelTrackedDBService;
import com.bussiness.events.services.ShipmentDBService;
import com.bussiness.events.services.ShipmentParcelReconciliatedDBService;
import com.bussiness.events.services.ShipmentReconciliatedDBService;
import com.bussiness.events.services.ShipmentSuccessIntegrationDBService;
import com.bussiness.events.util.Constants;
import com.bussiness.events.util.Tuple;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdererDataTest {
	
    //@Autowired private MockMvc mockMvc;

    //@Autowired ObjectMapper objectMapper;

    @Autowired CarrierDBService carrierDBService;
    
    @Autowired ParcelDBService parcelDBService;
    
    @Autowired IParcelRepository parcelRepository;
    
    @Autowired ParcelStatusDBService parcelStatusDBService;
    
    @Autowired ParcelTrackedDBService parcelTrackedDBService;
	
    @Autowired IParcelTrackedRepository parcelTrackedRepository;
    
    @Autowired IParcelStatusRepository parcelStatusRepository;
    
    @Autowired IShipmentRepository shipmentRepository;
    
    @Autowired ShipmentDBService shipmentDBService;
    
    @Autowired ShipmentParcelReconciliatedDBService shipmentParcelReconciliatedDBService;
    
    @Autowired ShipmentReconciliatedDBService shipmentReconciliatedDBService;
    
    @Autowired ShipmentSuccessIntegrationDBService shipmentSuccessIntegrationDBService;
    
    String reference;
    
    String tracking_number1;
    
    String tracking_number2;
    
    String carrier_name;
    
    String final_status;
    
    String parcel_status;
    
 	@Before
	public void initialize() {
 		reference = "AAA";
 		tracking_number1 = "BBB";
 		tracking_number2 = "CCC";
 		carrier_name = "UPS";
 		final_status = "DELIVERED";
 		parcel_status = "WAITING";
	}

	@Test
	public void o001carrierInsert() {
		Carrier carrier = new Carrier(null, carrier_name, final_status);
		carrier = carrierDBService.insertCarrier(carrier);		
		assertTrue(carrier != null && carrier.getCarrier_id() != null);		
	}
	
	@Test
	public void o002carrierGetName() {
		String get = carrierDBService.getFinalStatus(carrier_name);
		assertTrue(get != null && get.equals(final_status));
	}
	
	@Test
	public void o003getParcelById() {
		Parcel parcel = new Parcel(reference,0.5,1,1,1,tracking_number1);
		parcelRepository.save(parcel);
		
		parcel = parcelDBService.getById(parcel.getTracking_number());		
		assertTrue(parcel != null);		
	}
	
	@Test
	public void o004parcelTrackedInserted() {
		ParcelTracked parcelTracked = new ParcelTracked(tracking_number1, final_status, 0.8);		
		boolean temp = parcelTrackedDBService.insertParcelTrackedEvent(parcelTracked);
		assertTrue(temp);		
	}
	
	@Test
	public void o005parcelUpdateState() {
		Iterable<ParcelTracked> parcelTrackeds = parcelTrackedRepository.findAll();
		
		ParcelTracked parcelTracked = parcelTrackeds.iterator().next();
		
		ParcelStatus parcelStatus = new ParcelStatus(
				tracking_number1, reference, parcel_status, 0.5);
		
		boolean inserted = parcelStatusDBService.insertParcelStatus(parcelStatus);
		
		boolean updated = parcelStatusDBService.parcelStatusUpdate(parcelTracked);
		
		ParcelStatus status = parcelStatusRepository.findOne(tracking_number1);
		
		boolean updatedValues = status.getStatus().equals(final_status) 
				&& status.getWeight() == 0.8;
		assertTrue(inserted && updated && updatedValues);
	}
	
	@Test 
	public void o006insertShipment() {
		Shipment shipment = new Shipment(reference, 1, Constants.SHIPMENT_START_STATUS);
		shipmentRepository.save(shipment);
		assertTrue(true);
	}
	
	@Test
	public void o007updateShipmentStatus() {				
		boolean updated = shipmentDBService.updateState(reference, Constants.SHIPMENT_END_STATUS);		
		Shipment shipment = shipmentRepository.findOne(reference);		
		assertTrue(updated && shipment.getState().equals(Constants.SHIPMENT_END_STATUS));		
	}
	
	@Test
	public void o008insertShipmentParcelReconciliated() {
		ShipmentParcelReconciliated shipmentParcelReconciliated1 = new
				ShipmentParcelReconciliated(tracking_number1, reference);
		
		ShipmentParcelReconciliated shipmentParcelReconciliated2 = new
				ShipmentParcelReconciliated(tracking_number2, reference);
		
		boolean inserted1 = shipmentParcelReconciliatedDBService.
				insertShipmentParcelReconciliatedEvent(shipmentParcelReconciliated1);
		
		boolean inserted2 = shipmentParcelReconciliatedDBService.
				insertShipmentParcelReconciliatedEvent(shipmentParcelReconciliated2);
		
		assert(inserted1 && inserted2);
	}
	
	@Test
	public void o009countByReference() {
		int count = shipmentParcelReconciliatedDBService.countByReference(reference);
		assertTrue(count == 2);
	}
	
	@Test
	public void o010insertShipmentReconciliatedEvent() {
		ShipmentReconciliated shipmentReconciliated = new ShipmentReconciliated(reference, 2, 2.5);
		
		boolean inserted = shipmentReconciliatedDBService.
				insertShipmentReconciliatedEvent(shipmentReconciliated);
		
		assert(inserted);
	}
	
	@Test
	public void o011insertSuccessIntegration() {
		
		parcelRepository.deleteAll();
		parcelStatusRepository.deleteAll();
		shipmentRepository.deleteAll();
		
		ShipmentSuccessIntegration shipmentSuccessIntegration =
				new ShipmentSuccessIntegration();
		shipmentSuccessIntegration.setCarrier(carrier_name);
		shipmentSuccessIntegration.setIntegration_code("Z1");
		shipmentSuccessIntegration.setBussiness_reference(reference);
		shipmentSuccessIntegration.setService_id("service_id");
		
		Parcel parcel1 = new Parcel(reference,0.5,1,1,1,tracking_number1);
		Parcel parcel2 = new Parcel(reference,0.75,2,2,2,tracking_number2);
		
		Parcel[] parcels = new Parcel[2];
		parcels[0] = parcel1;
		parcels[1] = parcel2;
		
		
		String[] tracking_numbers = new String[] {tracking_number1, tracking_number2};
		
		shipmentSuccessIntegration.setParcels(parcels);
		shipmentSuccessIntegration.setTracking_numbers(tracking_numbers);
		
		boolean inserted = shipmentSuccessIntegrationDBService.
				insertShipmentSuccessIntegrationEvent(shipmentSuccessIntegration);
		
		assert(inserted);
				
	}
	
	@Test
	public void o012registerSuccessIntegration() {

		Tuple<Integer, Double> test = shipmentSuccessIntegrationDBService.getShipmentWeight(reference);
						
		assertTrue(test.getA() == 2 && (test.getB() == (double)(0.75 + 0.5)));
	}
	
	

}
