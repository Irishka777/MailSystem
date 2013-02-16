package com.tsystems.javaschool.mailsystem.mailserver.dao;

import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.MessageEntity;

public interface FolderDAO {
	public boolean addMessageInFolder(FolderEntity folder, MessageEntity message);
}
