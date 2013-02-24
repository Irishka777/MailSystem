package com.tsystems.javaschool.mailsystem.entities;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "MailBox")
@NamedQuery(name = "findByEmailAddress", query = "SELECT box FROM MailBoxEntity box WHERE box.emailAddress = :address")
public class MailBoxEntity implements Serializable {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false, unique = true)
	private String emailAddress;
	
	@Column(columnDefinition = "binary(50)")
	private byte[] password;
	
	private Calendar creationDate;
	
	@OneToOne(cascade = CascadeType.ALL)
	private UserEntity user;
	
	public MailBoxEntity() {}
	
	public MailBoxEntity(String emailAddress, String password, UserEntity user) {
		this.emailAddress = emailAddress.toLowerCase();

		try {
			String salt = "qwertyuiopasdfghjklzxcvbnm";
			password = password+salt;
			
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] bytesOfPassword;
			bytesOfPassword = messageDigest.digest(password.getBytes(Charset.forName("UTF8")));
			
			this.password = new byte[50];
			for (int i = 0; i < bytesOfPassword.length; i++) {
				this.password[i] = bytesOfPassword[i];
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		creationDate = Calendar.getInstance();
		this.user = user;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress.toLowerCase();
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	
//	public void setPassword(String password) {
//		this.password = password;
//	}
	public byte[] getPassword() {
		return password;
	}
	
	public void setCreationDate(Calendar currentDate) {
		creationDate = currentDate;
	}
	public Calendar getCreationDate() {
		return creationDate;
	}
	
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public UserEntity getUser() {
		return user;
	}
	
	public String toString() {
		return getEmailAddress();
	}
}
