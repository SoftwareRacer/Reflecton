//
//  InterfaceDatabase.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 14.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.database;

import java.util.List;
import com.pinnovations.exceptions.*;

public class InterfaceDatabase {
	// function returns 
	public static String getField(String columnElementToSelect, String rowElementToSelect, String field) throws FieldNotFoundException {
		Column[] columnElementsToSelect = {
				new Column(columnElementToSelect, "varchar(255)")
		};
		
		String[] rowElementsToSelect = {rowElementToSelect};
		String returnValue = "";
		
		try {
			// instantiate database with dynamic values
			Database db = new Database(
					Config.ipAddress, Config.port,
					Config.user, Config.password,
					Config.databaseName, Config.tableName, 
					Config.columnElements);
			
			// connect to database
			db.connectToDatabase();
			
			List<String> result = db.selectRow(columnElementsToSelect, rowElementsToSelect);
			String[] resultArray = new String[result.size()];
			result.toArray(resultArray);
			
			for (int i = 0; i < resultArray.length; i++) {
				if(Config.columnElements[i].getName() == field) {
					returnValue = resultArray[i];
				}
			}
			
			// disconnect from database
			db.closeConnectionToDatabase();
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		if (returnValue == "") {
			throw new FieldNotFoundException("Field not found!");
		}
		
		return returnValue;
	}
}
