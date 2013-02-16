package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Folder implements Serializable {
	private String folderName;
	private String emailAddress;
	private List<Message> listOfMessages;
	
	public Folder() {}
	
	public Folder(String folderName, String emailAddress) {
		this.folderName = folderName;
		this.emailAddress = emailAddress;
	}
	
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFolderName() {
		return folderName;
	}
	
	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setListOfMessages(List<Message> listOfMessages) {
		this.listOfMessages = listOfMessages;
	}
	public List<Message> getListOfMessages() {
		return listOfMessages;
	}
}
