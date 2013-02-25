package com.tsystems.javaschool.mailsystem.mailserver.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.tsystems.javaschool.mailsystem.mailserver.service.FolderService;
import com.tsystems.javaschool.mailsystem.mailserver.service.MailBoxService;
import com.tsystems.javaschool.mailsystem.mailserver.service.MessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;

 /*
  * object ServerProcess is created for every server client
  */

public class ServerProcess implements Runnable {
	
	private Socket clientSocket = null;
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	private MessageService messageService = null;
	private FolderService folderService = null;
	private MailBoxService loginService = null;
	
	public ServerProcess(Socket clientSocket, MessageService messageService,
			FolderService folderService, MailBoxService loginService) {
		this.clientSocket = clientSocket;
		this.messageService = messageService;
		this.folderService = folderService;
		this.loginService = loginService;
	}
	
	public void run() {	
		
		if (!createInputAndOutputStreams()) {
			return;
		}
		
		closeSocket: while (true) {
			try {
				CommandAndDataObject commandAndData = (CommandAndDataObject) input.readObject();

				switch (commandAndData.getCommand()) {
				case Registration:		
					output.writeObject(loginService.registration(commandAndData.getData()));
					break;
				case Login:
					output.writeObject(loginService.login(commandAndData.getData()));
					break;
				case GetMailBoxEntityByMailAddress:
					output.writeObject(loginService.getMailBoxEntityByEmailAddress(commandAndData.getData()));
					break;
				case SendMessage:
					output.writeObject(messageService.sendMessage(commandAndData.getData()));
					break;
				case SaveMessage:
					output.writeObject(messageService.saveMessage(commandAndData.getData()));
					break;
				case MoveMessageToAnotherFolder:
					output.writeObject(folderService.moveMessageToAnotherFolder(commandAndData.getData()));
					break;
				case DeleteMessage:
					output.writeObject(messageService.deleteMessage(commandAndData.getData()));
					break;
				case CreateFolder:
					output.writeObject(folderService.createFolder(commandAndData.getData()));
					break;
				case DeleteFolder:
					output.writeObject(folderService.deleteFolder(commandAndData.getData()));
					break;
				case RenameFolder:
					output.writeObject(folderService.renameFolder(commandAndData.getData()));
					break;
				case GetFolder:
					output.writeObject(folderService.getFolder(commandAndData.getData()));
					break;
				case FindFoldersForMailBox:
					output.writeObject(folderService.findFoldersForMailBox(commandAndData.getData()));
					break;
				case CloseSocket:
					break closeSocket;
				}
				
			} catch (ClassNotFoundException e) {
				Server.logger.error("Wrong type of object, inputStrean is in the indeterminate state; socket will be closed", e);
				break closeSocket;			
			} catch (Exception e) {
				Server.logger.error("Exception in process of comunication with client; socket will be closed", e);
				break closeSocket;
			}
		}
	}
	
	private boolean createInputAndOutputStreams() {
		try {		
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			input = new ObjectInputStream(clientSocket.getInputStream());
			Server.logger.info("Input and output streams are created");
			return true;
		} catch (Exception e) {
			Server.logger.error("Exception in process of creating input and output streams", e);
			return false;
		}	
	}
}
