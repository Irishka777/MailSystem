package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class User implements Serializable {
	
	private String name;
	private String surname;
	private Date dateOfBirth;
	private String phoneNumber;
	
	public void setName(String userName) {
		name = userName;
	}
	public String getName() {
		return name;
	}
	
	public void setSurname(String userSurname) {
		surname = userSurname;
	}
	public String getSurname() {
		return surname;
	}
	
	public void setDate(Date userDateOfBirth) {
		dateOfBirth = userDateOfBirth;
	}
	public Date getDate() {
		return dateOfBirth;
	}
	
	public void setPhoneNumber(String userPhoneNumber) {
		phoneNumber = userPhoneNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
}
