package com.tsystems.javaschool.mailsystem.mailserver.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.tsystems.javaschool.mailsystem.mailserver.service.FolderService;
import com.tsystems.javaschool.mailsystem.mailserver.service.LoginService;
import com.tsystems.javaschool.mailsystem.mailserver.service.MessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;

 /*
  * object ServerProcess creates for every server client
  */

public class ServerProcess implements Runnable {
	
	private Socket clientSocket = null;
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	private MessageService messageService = null;
	private FolderService folderService = null;
	private LoginService loginService = null;
	
	public ServerProcess(Socket clientSocket, MessageService messageService,
			FolderService folderService, LoginService loginService) {
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
				CommandAndDataObject commandAndData = null;
				
				try {
					commandAndData = (CommandAndDataObject) input.readObject();
				} catch (ClassNotFoundException e) {
					System.out.println(e.getMessage() + "InputStrean is in an indeterminate state, closeing socket");
					break closeSocket;			
				}
				
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
				case FindFoldersForMailBox:
					output.writeObject(folderService.findFoldersForMailBox(commandAndData.getData()));
					break;
				case MoveMessageToAnotherFolder:
					break;
				case Update:
					break;
				case CloseSocket:
					break closeSocket;
				}
			} catch (Exception e) {
				System.out.println("Exception in process of comunication with client " + e.getMessage() + " closing socket");
				e.printStackTrace();
				break closeSocket;
			}
		}
	}
	
	private boolean createInputAndOutputStreams() {
		try {		
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			input = new ObjectInputStream(clientSocket.getInputStream());
			return true;
		} catch (Exception e) {
			System.out.println("Exception in process of creating input and output streams");
			e.printStackTrace();
			return false;
		}	
//		return testConnectionBetweenServerAndClient();
	}
	
//	private boolean testConnectionBetweenServerAndClient()
//	{
//		try {
//			output.writeObject("Connection set");
//			System.out.println((String) input.readObject());
//		} catch (ClassNotFoundException e) {
//			System.out.println("ClassNotFoundException: Class of a serialized object cannot be found");
//			e.printStackTrace();
//			return false;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
}
