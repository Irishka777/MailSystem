package com.tsystems.javaschool.mailsystem.mailserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.tsystems.javaschool.mailsystem.mailserver.server.Server;
import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;

public class MailBoxDAOImpl implements MailBoxDAO {
	
	public String insert(MailBoxEntity mailBox) {
		
		FolderDAO folderDAO = new FolderDAOImpl();
		
		if (findByEmailAddress(mailBox.getEmailAddress()) != null) {
			return "Mail box with such email address already exists";
		}
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		try {
			trx.begin();
			em.persist(mailBox);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
		}
		em.close();
		
		MailBoxEntity createdMailBox = findByEmailAddress(mailBox.getEmailAddress());
		folderDAO.insert(new FolderEntity("Outgoing messages",createdMailBox));
		folderDAO.insert(new FolderEntity("Incoming messages",createdMailBox));
		folderDAO.insert(new FolderEntity("Draft messages",createdMailBox));
		
		return "Mail Box successfully created";
	}
	
	public boolean update(MailBoxEntity mailBox) {
		return true;
	}
	
	public boolean delete(MailBoxEntity mailBox) {
		return true;
	}
	
	public MailBoxEntity findByEmailAddress(String emailAddress) {
		
		EntityManager em = Server.emf.createEntityManager();
		
		TypedQuery<MailBoxEntity> query = em.createNamedQuery("findByEmailAddress", MailBoxEntity.class);
		query.setParameter("address", emailAddress);
		MailBoxEntity mailBox = null;
		try {
			mailBox = query.getSingleResult();
		} catch (javax.persistence.NoResultException e) {}
		
		em.close();
		
		return mailBox;
	}
}
