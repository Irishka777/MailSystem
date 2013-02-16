package com.tsystems.javaschool.mailsystem.mailserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tsystems.javaschool.mailsystem.mailserver.server.Server;
import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.MessageEntity;

public class MessageDAOImpl implements MessageDAO {
	
	FolderDAO folderDAO = new FolderDAOImpl();
	
	public String send(MessageEntity message) {
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		FolderEntity senderFolder = folderDAO.findByEmailAddressAndFolderName("Outgoing messages", message.getSender());
		senderFolder.getListOfMessages().add(message);
		
		FolderEntity receiverFolder = folderDAO.findByEmailAddressAndFolderName("Incoming messages", message.getReceiver());
		receiverFolder.getListOfMessages().add(new MessageEntity(message));

		try {
			trx.begin();		
			em.merge(senderFolder);
			em.merge(receiverFolder);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
				em.close();
				return "Message was not sent";
			}
		}
		
		em.close();
			
		return "Message successfully sent";
	}
	
	public String save(MessageEntity message) {
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		FolderEntity senderFolder = folderDAO.findByEmailAddressAndFolderName("Draft messages", message.getSender());
		senderFolder.getListOfMessages().add(message);

		try {
			trx.begin();		
			em.merge(senderFolder);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback(); 
				em.close();
				return "Message was not saved";
			}
		}
		
		em.close();
			
		return "Message successfully saved";
	}
	
	public String delete(MessageEntity message) {
		return  "Message successfully deleted";
	}
}
