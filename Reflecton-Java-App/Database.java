//
//  Database.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 14.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

public class Database {
	private String driver;
	private String ipAddress;
	private String port;
	private String url;
	private String user;
	private String password;
	private String databaseName;
	private String tableName;
	private Column[] columnElements;
	private Connection databaseConnection;
	
	public Database(String ipAddress, String port, String user, String password, String databaseName, String tableName, Column[] columnElements) {
		this.driver = "com.mysql.jdbc.Driver";
		this.ipAddress = ipAddress;
		this.port = port;
		this.user = user;
		this.password = password;
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.columnElements = columnElements;
	}
	
	public String connectToDatabase() throws Exception {
		this.url = "jdbc:mysql://" + this.ipAddress + ":" + this.port + "/" + this.databaseName + "?autoReconnect=true&useSSL=false";
		Class.forName(driver);
		this.databaseConnection = DriverManager.getConnection(this.url, this.user, this.password);
		
		return this.url;
	}
	
	public void closeConnectionToDatabase() throws Exception {
		this.databaseConnection.close();
	}
	
	public List<String> selectRow(Column[] columnElementsToSelect, String[] rowElementsToSelect) throws Exception {
		String sqlQuery = "SELECT * FROM " + this.tableName + " WHERE ";
		
		for(int i = 0; i < columnElementsToSelect.length; i++) {
			sqlQuery = sqlQuery + columnElementsToSelect[i].getName();
			sqlQuery = sqlQuery + "=" + "'" + rowElementsToSelect[i] + "'";
			
			if(i < columnElementsToSelect.length - 1) {
				sqlQuery = sqlQuery + " AND ";
			}
		}
		
		Statement statement = (Statement) this.databaseConnection.createStatement();
		ResultSet rs = statement.executeQuery(sqlQuery);
		
		List<String> result = new ArrayList<String>();
		while (rs.next()) {
			for (int i = 0; i < columnElements.length; i++) {
				result.add(rs.getString(this.columnElements[i].getName()));
			}
		}
		return result;
	}
	
}