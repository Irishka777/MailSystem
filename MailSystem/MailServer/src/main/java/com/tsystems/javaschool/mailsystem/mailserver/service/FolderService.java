package com.tsystems.javaschool.mailsystem.mailserver.service;

import com.tsystems.javaschool.mailsystem.mailserver.dao.FolderDAO;
import com.tsystems.javaschool.mailsystem.mailserver.dao.FolderDAOImpl;
import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;

public class FolderService {
	FolderDAO folderDAO = new FolderDAOImpl();
	
	public String createFolder(Object folder) {		
		if (folder instanceof FolderEntity) {			
			return folderDAO.insert((FolderEntity) folder);
		}
		System.out.println("Wrong type of object, FolderEntity required to create a folder");
		return "Wrong type of object, FolderEntity required to create a folder";	
	}
	
	public String deleteFolder(Object folder) {
		if (folder instanceof FolderEntity) {			
			return folderDAO.delete((FolderEntity) folder);
		}
		System.out.println("Wrong type of object, FolderEntity required to delete a folder");
		return "Wrong type of object, FolderEntity required to delete a folder";	
	}
	
	public String renameFolder(Object folder) {
		if (folder instanceof FolderEntity) {			
			return folderDAO.rename((FolderEntity) folder);
		}
		System.out.println("Wrong type of object, FolderEntity required to rename a folder");
		return "Wrong type of object, FolderEntity required to rename a folder";	
	}
}
