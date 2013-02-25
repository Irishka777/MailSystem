package com.tsystems.javaschool.mailsystem.clientservice;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.entities.UserEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;

public class ClientMailBoxService {
	
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	public ClientMailBoxService(ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
	}
	
//	public MailBoxEntity createMailBox(String emailAddress, String password, String name, String surname,
//			int year,int month, int day, String phoneNumber) {
//		return new MailBoxEntity(emailAddress, password, new UserEntity(name,surname,year,month,day,phoneNumber));
//	}
//	
	public MailBoxEntity createMailBox(String emailAddress, String password, String name, String surname,
			Date dateOfBirth, String phoneNumber) {
		return new MailBoxEntity(emailAddress, password, new UserEntity(name,surname,dateOfBirth,phoneNumber));
	}
	
	public MailBoxEntity createMailBox(String emailAddress, String password,UserEntity user) {
		return new MailBoxEntity(emailAddress, password, user);
	}
	
//	public UserEntity createUser(String name, String surname, int year,int month, int day,String phoneNumber) {
//		return new UserEntity(name,surname,year,month,day,phoneNumber);
//	}
	
	public ServerResponse login(MailBoxEntity mailBox) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.Login, mailBox));
			return (ServerResponse) input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");	
		}	
	}
	
	public ServerResponse registration(MailBoxEntity mailBox) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.Registration, mailBox));
			return (ServerResponse) input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");	
		}	
	}
}
