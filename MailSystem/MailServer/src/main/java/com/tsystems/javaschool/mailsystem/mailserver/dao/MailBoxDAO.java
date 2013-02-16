package com.tsystems.javaschool.mailsystem.mailserver.dao;

import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;

public interface MailBoxDAO {
	public String insert(MailBoxEntity mailBox);
	public boolean update(MailBoxEntity mailBox);
	public boolean delete(MailBoxEntity mailBox);
	public MailBoxEntity findByEmailAddress(String emailAddress);
}
