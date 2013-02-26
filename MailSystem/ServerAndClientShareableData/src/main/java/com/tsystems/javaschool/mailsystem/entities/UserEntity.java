package com.tsystems.javaschool.mailsystem.entities;

import java.io.Serializable;
import java.util.Date;

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
	private Date dateOfBirth;
	private String phoneNumber;
	
	public UserEntity() {}
	
	public UserEntity(String name, String surname, Date dateOfBirth, String phoneNumber) {
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
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
	
	public void setDate(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Date getDate() {
		return dateOfBirth;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
}
