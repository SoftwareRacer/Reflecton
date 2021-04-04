//
//  ReflectonUI.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 14.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.main;

import java.util.Timer;
import java.util.TimerTask;

import javax.mail.MessagingException;

import com.pinnovations.database.*;
import com.pinnovations.download.*;
import com.pinnovations.filereader.*;
import com.pinnovations.widgets.EmailWidget;

public class ReflectonUI {
	
	private static FrameMain mainFrame;
	
	public static void main(String[] args) {
		mainFrame = new FrameMain();
		try {
			mainFrame.setVisible(false);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			public void run() {
				String[] widgetsArrangementArray = getWidgetsArrangement();
				for (int i = 0; i < widgetsArrangementArray.length; i++) {
					// System.out.println(widgetsArrangementArray[i]);
				}
//mainFrame.clearPanels();
				
				for (int i = 0; i < widgetsArrangementArray.length; i++) {
					if (widgetsArrangementArray[i].contains("email")) {
						// System.out.println("EMAIL:");
						String[] emailPasswordArray = widgetsArrangementArray[i].split(":");
						String email = emailPasswordArray[emailPasswordArray.length-2];
						String password = emailPasswordArray[emailPasswordArray.length-1];
						
//						try {
//							Email[] emails = InterfaceDownload.getEmails(email, password, 2);
//							/*for (int j = 0; j < emails.length; j++) {
//								System.out.println("UID: " + emails[j].getUid());
//								System.out.println("Subject: " + emails[j].getSubject());
//								System.out.println("Content: " + emails[j].getContent());
//							}*/
//						} catch (MessagingException e) {
//							e.printStackTrace();
//						}
						EmailWidget emailWidget = new EmailWidget();
						emailWidget.setHeading("Email");
						emailWidget.setPosition(i);
						mainFrame.setWidgetEmail(emailWidget);
//mainFrame.addWidgetsToFrame();
					}
					
					if (widgetsArrangementArray[i].contains("calendar")) {
						//System.out.println("CALENDAR:");
//mainFrame.setWidgetCalendar(i);
					}
					
					if (widgetsArrangementArray[i].contains("weather")) {
						// System.out.println("FORECAST:");
						Day[] weather = InterfaceDownload.getWeather();
						/*
						for (int j = 0; j < weather.length; j++) {
							System.out.println(weather[j]);
							System.out.println("\n");
						}*/
//mainFrame.setWidgetWeather(i, weather);
					}
					
					if (widgetsArrangementArray[i].contains("clock")) {
						//System.out.println("CLOCK:");
						String time = InterfaceDownload.getClock();
						//System.out.println(time);
// mainFrame.setWidgetClock(i);
					}
				}
				
				mainFrame.setVisible(true);
				// System.out.println("Timer");
			}
		}, 0, 5000);
		
	}
	
	public static String[] getWidgetsArrangement() {
		// define variables
		String column = "email";
		String reflectonEmail = Filereader.getLines("/Users/alexanderjeitlerstehr/Desktop/email.txt")[0];
		String returnField = "data";
		
		// get arrangement of widgets (data)
		String widgetsArrangement = "";
		try {
			widgetsArrangement = InterfaceDatabase.getField(column, reflectonEmail, returnField);
		} catch (Exception e) {
			e.printStackTrace();
			widgetsArrangement = "<widget>email</widget><widget>calendar</widget><widget>weather</widget><widget>clock</widget>";
		}
		
		// format: widget-tags -> slashes -> array-elements
		// format by replacing widget-tags with slashes
		String widgetsArrangementHelp = widgetsArrangement.replaceAll("</widget>", "/").replaceAll("<widget>", "");
		
		// format by deleting last slash
		String widgetsArrangementString = widgetsArrangementHelp.substring(0, widgetsArrangementHelp.length() - 1);
				
		// split into array by slash
		String[] widgetsArrangementArray = widgetsArrangementString.split("/");
		// System.out.println(widgetsArrangementArray[0]);
				
		return widgetsArrangementArray;
	}
	
}