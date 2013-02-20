package com.tsystems.javaschool.mailsystem.mailserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.entities.MessageEntity;
import com.tsystems.javaschool.mailsystem.mailserver.server.Server;

public class MessageDAOImpl implements MessageDAO {
	
	FolderDAO folderDAO = new FolderDAOImpl();
	
	public boolean send(MessageEntity message) {
		
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
			}
			em.close();
		}
			
		return true;
	}
	
	public boolean save(MessageEntity message) {
		
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
			}
			em.close();
		}
			
		return true;
	}
	
	public boolean delete(MessageEntity message) {
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();

		try {
			trx.begin();
			em.remove(em.merge(message));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			em.close();
		}
			
		return true;
	}
}
