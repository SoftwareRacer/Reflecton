//
//  Email.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 22.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.download;

import java.util.Calendar;

public class Day {
	private String day;
	private String[] fields;
	
	public Day() {
		this.day = null;
		this.fields = null;
	}
	
	public Day(String day, String[] forecastfields) {
		this.day = day;
		this.fields = forecastfields;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public void setForecastFields(String[] fields) {
		this.fields = fields;
	}
	
	public String getDay() {
		return this.day;
	}
	
	public String[] getFields() {
		return this.fields;
	}
	
	public String toString() {
		String returnValue = this.day + ": ";
		for (int i = 0; i < this.fields.length; i++) {
			returnValue += "\n" + this.fields[i];
		}
		return returnValue;
	}
	
	public static String getDayByNumber(int iterator) {
		String day = null;
		
		switch(iterator) {
		case Calendar.MONDAY:
			day = "Monday";
			break;
		case Calendar.TUESDAY:
			day = "Tuesday";
			break;
		case Calendar.WEDNESDAY:
			day = "Wednesday";
			break;
		case Calendar.THURSDAY:
			day = "Thursday";
			break;
		case Calendar.FRIDAY:
			day = "Friday";
			break;
		case Calendar.SATURDAY:
			day = "Saturday";
			break;
		case Calendar.SUNDAY:
			day = "Sunday";
			break;
		default:
			day = null;
			break;
		}
		
		return day;
	}
}