package com.tsystems.javaschool.mailsystem.mailserver.service;

import com.tsystems.javaschool.mailsystem.entities.MessageEntity;
import com.tsystems.javaschool.mailsystem.mailserver.dao.MessageDAO;
import com.tsystems.javaschool.mailsystem.mailserver.dao.MessageDAOImpl;
import com.tsystems.javaschool.mailsystem.mailserver.server.Server;
import com.tsystems.javaschool.mailsystem.mailserver.service.MessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

public class MessageService {
	
	MessageDAO messageDAO = new MessageDAOImpl();
	
	public ServerResponse sendMessage(Object message) {
		
		if (message instanceof MessageEntity) {
			if (((MessageEntity) message).getReceiver() == null) {
				return new ServerResponse(true,false,null,"Mail box with sush email address does not exist");
			}
			try {
				if (messageDAO.send((MessageEntity) message)) {
					return new ServerResponse(false,false,"Message successfully sent",null);
				}
				return new ServerResponse(true, true, null, "System error, error in programe code");
			} catch (Exception e) {
				Server.logger.error(e.getStackTrace());
//				e.printStackTrace();
				return new ServerResponse(true, true, null, "System error, program will be closed");
			}		
		}
		return new ServerResponse(true,true,null,
				"Wrong type of object, MessageEntity required to send a message, error in programe code");		
	}
	
	public ServerResponse saveMessage(Object message) {
		
		if (message instanceof MessageEntity) {
			try {
				if (messageDAO.save((MessageEntity) message)) {
					return new ServerResponse(false,false,"Message successfully saved in draft messages",null);
				}
				return new ServerResponse(true, true, null, "System error, error in programe code");
			} catch (Exception e) {
				e.printStackTrace();
				return new ServerResponse(true, true, null, "System error, program will be closed");
			}
		}
		return new ServerResponse(true,true,null,
				"Wrong type of object, MessageEntity required to save a message, error in programe code");	
	}
	
	public ServerResponse deleteMessage(Object message) {
		
		if (message instanceof MessageEntity) {
			try {
				if (messageDAO.delete((MessageEntity) message)) {
					return new ServerResponse(false,false,"Message successfully deleted",null);
				}
				return new ServerResponse(true, true, null, "System error, error in programe code");
			} catch (Exception e) {
				e.printStackTrace();
				return new ServerResponse(true, true, null, "System error, program will be closed");
			}
		}
		return new ServerResponse(true,true,null,"Wrong type of object, MessageEntity required to send a message, error in programe code");	
	}
}