package com.tsystems.javaschool.mailsystem.mailserver.dao;

import com.tsystems.javaschool.mailsystem.entities.MessageEntity;

public interface MessageDAO {
	public boolean send(MessageEntity message);
	public boolean save(MessageEntity message);
	public boolean delete(MessageEntity message);
}
