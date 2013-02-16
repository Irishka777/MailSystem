package com.tsystems.javaschool.mailsystem.mailserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.tsystems.javaschool.mailsystem.mailserver.server.Server;
import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;

public class MailBoxDAOImpl implements MailBoxDAO {
	public boolean insert(MailBoxEntity mailBox) {
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		trx.begin();
		try {
			em.persist(mailBox);
			trx.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			trx.rollback();
			em.close();
			return false;
		}
		
		em.close();
			
		return true;
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
		} catch (javax.persistence.NoResultException e) { }
		
		em.close();
		
		return mailBox;
	}
}
