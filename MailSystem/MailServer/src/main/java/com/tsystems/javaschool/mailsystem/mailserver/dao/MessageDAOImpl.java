package com.tsystems.javaschool.mailsystem.mailserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tsystems.javaschool.mailsystem.mailserver.server.Server;
import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.MessageEntity;

public class MessageDAOImpl implements MessageDAO {
	
	public boolean insert(MessageEntity message) {
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		FolderEntity folder = new FolderEntity("Outcomming",message.getSender());
		folder.getListOfMessages().add(message);
		
		trx.begin();
		em.merge(folder);
		trx.commit();
		
		em.close();
			
		return true;
	}
	
	public boolean delete(MessageEntity message) {
		return true;
	}
}
