package com.tsystems.javaschool.mailsystem.clientservice;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;

public class ClientFolderService {
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	public ClientFolderService(ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
	}
	
	public ServerResponse createFolder(FolderEntity folder) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.CreateFolder, folder));
			return (ServerResponse) input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");	
		}	
	}
	
	public ServerResponse deleteFolder(FolderEntity folder) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.DeleteFolder, folder));
			return (ServerResponse) input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");	
		}	
	}
	
	public ServerResponse renameFolder(FolderEntity folder) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.RenameFolder, folder));
			return (ServerResponse) input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");		
		}	
	}
	
	public ServerResponse findFoldersForMailBox(MailBoxEntity mailBox) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.FindFoldersForMailBox, mailBox));
			return (ServerResponse) input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");		
		}	
	}
	
}
