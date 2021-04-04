//
//  Column.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 14.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.database;

public class Column {
	private String name;
	private String type;
	
	public Column(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}
	
	public String toString() {
		return this.name + " " + this.type;
	}
}