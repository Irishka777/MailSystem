package com.tsystems.javaschool.mailsystem.clientservice;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;
import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;

public class ClientFolderService {
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	public ClientFolderService(ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
	}
	
	public String createFolder(FolderEntity folder) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.CreateFolder, folder));
		} catch (IOException e) {
			return "Error in process of transfer folder from client to server: " + e.getMessage();		
		}	
		
		try {
			String response = (String) input.readObject();
			return response;
		} catch (ClassNotFoundException e) {
			return "Cannot get response about new folder from server: " + e.getMessage();
		} catch (IOException e) {
			return "Error in process of geting response about new folder from server" + e.getMessage();
		}
	}
	
	public String deleteFolder(FolderEntity folder) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.DeleteFolder, folder));
		} catch (IOException e) {
			return "Error in process of transfer folder from client to server: " + e.getMessage();		
		}	
		
		try {
			String response = (String) input.readObject();
			return response;
		} catch (ClassNotFoundException e) {
			return "Cannot get response about deleted folder from server: " + e.getMessage();
		} catch (IOException e) {
			return "Error in process of geting response about deleted folder from server" + e.getMessage();
		}
	}
	
	public String renameFolder(FolderEntity folder) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.RenameFolder, folder));
		} catch (IOException e) {
			return "Error in process of transfer folder from client to server: " + e.getMessage();		
		}	
		
		try {
			String response = (String) input.readObject();
			return response;
		} catch (ClassNotFoundException e) {
			return "Cannot get response about renamed folder from server: " + e.getMessage();
		} catch (IOException e) {
			return "Error in process of geting response about renamed folder from server" + e.getMessage();
		}
	}
	
}
