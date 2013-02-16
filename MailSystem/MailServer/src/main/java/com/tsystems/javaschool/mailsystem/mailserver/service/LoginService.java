package com.tsystems.javaschool.mailsystem.mailserver.service;

//import java.io.IOException;
//import java.io.ObjectInputStream;

import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAO;
import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAOImpl;
import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;

public class LoginService {
	private MailBoxDAO mailBoxDAO = new MailBoxDAOImpl();
	
//	public boolean registration(ObjectInputStream input) {
//		try {
//			MailBoxEntity mailBox = (MailBoxEntity) input.readObject();
//			return mailBoxDAO.insert(mailBox);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	
	public String registration(Object mailBox) {
		
		if (mailBox instanceof MailBoxEntity) {
			return mailBoxDAO.insert((MailBoxEntity) mailBox);
		}
		return "Wrong type of object, MailBoxEntity required to create mail box";
		
//		try {
//			MailBoxEntity mailBox = (MailBoxEntity) input.readObject();
//			return mailBoxDAO.insert(mailBox);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
	}
	
	
	public MailBoxEntity getMailBoxEntityByEmailAddress(Object emailAddress) {
		
		if (emailAddress instanceof String) {
			return mailBoxDAO.findByEmailAddress((String) emailAddress);
		}
		System.out.println("Wrong type of object, String required to get mail box");
		return null;		
	}
	
//	public MailBoxEntity getMailBoxEntityByEmailAddress(ObjectInputStream input) {
//		try {
//			String emailAddress = (String) input.readObject();
//			return mailBoxDAO.findByEmailAddress(emailAddress);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
}
