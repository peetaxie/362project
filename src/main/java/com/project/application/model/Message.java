package com.project.application.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

/** 
* @author Yuhang Xie 
* @version Apr 18, 2019 3:24:43 PM 
*/
public class Message {
	private int senderId;
	private User sender;
	private String message;
	@CreationTimestamp
	private Date dateTime;
	
	public Message() {
		
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
