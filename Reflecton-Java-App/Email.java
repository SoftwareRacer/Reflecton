//
//  Email.java
//  ReflectonUI
//
//  Created by Alexander Jeitler-Stehr on 21.11.17.
//  Copyright © 2017 Alexander Jeitler-Stehr. All rights reserved.
//

package com.pinnovations.download;

public class Email { 
	private long uid;
	private String subject;
	private String content;
	
	public Email() {
		this.uid = 0;
		this.subject = null;
		this.content = null;
	}
	
	public Email(long uid, String subject, String content) {
		this.uid = uid;
		this.subject = subject;
		this.content = content;
	}
	
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public long getUid() {
		return this.uid;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String toString() {
		return this.subject + ": " + this.content;
	}
}