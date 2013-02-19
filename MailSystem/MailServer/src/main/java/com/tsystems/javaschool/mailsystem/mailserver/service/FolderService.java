package com.tsystems.javaschool.mailsystem.mailserver.service;

import javax.persistence.NoResultException;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.mailserver.dao.FolderDAO;
import com.tsystems.javaschool.mailsystem.mailserver.dao.FolderDAOImpl;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

public class FolderService {
	FolderDAO folderDAO = new FolderDAOImpl();
	
	public ServerResponse createFolder(Object folder) {
		
		if (folder instanceof FolderEntity) {
			try {
				if (folderDAO.insert((FolderEntity) folder)) {
					return new ServerResponse(false,false,"Folder successfully created",null);
				}
				return new ServerResponse(true, true, null, "System error, error in programe code");
			} catch (Exception e) {
				e.printStackTrace();
				return new ServerResponse(true, true, null, "System error, program will be closed");
			}		
		}
		return new ServerResponse(true,true,null,
				"Wrong type of object, FolderEntity required to create a folder, error in programe code");	
	}
	
	public ServerResponse deleteFolder(Object folder) {
		if (folder instanceof FolderEntity) {
			try {
				if (folderDAO.delete((FolderEntity) folder)) {
					return new ServerResponse(false,false,"Folder successfully deleted",null);
				}
				return new ServerResponse(true, true, null, "System error, error in programe code");
			} catch (Exception e) {
				e.printStackTrace();
				return new ServerResponse(true, true, null, "System error, program will be closed");
			}		
		}
		return new ServerResponse(true,true,null,
				"Wrong type of object, FolderEntity required to delete a folder, error in programe code");
	}
	
	public ServerResponse renameFolder(Object folder) {
		if (folder instanceof FolderEntity) {
			try {
				if (folderDAO.rename((FolderEntity) folder)) {
					return new ServerResponse(false,false,"Folder successfully renamed",null);
				}
				return new ServerResponse(true, true, null, "System error, error in programe code");
			} catch (Exception e) {
				e.printStackTrace();
				return new ServerResponse(true, true, null, "System error, program will be closed");
			}		
		}
		return new ServerResponse(true,true, null,"Wrong type of object, FolderEntity required to rename a folder, error in programe code");
	}
	
	public ServerResponse findFoldersForMailBox(Object mailBox) {
		if (mailBox instanceof MailBoxEntity) {
			try {
				return new ServerResponse(false,false,folderDAO.findFoldersForMailBox((MailBoxEntity)mailBox),null);
			} catch (NoResultException e) {
				return new ServerResponse(true, false, null, "Mail box with such email address does not exist");
			} catch (Exception e) {
				e.printStackTrace();
				return new ServerResponse(true, true, null, "System error, program will be closed");
			}
		}
		return new ServerResponse(true,true, null,
				"Wrong type of object, FolderEntity required to rename a folder, error in programe code");
	}
}
