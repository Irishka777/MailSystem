package com.tsystems.javaschool.mailsystem.mailserver.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.mailserver.server.Server;

public class FolderDAOImpl implements FolderDAO {
	
	public boolean insert(FolderEntity folder) {
		
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
			em.close();
		}		
		return true;
	}
	
	public boolean delete(FolderEntity folder) {
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		try {
			trx.begin();
			em.remove(em.merge(folder));
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();
			}
			em.close();
		}
		return true;
	}
	
	public boolean rename(FolderEntity folder) {
		
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		try {
			trx.begin();
			em.merge(folder);
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();				
			}
			em.close();
		}		
		return true;
	}
	
	public FolderEntity getFolder(FolderEntity folder) {
		EntityManager em = Server.emf.createEntityManager();
		EntityTransaction trx = em.getTransaction();
		
		try {
			trx.begin();
			folder = em.find(FolderEntity.class, folder.getId());
			trx.commit();
		} finally {
			if (trx.isActive()) {
				trx.rollback();				
			}
			em.close();
		}		
		return folder;
	}
	
	public List<FolderEntity> findFoldersForMailBox(MailBoxEntity mailBox) {
		
		EntityManager em = Server.emf.createEntityManager();
		
		TypedQuery<FolderEntity> query = em.createNamedQuery("findFoldersForMailBox", FolderEntity.class);
		query.setParameter("mailBox", mailBox);
		List<FolderEntity> folder = null;
		try {
			folder = query.getResultList();
		} finally {
			em.close();
		}		
		return folder;
	}
	
	public FolderEntity findByEmailAddressAndFolderName(String folderName, MailBoxEntity emailAddress) {
		
		EntityManager em = Server.emf.createEntityManager();
		
		TypedQuery<FolderEntity> query = em.createNamedQuery("findByEmailAddressAndFolderName", FolderEntity.class);
		query.setParameter("mailBox", emailAddress);
		query.setParameter("folderName", folderName);
		FolderEntity folder = null;
		try {
			folder = query.getSingleResult();
		} finally {
			em.close();
		}		
		return folder;
	}
}
