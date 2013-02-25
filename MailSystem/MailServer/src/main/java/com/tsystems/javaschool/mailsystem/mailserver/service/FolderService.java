package com.tsystems.javaschool.mailsystem.mailserver.service;

import javax.persistence.NoResultException;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.mailserver.dao.FolderDAO;
import com.tsystems.javaschool.mailsystem.mailserver.dao.FolderDAOImpl;
import com.tsystems.javaschool.mailsystem.mailserver.server.Server;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

public class FolderService {
	FolderDAO folderDAO = new FolderDAOImpl();
	
	public ServerResponse createFolder(Object folder) {
		try {
			if (folderDAO.insert((FolderEntity) folder)) {
				Server.logger.info("Folder " + ((FolderEntity) folder).getFolderName() + " successfully created");
				return new ServerResponse(false, false, "Folder successfully created", null);
			}
			Server.logger.error("If the program is working properly, this message should not appear");
			return new ServerResponse(true, true, null, "Error in programe code");
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, FolderEntity required to create a folder, error in programe code");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
	
	public ServerResponse deleteFolder(Object folder) {
		try {
			if (folderDAO.delete((FolderEntity) folder)) {
				Server.logger.info("Folder " + ((FolderEntity) folder).getFolderName() + " successfully deleted");
				return new ServerResponse(false, false, "Folder successfully deleted", null);
			}
			Server.logger.error("If the program is working properly, this message should not appear");
			return new ServerResponse(true, true, null, "Error in programe code");
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, FolderEntity required to delete a folder, error in programe code");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
	
	public ServerResponse renameFolder(Object folder) {
		try {
			if (folderDAO.update((FolderEntity) folder)) {
				Server.logger.info("Folder " + ((FolderEntity) folder).getFolderName() + " successfully renamed");
				return new ServerResponse(false, false, "Folder successfully renamed", null);
			}
			Server.logger.error("If the program is working properly, this message should not appear");
			return new ServerResponse(true, true, null, "Error in programe code");
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, FolderEntity required to rename a folder, error in programe code");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
	
	public ServerResponse moveMessageToAnotherFolder(Object folder) {
		try {
			if (folderDAO.update((FolderEntity) folder)) {
				Server.logger.info("Message successfully moved into folder " + ((FolderEntity) folder).getFolderName());
				return new ServerResponse(false, false, "Message successfully moved to another folder", null);
			}
			Server.logger.error("If the program is working properly, this message should not appear");
			return new ServerResponse(true, true, null, "Error in programe code");
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, FolderEntity required to update a folder, error in programe code");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
	
	public ServerResponse getFolder(Object folder) {
		try {
			return new ServerResponse(false, false, folderDAO.getFolder((FolderEntity)folder), null);
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, FolderEntity required to get a folder, error in programe code");
		} catch (NoResultException e) {
			Server.logger.warn("Folder with such id does not exist", e);
			return new ServerResponse(true, false, null, "Folder with such id does not exist");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
	
	public ServerResponse findFoldersForMailBox(Object mailBox) {
		try {
			return new ServerResponse(false, false, folderDAO.findFoldersForMailBox((MailBoxEntity)mailBox), null);
		} catch (ClassCastException e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null,
					"Wrong type of object, MailBox required to get mail box folders, error in programe code");
		} catch (NoResultException e) {
			Server.logger.warn("Mail box with such email address does not exist", e);
			return new ServerResponse(true, false, null, "Mail box with such email address does not exist");
		} catch (Exception e) {
			Server.logger.error(e.getMessage(), e);
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
}
