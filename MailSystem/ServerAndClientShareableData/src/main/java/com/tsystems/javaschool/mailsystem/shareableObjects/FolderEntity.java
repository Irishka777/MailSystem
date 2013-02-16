package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Folder")//,uniqueConstraints = @UniqueConstraint(columnNames = { "folderName", "emailAddress" }))
public class FolderEntity implements Serializable {
	@Id
	@GeneratedValue
	private long id;
	
	private String folderName;
	
	@OneToOne
	private MailBoxEntity mailBox;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<MessageEntity> listOfMessages;
	
	public FolderEntity() {}
	
	public FolderEntity(String folderName, MailBoxEntity mailBox) {
		this.folderName = folderName;
		this.mailBox = mailBox;
		this.listOfMessages = new ArrayList<MessageEntity>();
	}
	
	public void setId(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFolderName() {
		return folderName;
	}
	
	public void setEmailAddress(MailBoxEntity emailAddress) {
		this.mailBox = emailAddress;
	}
	public MailBoxEntity getEmailAddress() {
		return mailBox;
	}
	
	public void setListOfMessages(ArrayList<MessageEntity> listOfMessages) {
		this.listOfMessages = listOfMessages;
	}
	public List<MessageEntity> getListOfMessages() {
		return listOfMessages;
	}	
}
