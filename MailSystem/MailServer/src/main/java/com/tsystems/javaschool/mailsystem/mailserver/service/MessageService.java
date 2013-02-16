package com.tsystems.javaschool.mailsystem.mailserver.service;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.tsystems.javaschool.mailsystem.mailserver.dao.MessageDAO;
import com.tsystems.javaschool.mailsystem.mailserver.dao.MessageDAOImpl;
import com.tsystems.javaschool.mailsystem.mailserver.service.MessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.MessageEntity;

public class MessageService {
	MessageDAO messageDAO = new MessageDAOImpl();
	
	public boolean sendMessage(ObjectInputStream input) {
		try {
			MessageEntity message = (MessageEntity) input.readObject();
			if (message.getReceiver() == null) {
				return false;
			}
			
			messageDAO.insert(message);
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteMessage(ObjectInputStream input) {
		try {
			MessageEntity message = (MessageEntity) input.readObject();
			System.out.println(message);
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}		
	}
}