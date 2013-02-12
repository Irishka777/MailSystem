package com.tsystems.javaschool.mailsystem.mailserver.server;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.tsystems.javaschool.mailsystem.mailserver.service.MessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.Folder;
import com.tsystems.javaschool.mailsystem.shareableObjects.Message;


public class MessageServer {
	
	private MessageService messageService = new MessageService();
	
	public Message sendMessage(ObjectInputStream input) {
		try {
			Message message = (Message) input.readObject();
			messageService.createMessage(message);
			System.out.println(message.getDate());
			return message;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean deleteMessage(ObjectInputStream input) {
		try {
			Message message = (Message) input.readObject();
			System.out.println(message);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}