package com.pinnovations.download;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.eclipsesource.json.JsonValue;

public class FIOAlerts {
	
	private Alert[] alert;
	private String timezone;
	private int numberofalerts;
	
	public FIOAlerts(ForecastIO fio) {
		
		if(fio.hasAlerts()) {
			alert = new Alert[fio.getAlerts().size()];
			timezone = fio.getTimezone();
			numberofalerts = fio.getAlerts().size();
			init(fio);
		}
	}
	
	private void init(ForecastIO fio) {
		
		JsonValue jsonvalue;
		
		if(fio.hasAlerts()) {
			for(int i = 0; i<NumberOfAlerts(); i++) {
				alert[i] = new Alert();
				jsonvalue = fio.getAlerts().get(i);
				alert[i].setTitle(jsonvalue.asObject().get("title").asString());
				alert[i].setTime(jsonvalue.asObject().get("time").asLong());
				alert[i].setExpire(jsonvalue.asObject().get("expires").asLong());
				alert[i].setDescription(jsonvalue.asObject().get("description").asString());
				alert[i].setUri(jsonvalue.asObject().get("uri").asString());
			}
		}
	}
	
	public int NumberOfAlerts() {
		return numberofalerts;
	}
	
	public String getAlertTitle(int index) {
		if(index<0 || index >= alert.length)
			return null;
		else
			return alert[index].getTitle();
	}
	
	public String getAlertTime(int index) {
		if(index<0 || index>= alert.length)
			return null;
		else {
			String out = "";
			DateFormat dfm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			dfm.setTimeZone(TimeZone.getTimeZone(timezone));
			out = dfm.format(Long.parseLong(String.valueOf(alert[index].getTime()*1000)));
			return out;
			}
	}
	
	public String getAlertExpireTime(int index) {
		if(index < 0 || index>= alert.length)
			return null;
		else {
			String out = "";
			DateFormat dfm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			dfm.setTimeZone(TimeZone.getTimeZone(timezone));
			out = dfm.format(Long.parseLong(String.valueOf(alert[index].getExpire()*1000)));
			return out;
		}
	}
	
	public String getAlertUri(int index) {
		if(index<0 || index >= alert.length)
			return null;
		else
			return alert[index].getUri();
	}
	
	public String getAlert(int index) {
		
		StringBuilder sb = new StringBuilder("");
		
		sb.append("Title: "+getAlertTitle(index));
		sb.append("\n");
		sb.append("Time: "+getAlertTitle(index));
		sb.append("\n");
		sb.append("Expires: "+getAlertTitle(index));
		sb.append("\n");
		sb.append("Description: "+getAlert(index));
		sb.append("\n");
		sb.append("URI: "+getAlert(index));
		sb.append("\n");
		
		return sb.toString();
	}
}

class Alert{
	private String title;
	private Long time;
	private Long expire;
	private String description;
	private String uri;
	
	public Alert() {
		setTitle("");
		setTime(0L);
		setExpire(0L);
		setDescription("");
		setUri("");
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public long getTime() {
		return this.time;
	}
	
	public void setTime(Long time) {
		this.time = time;
	}
	
	public long getExpire() {
		return this.expire;
	}
	
	public void setExpire(Long expire) {
		this.expire = expire;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUri() {
		return this.uri;
	}
	
	public void setUri(String Uri) {
		this.uri = Uri;
	}
}
