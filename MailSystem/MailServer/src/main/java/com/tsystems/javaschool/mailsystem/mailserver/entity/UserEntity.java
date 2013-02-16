//package com.tsystems.javaschool.mailsystem.mailserver.entity;
//
//import java.util.Calendar;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import com.tsystems.javaschool.mailsystem.shareableObjects.User;
//
//@Entity
//@Table(name = "User")
//public class UserEntity {
//	@Id
//	@GeneratedValue
//	private long id;
//	
//	private String name;
//	private String surname;
//	
//	@Column(columnDefinition = "DATE")
//	private Calendar dateOfBirth;
//	private String phoneNumber;
//	
//	public UserEntity() {}
//	
//	public UserEntity(User user) {
//		name = user.getName();
//		surname = user.getSurname();
//		dateOfBirth = user.getDateOfBirth();
//		phoneNumber = user.getPhoneNumber();
//	}
//	
////	public UserEntity(String name, String surname, Calendar dateOfBirth, String phoneNumber) {
////		this.name = name;
////		this.surname = surname;
////		this.dateOfBirth = dateOfBirth;
////		this.phoneNumber = phoneNumber;
////	}
//	
//	public void setId(long id) {
//		this.id = id;
//	}
//	public long getId() {
//		return id;
//	}
//	
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getName() {
//		return name;
//	}
//	
//	public void setSurname(String surname) {
//		this.surname = surname;
//	}
//	public String getSurname() {
//		return surname;
//	}
//	
//	public void setDate(Calendar dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
//	public Calendar getDate() {
//		return dateOfBirth;
//	}
//	
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//}
