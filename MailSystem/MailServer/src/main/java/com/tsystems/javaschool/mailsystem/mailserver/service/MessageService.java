package com.tsystems.javaschool.mailsystem.mailserver.service;

import com.tsystems.javaschool.mailsystem.mailserver.dao.MessageDAO;
import com.tsystems.javaschool.mailsystem.mailserver.dao.MessageDAOImpl;
import com.tsystems.javaschool.mailsystem.mailserver.service.MessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.MessageEntity;

public class MessageService {
	
	MessageDAO messageDAO = new MessageDAOImpl();
	
	public String sendMessage(Object message) {
		
		if (message instanceof MessageEntity) {
			if (((MessageEntity) message).getReceiver() == null) {
				return "Mail box with sush email address does not exist";
			}			
			return messageDAO.send((MessageEntity) message);
		}
		System.out.println("Wrong type of object, MessageEntity required to send a message");
		return "Wrong type of object, MessageEntity required to send a message";		
	}
	
	public String saveMessage(Object message) {
		
		if (message instanceof MessageEntity) {			
			return messageDAO.save((MessageEntity) message);
		}
		System.out.println("Wrong type of object, MessageEntity required to save a message");
		return "Wrong type of object, MessageEntity required to save a message";
		
	}
	
	public String deleteMessage(Object message) {
		
		if (message instanceof MessageEntity) {			
			return messageDAO.delete((MessageEntity) message);
		}
		System.out.println("Wrong type of object, MessageEntity required to delete a message");
		return "Wrong type of object, MessageEntity required to send a message";	
	}
	
//	public boolean sendMessage(ObjectInputStream input) {
//		try {
//			MessageEntity message = (MessageEntity) input.readObject();
//			if (message.getReceiver() == null) {
//				return false;
//			}
//			
//			messageDAO.insert(message);
//			return true;
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//	
//	public boolean deleteMessage(ObjectInputStream input) {
//		try {
//			MessageEntity message = (MessageEntity) input.readObject();
//			System.out.println(message);
//			return true;
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}		
//	}
}