package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MailBox implements Serializable {
	private String emailAddress;
	private Date creationDate;
	private User user;
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setCreationDate(Date currentDate) {
		creationDate = currentDate;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
