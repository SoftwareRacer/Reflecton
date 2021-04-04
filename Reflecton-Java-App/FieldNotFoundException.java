//
//  FieldNotFoundException.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 14.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.exceptions;

public class FieldNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public FieldNotFoundException(String message) {
        super(message);
    }
}