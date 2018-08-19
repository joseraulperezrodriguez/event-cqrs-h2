package com.bussiness.events.util;

public class StaticOps {
	
	public static boolean compareDouble(double a, double b) {
		double diff = Math.abs(b-a);
		return (diff <= Constants.EPSILON);
	}

}
