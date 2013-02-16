package com.tsystems.javaschool.mailsystem.mailserver.dao;

import com.tsystems.javaschool.mailsystem.shareableObjects.MessageEntity;

public interface MessageDAO {
	public boolean insert(MessageEntity message);
	public boolean delete(MessageEntity message);
}
