package com.tsystems.javaschool.mailsystem.mailserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.mailserver.server.Server;

public class MailBoxDAOImpl implements MailBoxDAO {
	
	public boolean insert(MailBoxEntity mailBox) {
		
		FolderDAO folderDAO = new FolderDAOImpl();
		
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
			em.close();
		}
	
		MailBoxEntity createdMailBox = findByEmail(mailBox.getEmail());
		folderDAO.insert(new FolderEntity("Incoming messages",createdMailBox));
		folderDAO.insert(new FolderEntity("Outgoing messages",createdMailBox));
		folderDAO.insert(new FolderEntity("Draft messages",createdMailBox));
		
		return true;
	}
	
	public MailBoxEntity update(MailBoxEntity mailBox) {
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		try {
			trx.begin();
			em.merge(mailBox);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			em.close();
		}
		return mailBox;
	}
	
	public boolean delete(MailBoxEntity mailBox) {
		return true;
	}
	
	public MailBoxEntity findByEmail(String email) {
		
		EntityManager em = Server.emf.createEntityManager();
		
		TypedQuery<MailBoxEntity> query = em.createNamedQuery("findByEmail", MailBoxEntity.class);
		query.setParameter("address", email);
		MailBoxEntity mailBox = null;
		try {
			mailBox = query.getSingleResult();
		} finally {
			em.close();
		}
			
		return mailBox;
	}
}
