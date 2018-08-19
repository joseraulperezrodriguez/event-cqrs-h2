package com.bussiness.events.test;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bussiness.events.data.repository.commands.IShipmentParcelReconciliatedRepository;
import com.bussiness.events.data.repository.commands.IShipmentReconciliatedRepository;
import com.bussiness.events.data.repository.query.IParcelRepository;
import com.bussiness.events.data.repository.query.IParcelStatusRepository;
import com.bussiness.events.data.repository.query.IShipmentRepository;
import com.bussiness.events.dispatcher.ParcelTrackedDispatcher;
import com.bussiness.events.dispatcher.ShipmentSuccessIntegrationDispatcher;
import com.bussiness.events.domain.commands.ParcelTracked;
import com.bussiness.events.domain.commands.ShipmentParcelReconciliated;
import com.bussiness.events.domain.commands.ShipmentReconciliated;
import com.bussiness.events.domain.commands.ShipmentSuccessIntegration;
import com.bussiness.events.domain.query.Parcel;
import com.bussiness.events.domain.query.ParcelStatus;
import com.bussiness.events.domain.query.Shipment;
import com.bussiness.events.services.CarrierDBService;
import com.bussiness.events.services.ShipmentSuccessIntegrationDBService;
import com.bussiness.events.util.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventProcessorTest {

	@Autowired ShipmentSuccessIntegrationDBService shipmentSuccessIntegrationDBService;

	@Autowired ShipmentSuccessIntegrationDispatcher shipmentSuccessIntegrationDispatcher;

	@Autowired ParcelTrackedDispatcher parcelTrackedDispatcher;

	@Autowired IShipmentRepository shipmentRepository;

	@Autowired IParcelRepository parcelRepository;

	@Autowired IParcelStatusRepository parcelStatusRepository;

	@Autowired IShipmentParcelReconciliatedRepository shipmentParcelReconciliatedRepository;

	@Autowired IShipmentReconciliatedRepository shipmentReconciliatedRepository;

	@Autowired CarrierDBService carrierDBService;

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

	public ShipmentSuccessIntegration instance() {
		ShipmentSuccessIntegration shipmentSuccessIntegration =
				new ShipmentSuccessIntegration();
		shipmentSuccessIntegration.setCarrier(carrier_name);
		shipmentSuccessIntegration.setIntegration_code("Z1");
		shipmentSuccessIntegration.setBussiness_reference(reference);
		shipmentSuccessIntegration.setService_id("service_id");

		Parcel parcel1 = new Parcel(tracking_number1, 1.5,1,1,1,reference);
		Parcel parcel2 = new Parcel(tracking_number2,2.5,2,2,2,reference);

		Parcel[] parcels = new Parcel[2];
		parcels[0] = parcel1;
		parcels[1] = parcel2;


		String[] tracking_numbers = new String[] {tracking_number1, tracking_number2};

		shipmentSuccessIntegration.setParcels(parcels);
		shipmentSuccessIntegration.setTracking_numbers(tracking_numbers);

		return shipmentSuccessIntegration;
	}

	@Test
	public void fullEventProcessBasicCase() throws InterruptedException {

		//Check success integration dispatcher
		ShipmentSuccessIntegration successIntegration = instance();

		boolean integrationDispatched = shipmentSuccessIntegrationDispatcher.dispatchEvent(successIntegration);

		assert(integrationDispatched);

		Thread.sleep(5 * 1000);

		//Check shipment success integration processor

		Iterable<Shipment> shipments = shipmentRepository.findAll();
		final AtomicInteger countShipment = new AtomicInteger(0);
		shipments.forEach(arg0 -> countShipment.incrementAndGet());

		assert(countShipment.get() == 1);

		String final_status = carrierDBService.getFinalStatus(successIntegration.getCarrier());

		Iterable<Parcel> parcels = parcelRepository.findAll();
		final AtomicInteger countParcels = new AtomicInteger(0);
		parcels.forEach(arg0 -> { countParcels.incrementAndGet(); assert(arg0.getFinal_status().equals(final_status)); });

		assert(countParcels.get() == 2);

		Iterable<ParcelStatus> parcelsStatuses = parcelStatusRepository.findAll();
		final AtomicInteger countParcelsStatuses = new AtomicInteger(0);
		parcelsStatuses.forEach(arg0 -> { countParcelsStatuses.incrementAndGet(); assert(arg0.getStatus().equals(Constants.PARCEL_REGISTERED)); } );

		assert(countParcelsStatuses.get() == 2);


		//Check parcel tracked dispatcher
		ParcelTracked parcelTracked = new ParcelTracked(tracking_number1, Constants.PARCEL_WAITING_IN_HUB, 1.3);
		boolean parcelTrackedDispatched = parcelTrackedDispatcher.dispatchEvent(parcelTracked);

		assert(parcelTrackedDispatched);

		Thread.sleep(5 * 1000);

		//Check parcel tracked processor
		ParcelStatus parcelStatus = parcelStatusRepository.findOne(tracking_number1);
		boolean sameWeightAndStatus = parcelStatus.getStatus().
				equals(parcelTracked.getStatus()) && parcelStatus.getWeight() == parcelTracked.getWeight();

		assert(sameWeightAndStatus);

		ShipmentParcelReconciliated notNull1 = shipmentParcelReconciliatedRepository.findOne(tracking_number1);
		ShipmentReconciliated notNull2 = shipmentReconciliatedRepository.findOne(reference);

		assert(notNull1 == null && notNull2 == null);

		parcelTracked = new ParcelTracked(tracking_number1, Constants.PARCEL_DEFAULT_FINAL_STATUS, 1.5);
		parcelTrackedDispatched = parcelTrackedDispatcher.dispatchEvent(parcelTracked);
		assert(parcelTrackedDispatched);

		Thread.sleep(5*1000);

		//Check parcel tracked processor
		parcelStatus = parcelStatusRepository.findOne(tracking_number1);
		sameWeightAndStatus = parcelStatus.getStatus().
				equals(parcelTracked.getStatus()) && parcelStatus.getWeight() == parcelTracked.getWeight();

		assert(sameWeightAndStatus);

		notNull1 = shipmentParcelReconciliatedRepository.findOne(tracking_number1);
		notNull2 = shipmentReconciliatedRepository.findOne(reference);

		assert(notNull1 != null && notNull2 == null);

		parcelTracked = new ParcelTracked(tracking_number2, Constants.PARCEL_DEFAULT_FINAL_STATUS, 2.5);
		parcelTrackedDispatched = parcelTrackedDispatcher.dispatchEvent(parcelTracked);
		assert(parcelTrackedDispatched);

		Thread.sleep(5*1000);

		//Check parcel tracked processor
		parcelStatus = parcelStatusRepository.findOne(tracking_number2);
		sameWeightAndStatus = parcelStatus.getStatus().
				equals(parcelTracked.getStatus()) && parcelStatus.getWeight() == parcelTracked.getWeight();

		assert(sameWeightAndStatus);

		notNull1 = shipmentParcelReconciliatedRepository.findOne(tracking_number1);
		notNull2 = shipmentReconciliatedRepository.findOne(reference);

		assert(notNull1 != null && notNull2 != null);

		//ParcelTracked parcelTracked1 = new ParcelTracked(tracking_number1, Constants., weight)

	}	


}
