package com.tsystems.javaschool.mailsystem.mailserver.service;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAO;
import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAOImpl;
import com.tsystems.javaschool.mailsystem.mailserver.server.Server;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

public class MailBoxService {
	private MailBoxDAO mailBoxDAO = new MailBoxDAOImpl();
	
	public ServerResponse login(Object neededMailBox) {
		try {
			MailBoxEntity mailBox = mailBoxDAO.findByEmailAddress(((MailBoxEntity) neededMailBox).getEmailAddress());
			byte[] password = mailBox.getPassword();
			byte[] neededPassword = ((MailBoxEntity) neededMailBox).getPassword();
			if (password.length != neededPassword.length) {
				Server.logger.info("Entered password for mailbox with email address " 
						+ ((MailBoxEntity) neededMailBox).getEmailAddress() + " is wrong");
				return new ServerResponse(true, false, null, "Entered password is wrong");
			}
			for (int i = 0; i < password.length; i++) {
				if (password[i] != neededPassword[i]) {
					Server.logger.info("Entered password for mailbox with email address " 
							+ ((MailBoxEntity) neededMailBox).getEmailAddress() + " is wrong");
					return new ServerResponse(true, false, null, "Entered password is wrong");
				}
			}
			Server.logger.info("Successful login into mailbox " + mailBox.getEmailAddress());
			return new ServerResponse(false, false, mailBox, null);
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, MailBoxEntity required to login, error in programe code");
		} catch (NoResultException e) {
			Server.logger.warn("Mailbox with such email address does not exist", e);
			return new ServerResponse(true, false, null, "Mailbox with such email address does not exist");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
	
	public ServerResponse registration(Object mailBox) {
		try {
			if (mailBoxDAO.insert((MailBoxEntity) mailBox)) {
				Server.logger.info("Mailbox with email address" + ((MailBoxEntity) mailBox).getEmailAddress() + " successfully created");
				return new ServerResponse(false, false, "Mailbox successfully created", null);
			}
			Server.logger.error("If the program is working properly, this message should not appear");
			return new ServerResponse(true, true, null, "Error in programe code");
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, MailBoxEntity required to create a mailbox, error in programe code");
		} catch (PersistenceException e) {
			Server.logger.warn("Mailbox with such email address already exists", e);
			return new ServerResponse(true, false, null,
					"Mail box with such email address already exists, try to use another mailbox name");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
	
	public ServerResponse updateMailBoxData(Object mailBox) {
		try {
			return new ServerResponse(false, false, mailBoxDAO.update((MailBoxEntity) mailBox), null);
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, MailBoxEntity required to update a mailbox, error in programe code");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
	
	public ServerResponse getMailBoxEntityByEmailAddress(Object emailAddress) {
		try {
			return new ServerResponse(false, false, mailBoxDAO.findByEmailAddress(((String) emailAddress).toLowerCase()), null);
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, MailBox required to get a mail box, error in programe code");
		} catch (NoResultException e) {
			Server.logger.warn("Mail box with such email address does not exists", e);
			return new ServerResponse(true, false, null, "Mail box with such email address does not exists");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}	
	}
}
