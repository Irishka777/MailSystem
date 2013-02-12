package com.tsystems.javaschool.mailsystem.mailserver.service;

import com.tsystems.javaschool.mailsystem.mailserver.dao.MessageDAO;
import com.tsystems.javaschool.mailsystem.shareableObjects.Folder;
import com.tsystems.javaschool.mailsystem.shareableObjects.Message;

public class MessageService {
	private MessageDAO messageDAO = new MessageDAO();
	
	public boolean createMessage(Message message) {
		messageDAO.createMessage(message);
		return true;
	}
	
	public boolean deleteMessage(Message message) {
		return true;
	}
}
