package com.tsystems.javaschool.mailsystem.mailserver.dao;

import com.tsystems.javaschool.mailsystem.shareableObjects.MessageEntity;

public interface MessageDAO {
	public String send(MessageEntity message);
	public String save(MessageEntity message);
	public String delete(MessageEntity message);
}
