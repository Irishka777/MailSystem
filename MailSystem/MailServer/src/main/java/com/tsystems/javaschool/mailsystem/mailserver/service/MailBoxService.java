package com.tsystems.javaschool.mailsystem.mailserver.service;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAO;
import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAOImpl;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

public class MailBoxService {
	private MailBoxDAO mailBoxDAO = new MailBoxDAOImpl();
	
	public ServerResponse login(Object neededMailBox) {
		if (neededMailBox instanceof MailBoxEntity) {
			try {
				MailBoxEntity mailBox = mailBoxDAO.findByEmailAddress(((MailBoxEntity) neededMailBox).getEmailAddress());
				byte[] password = mailBox.getPassword();
				byte[] neededPassword = ((MailBoxEntity) neededMailBox).getPassword();
				if (password.length != neededPassword.length) {
					return new ServerResponse(true,false,null,"Entered password is wrong");
				}
				for (int i = 0; i < password.length; i++) {
					if (password[i] != neededPassword[i]) {
						return new ServerResponse(true,false,null,"Entered password is wrong");
					}
				}
				return new ServerResponse(false,false,mailBox,null);
			} catch (NoResultException e) {
				return new ServerResponse(true,false,null,"There is no user with such email address");
			} catch (Exception e) {
				e.printStackTrace();
				return new ServerResponse(true,true,null,"System error, program will be closed");
			}
		}
		return new ServerResponse(true,true,null,
				"Wrong type of object, MailBoxEntity required to login, error in program code");
	}
	
	public ServerResponse registration(Object mailBox) {
		
		if (mailBox instanceof MailBoxEntity) {
			try {
				if (mailBoxDAO.insert((MailBoxEntity) mailBox)) {
					return new ServerResponse(false,false, "Mail box successfully created",null);
				}
				return new ServerResponse(true, true, null, "System error, error in programe code");
			} catch (PersistenceException e) {
				e.printStackTrace();
				return new ServerResponse(true, false, null,
						"Mail box with such email address already exists, try to use another mail box name");
			} catch (Exception e) {
				e.printStackTrace();
				return new ServerResponse(true, true, null, "System error, program will be closed");
			}	
		}
		return new ServerResponse(true,true, null,
				"Wrong type of object, MailBoxEntity required to create mail box, error in program code");
	}
	
	
	public ServerResponse getMailBoxEntityByEmailAddress(Object emailAddress) {
		
		if (emailAddress instanceof String) {
			try {
				MailBoxEntity mailBox = mailBoxDAO.findByEmailAddress(((String) emailAddress).toLowerCase());
				return new ServerResponse(false,false,mailBox,null);
			} catch (NoResultException e) {
				return new ServerResponse(true,false,null,"There is no such email address");
			} catch (Exception e) {
				e.printStackTrace();
				return new ServerResponse(true,true,null,"System error, program will be closed");
			}			
		}
		return new ServerResponse(true,true, null,
				"Wrong type of object, String required to get mail box, error in program code");	
	}
}
