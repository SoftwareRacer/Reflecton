//
//  Config.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 14.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.database;

public class Config {
	/*--------------------------------------------------DATABASE--------------------------------------------------*/
	// 173.212.224.225 reflecton O5p73cDi7rUt
	public static final String ipAddress = "127.0.0.1";
	public static final String port = "3306";
	public static final String user = "root";
	public static final String password = "";
	public static final String databaseName = "reflecton";
	public static final String tableName = "user";
	
	/*--------------------------------------------------COLUMNS--------------------------------------------------*/
	public static Column[] columnElements = {
			new Column("first_name", "varchar(255)"),
			new Column("last_name", "varchar(255)"),
			new Column("email", "varchar(255)"),
			new Column("password", "varchar(255)"),
			new Column("data", "varchar(255)")
	};
}