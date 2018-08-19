package com.bussiness.events.domain.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity(name="carrier")
@Table(name="carrier", indexes = { @Index(name = "carrier_name_idx", columnList = "name") })
public class Carrier {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer carrier_id;
	
	@Column(unique=true)
	private String name;
	
	private String final_status;
	
	public Carrier(Integer carrier_id, String name, String final_status) {
		super();
		this.carrier_id = carrier_id;
		this.name = name;
		this.final_status = final_status;
	}
	
	public Carrier() {
		super();
	}

	public Integer getCarrier_id() {
		return carrier_id;
	}

	public void setCarrier_id(Integer carrier_id) {
		this.carrier_id = carrier_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFinal_status() {
		return final_status;
	}

	public void setFinal_status(String final_status) {
		this.final_status = final_status;
	}
	
	
	

}
