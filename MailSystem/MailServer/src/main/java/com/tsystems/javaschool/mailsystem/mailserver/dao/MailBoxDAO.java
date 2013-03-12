package com.tsystems.javaschool.mailsystem.mailserver.dao;

import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;

public interface MailBoxDAO {
	public boolean insert(MailBoxEntity mailBox);
	public MailBoxEntity update(MailBoxEntity mailBox);
	public boolean delete(MailBoxEntity mailBox);
	public MailBoxEntity findByEmail(String emailAddress);
}
