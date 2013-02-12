package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Folder implements Serializable {
	private String folderName;
	private String emailAddress;
	private ArrayList<Message> listOfMessages;
	
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFolderName() {
		return folderName;
	}
	
	public void setEmailAddress(String folderEmail){
		emailAddress = folderEmail;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setMessagesList(ArrayList<Message> listOfMessages) {
		this.listOfMessages = listOfMessages;
	}
	public ArrayList<Message> getMessagesList() {
		return listOfMessages;
	}
}
