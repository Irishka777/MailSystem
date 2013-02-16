package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;
import java.util.Calendar;

@SuppressWarnings("serial")
public class MailBox implements Serializable {
	private String emailAddress;
	private String password;
	private Calendar creationDate;
	private User user;
	
	public MailBox() {}
	
	public MailBox(String emailAddress, String password, User user) {
		this.emailAddress = emailAddress;
		this.password = password;
		creationDate = Calendar.getInstance();
		this.user = user;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
//	public void setCreationDate(Calendar currentDate) {
//		creationDate = currentDate;
//	}
	public Calendar getCreationDate() {
		return creationDate;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
