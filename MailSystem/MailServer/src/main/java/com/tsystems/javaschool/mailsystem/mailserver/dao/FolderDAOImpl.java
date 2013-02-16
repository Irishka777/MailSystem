package com.tsystems.javaschool.mailsystem.mailserver.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.tsystems.javaschool.mailsystem.mailserver.server.Server;
import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;

public class FolderDAOImpl implements FolderDAO {
	
	public String insert(FolderEntity folder) {
		
		if (findByEmailAddressAndFolderName(folder.getFolderName(), folder.getEmailAddress()) != null) {
			return "Folder with such a name already exists in this mail box";
		}
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		try {
			trx.begin();
			em.persist(folder);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
		}		
		em.close();
		
		return "Folder successfully created";
	}
	
	public String delete(FolderEntity folder) {
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		try {
			trx.begin();
			em.remove(folder);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
				em.close();
				return "Error in process of deleting folder";
			}
		}		
		em.close();
		return "Folder successfully deleted";
	}
	
	public String rename(FolderEntity folder) {
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		try {
			trx.begin();
//			FolderEntity newFolder = em.find(FolderEntity.class, folder.getId());
			em.merge(folder);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
				em.close();
				return "Error in process of renaming folder";
			}
		}		
		em.close();
		return "Folder successfully renamed";
	}
	
	public FolderEntity findByEmailAddressAndFolderName(String folderName, MailBoxEntity emailAddress) {
		
		EntityManager em = Server.emf.createEntityManager();
		
		TypedQuery<FolderEntity> query = em.createNamedQuery("findByEmailAddressAndFolderName", FolderEntity.class);
		query.setParameter("mailBox", emailAddress);
		query.setParameter("folderName", folderName);
		FolderEntity folder = null;
		try {
			folder = query.getSingleResult();
		} catch (javax.persistence.NoResultException e) {}
		
		em.close();
		
		return folder;
	}
}
