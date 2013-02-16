package com.tsystems.javaschool.mailsystem.clientservice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;
import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.MessageEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;

public class ClientMessageService {
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	public ClientMessageService(ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
	}
	
	public MessageEntity createMessage(MailBoxEntity sender, String receiver, String theme, String messageBody) {
		MailBoxEntity receiverMailBox = null;
		
		try {
			output.writeObject(new CommandAndDataObject(ToDo.GetMailBoxEntityByMailAddress, receiver));
		} catch (IOException e) {
			System.out.println("Error in process of transfer receiver string " +
					"from client to server: " + e.getMessage());
			return null;
		}
		try {
			receiverMailBox = (MailBoxEntity) input.readObject();
			return new MessageEntity(sender,receiverMailBox,theme,messageBody);
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot get receiver from server: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("Cannot get response from server: " + e.getMessage());
			return null;
		}	
	}
	
	public String sendMessage(MessageEntity message) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.SendMessage, message));
		} catch (IOException e) {
			return "Error in process of transfer message from client to server: " + e.getMessage();		
		}	
		
		try {
			String response = (String) input.readObject();
			return response;
		} catch (ClassNotFoundException e) {
			return "Cannot get response about your message from server, try again later";
		} catch (IOException e) {
			return "Error in process of geting response about your message from server, try again later";
		}
	}
	
	public String saveMessage(MessageEntity message) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.SaveMessage, message));
		} catch (IOException e) {
			return "Error in process of transfer message from client to server: " + e.getMessage();		
		}	
		
		try {
			String response = (String) input.readObject();
			return response;
		} catch (ClassNotFoundException e) {
			return "Cannot get response about your message from server";
		} catch (IOException e) {
			return "Error in process of geting response about your message from server";
		}
	}
	
	public String deleteMessage(FolderEntity folder, MessageEntity message) {
//		try {
//			output.writeObject(new CommandAndDataObject(ToDo.DeleteMessage, message));
//		} catch (IOException e) {
//			return "Error in process of transfer message from client to server: " + e.getMessage();		
//		}	
//		
//		try {
//			String response = (String) input.readObject();
//			return response;
//		} catch (ClassNotFoundException e) {
//			return "Cannot get response about your message from server";
//		} catch (IOException e) {
//			return "Error in process of geting response about your message from server";
//		}
		return "";
	}
}
