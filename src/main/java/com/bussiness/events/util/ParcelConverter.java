package com.bussiness.events.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.codehaus.jackson.map.ObjectMapper;

import com.bussiness.events.domain.query.Parcel;

@Converter
public class ParcelConverter implements AttributeConverter<Parcel[], String> {

	private ObjectMapper objectMapper;
	
	private Class<? extends Parcel[]> clazz;
	
	public ParcelConverter() {
		objectMapper = new ObjectMapper();
		clazz = new Parcel[1].getClass();
	}
	
	@Override
	public String convertToDatabaseColumn(Parcel[] arg0) {
		try {
			return objectMapper.writeValueAsString(arg0);
		} catch(Exception e) {
			return "";
		}
	}

	@Override
	public Parcel[] convertToEntityAttribute(String arg0) {
		try {
			return objectMapper.readValue(arg0, clazz);
		} catch (Exception e) {
			return null;
		}
				
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Parcel[] ps = new Parcel[2];
		
		ps[0] = new Parcel("A",0,1,1,1,"A");
		ps[1] = new Parcel("B",0,1,1,1,"B");
		
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(ps);
		//System.out.println(str);
		
		
		ps = mapper.readValue(str, ps.getClass());
		
		str = mapper.writeValueAsString(ps);
		System.out.println(str);
	
		
		//JSONArray jsonArray = new JSONArray("[  { \"test\": 5  }  ]");		
		//System.out.println(jsonArray);
	}

}
