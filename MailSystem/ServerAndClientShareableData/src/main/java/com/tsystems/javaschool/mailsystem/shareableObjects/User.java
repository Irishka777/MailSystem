package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;
import java.util.Calendar;

@SuppressWarnings("serial")
public class User implements Serializable {
	
	private String name;
	private String surname;
	private Calendar dateOfBirth;
	private String phoneNumber;
	
	public User() {}
	
	public User(String name, String surname, int year, int month, int day, String phoneNumber) {
		this.name = name;
		this.surname = surname;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month,day,0,0,0);
		this.dateOfBirth = calendar;
		this.phoneNumber = phoneNumber;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getSurname() {
		return surname;
	}
	
	public void setDateOfBirth(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month,day,0,0,0);
		dateOfBirth = calendar;
	}
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
}
