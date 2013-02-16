package com.tsystems.javaschool.mailsystem.clientservice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;
import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;
import com.tsystems.javaschool.mailsystem.shareableObjects.UserEntity;

public class ClientMailBoxService {
	
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	public ClientMailBoxService(ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
	}
	
	public MailBoxEntity createMailBox(String name, String surname, int year,int month, int day,
			String phoneNumber, String emailAddress, String password) {
		return new MailBoxEntity(emailAddress, password, new UserEntity(name,surname,year,month,day,phoneNumber));
	}
	
	public String registration(MailBoxEntity mailBox) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.Registration, mailBox));
		} catch (IOException e) {
			return "Error in process of transfer mail box data from client to server: " + e.getMessage();		
		}	
		
		try {
			String response = (String) input.readObject();
			return response;
		} catch (ClassNotFoundException e) {
			return "Cannot get response about registration from server, try again later";
		} catch (IOException e) {
			return "Error in process of geting response about registration from server, try again later";
		}
	}
}
