package com.tsystems.javaschool.mailsystem.mailserver.dao;

import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;

public interface FolderDAO {
	public String insert(FolderEntity folder);
	public String delete(FolderEntity folder);
	public String rename(FolderEntity folder);
	public FolderEntity findByEmailAddressAndFolderName(String folderName, MailBoxEntity emailAddress);
}
