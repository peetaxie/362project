package com.project.application.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

/** 
* @author Yuhang Xie 
* @version Apr 18, 2019 3:24:43 PM 
*/
public class Message {
	private int senderId;
	private String sender;
	private int receiverId;
	private String receiver;
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

	

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
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

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	
}
