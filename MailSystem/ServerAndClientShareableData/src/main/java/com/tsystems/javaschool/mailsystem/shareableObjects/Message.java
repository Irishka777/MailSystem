package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class Message implements Serializable {
	
	private String sender;
	private String receiver;
	private Date date;
	private String theme;
	private String messageBody;
	
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
	
	public void setDate(Date currentDate) {
		date = currentDate;
	}
	public Date getDate() {
		return date;
	}
	
	public void setTheme(String messageTheme) {
		theme = messageTheme;
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
