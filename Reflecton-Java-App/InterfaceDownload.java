//
//  InterfaceDownload.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 14.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.download;

import java.text.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.jsoup.Jsoup;

import com.sun.mail.imap.IMAPFolder;

public class InterfaceDownload {

	public static Email[] getEmails(String email, String password, long numberOfEmails) throws MessagingException {
		Email[] emails = null;
		String server = null;
		
		if(email.contains("@gmail.com")) {
			server = "imap.gmail.com";
		}
		else if(email.contains("@gmx")) {
			server = "imap.gmx.net";
		}
		else if(email.contains("@outlook.com")) {
			server = "imap-mail.outlook.com";
		}
		else if(email.contains("@icloud.com")) {
			server = "imap.mail.me.com";
		}
		
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("imaps");
        try {
            store.connect(server, email, password);
            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
            try {
                if (!folder.isOpen()) {
                    folder.open(Folder.READ_ONLY);
                }
                long largestUid = folder.getUIDNext() - 1;
                
                // if numberOfEmails is -1 then all emails are displayed
                if (numberOfEmails == -1) {
                		numberOfEmails = largestUid;
                }	
                	Message[] messages = folder.getMessagesByUID(largestUid-numberOfEmails + 1, largestUid);
                	FetchProfile metadataProfile = new FetchProfile();
                	metadataProfile.add(FetchProfile.Item.FLAGS);
                	metadataProfile.add(FetchProfile.Item.ENVELOPE);
                	folder.fetch(messages, metadataProfile);
                	
                	emails = new Email[messages.length];
                	for (int i = 0; i < messages.length; i++) {
                		//messagesList[i] = messages[messages.length - i - 1].getSubject();
                		emails[i] = new Email();
                		emails[i].setUid(folder.getUID(messages[messages.length - i - 1]));
                		emails[i].setSubject(messages[messages.length - i - 1].getSubject());
                		
                		try {
                    		if (messages[messages.length - i - 1].isMimeType("text/plain")) {
                    			// System.out.println("XXX" + messages[messages.length - i - 1].getContent().toString());
                    			emails[i].setContent(messages[messages.length - i - 1].getContent().toString());
                    		}
                    		else if (messages[messages.length - i - 1].isMimeType("multipart/*")) {
                    			String result = "";
                    			MimeMultipart mimeMultipart = (MimeMultipart)messages[messages.length - i - 1].getContent();
                    			int count = mimeMultipart.getCount();
                    			for (int j = 0; j < count; j ++) {
                    				BodyPart bodyPart = mimeMultipart.getBodyPart(j);
                    				if (bodyPart.isMimeType("text/plain")){
                    					result = result + "\n" + bodyPart.getContent();
                    					break;  //without break same text appears twice in my tests
                    				}
                    				else if (bodyPart.isMimeType("text/html")){
                    					String html = (String) bodyPart.getContent();
                    					result = result + "\n" + Jsoup.parse(html).text();
                    				}
                    			}
                            // System.out.println("XX" + result);
                            emails[i].setContent(result);
                    		}
                    	} catch(Exception e) {
                    		e.printStackTrace();
                    	}
                }
            }
            finally {
                if (folder.isOpen()) {
                    folder.close(true);
                }
            }
        }
        finally {
            store.close();
        }
        return emails;
    }
	
	public static StringBuilder[] getCurrentWeather() {
		String latitude = "48.209";
		String longitude = "16.37";;
		String apikey = "7a4a8398d4ae0d6134ea0a4eafcf2b45";
		
		ForecastIO fio = new ForecastIO(apikey);
		fio.setUnits(ForecastIO.UNITS_AUTO);
		fio.setLang(ForecastIO.LANG_ENGLISH);
		fio.getForecast(latitude, longitude);
		
		String currentweather = null;
		StringBuilder[] weatherFields = new StringBuilder[17];
		FIOCurrently currently = new FIOCurrently(fio);
		String[] currentfield = currently.get().getFieldsArray();

		for (int i = 0; i < currentfield.length; i++) {
			currentweather = currentfield[i] + ": " + currently.get().getByKey(currentfield[i]);
			weatherFields[i] = new StringBuilder();
			weatherFields[i].append(currentweather);
		}
		
		return weatherFields;
	}

	public static Day[] getWeather(){
		String latitude = "48.209";
		String longitude = "16.37";
		String apikey = "7a4a8398d4ae0d6134ea0a4eafcf2b45";
		
		ForecastIO fio = new ForecastIO(apikey);
		fio.setUnits(ForecastIO.UNITS_AUTO);
		fio.setLang(ForecastIO.LANG_ENGLISH);
		fio.getForecast(latitude, longitude);
		
		Calendar calendar = Calendar.getInstance();
		FIODaily daily = new FIODaily(fio);
		
		Day[] day = new Day[daily.days() - calendar.get(Calendar.DAY_OF_WEEK)];
		
		for (int i = 0; i < day.length; i++) {
			day[i] = new Day();
			day[i].setDay(Day.getDayByNumber(i + calendar.get(Calendar.DAY_OF_WEEK)));
			
			String[] help1 = daily.getDay(i+calendar.get(Calendar.DAY_OF_WEEK)).getFieldsArray();
			String[] help2 = new String[help1.length];
			
			// System.out.println(dArray[i].getDay());
			for (int j = 0; j < help1.length; j++) {
				help2[j] = help1[j] + ": " + daily.getDay(i + calendar.get(Calendar.DAY_OF_WEEK)).getByKey(help1[j]);
				// System.out.println(help2[j]);
			}
			
			day[i].setForecastFields(help2);
		}
		
		return day;
	}
	
	public static String getClock() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm dd.MM.yy");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}
