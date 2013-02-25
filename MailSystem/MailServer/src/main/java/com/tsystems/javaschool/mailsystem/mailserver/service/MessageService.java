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
		try {
			if (((MessageEntity) message).getReceiver() == null) {
				return new ServerResponse(true, false, null, "Mail box with sush email address does not exist");
			}
			if (messageDAO.send((MessageEntity) message)) {
				Server.logger.info("Message successfully sent from " + ((MessageEntity) message).getSender() + " to " + ((MessageEntity) message).getReceiver());
				return new ServerResponse(false, false, "Message successfully sent", null);
			}
			Server.logger.error("If the program is working properly, this message should not appear");
			return new ServerResponse(true, true, null, "Error in programe code");
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, MessageEntity required to send a message, error in programe code");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}	
	}
	
	public ServerResponse saveMessage(Object message) {
		try {
			if (messageDAO.save((MessageEntity) message)) {
				Server.logger.info("Message successfully saved in draft messages of " + ((MessageEntity) message).getSender());
				return new ServerResponse(false, false, "Message successfully saved in draft messages", null);
			}
			Server.logger.error("If the program is working properly, this message should not appear");
			return new ServerResponse(true, true, null, "Error in programe code");
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, MessageEntity required to save a message, error in programe code");	
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}	
	}
	
	public ServerResponse deleteMessage(Object message) {
		try {
			if (messageDAO.delete((MessageEntity) message)) {
				Server.logger.info("Message successfully deleted");
				return new ServerResponse(false, false, "Message successfully deleted", null);
			}
			Server.logger.error("If the program is working properly, this message should not appear");
			return new ServerResponse(true, true, null, "Error in programe code");
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, MessageEntity required to delete a message, error in programe code");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}	
	}
}