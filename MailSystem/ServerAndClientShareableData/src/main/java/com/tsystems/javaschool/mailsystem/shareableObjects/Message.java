package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;
import java.util.Calendar;

@SuppressWarnings("serial")
public class Message implements Serializable {
	
	private String sender;
	private String receiver;
	private Calendar date;
	private String theme;
	private String messageBody;
	
	public Message() {}
	
	public Message(String sender,String receiver, String theme, String messageBody) {
		this.sender = sender;
		this.receiver = receiver;
		date = Calendar.getInstance();
		this.theme = theme;
		this.messageBody = messageBody;
	}
	
	public void setSender(String senderEmail) {
		sender = senderEmail;
	}
	public String getSender() {
		return sender;
	}
	
	public void setReceiver(String receiverEmail) {
		receiver = receiverEmail;
	}
	public String getReceiver() {
		return receiver;
	}
	
	public void setDate(Calendar currentDate) {
		date = currentDate;
	}
	public Calendar getDate() {
		return date;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getTheme() {
		return theme;
	}
	
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public String getMessageBody() {
		return messageBody;
	}
}
