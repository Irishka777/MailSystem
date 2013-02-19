package com.tsystems.javaschool.mailsystem.mailserver.dao;

import java.util.List;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;

public interface FolderDAO {
	public boolean insert(FolderEntity folder);
	public boolean delete(FolderEntity folder);
	public boolean rename(FolderEntity folder);
	public FolderEntity findByEmailAddressAndFolderName(String folderName, MailBoxEntity emailAddress);
	public List<FolderEntity> findFoldersForMailBox(MailBoxEntity mailBox);
}
