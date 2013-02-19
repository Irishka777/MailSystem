package com.tsystems.javaschool.mailsystem.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "User")
public class UserEntity implements Serializable {
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	private String surname;
	
	@Column(columnDefinition = "DATE")
	private Calendar dateOfBirth;
	private String phoneNumber;
	
	public UserEntity() {}
	
	public UserEntity(String name, String surname, int year, int month, int day, String phoneNumber) {
		this.name = name;
		this.surname = surname;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month,day,0,0,0);
		this.dateOfBirth = calendar;
		this.phoneNumber = phoneNumber;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
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
	
	public void setDate(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Calendar getDate() {
		return dateOfBirth;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
}
