package com.tsystems.javaschool.mailsystem.mailserver.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.tsystems.javaschool.mailsystem.mailserver.service.FolderService;
import com.tsystems.javaschool.mailsystem.mailserver.service.LoginService;
import com.tsystems.javaschool.mailsystem.mailserver.service.MessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;


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
				switch ((ToDo) input.readObject()) {
				case Registration:		
					output.writeObject(loginService.registration(input));
					break;
				case Login:
					break;
				case GetMailBoxEntityByMailAddress:
					output.writeObject(loginService.getMailBoxEntityByEmailAddress(input));
					break;
				case SendMessage:
					output.writeObject(messageService.sendMessage(input));
					break;
				case DeleteMessage:
					messageService.deleteMessage(input);
					break;
				case SaveMessage:
					break;
				case CreateFolder:
					break;
				case DeleteFolder:
					break;
				case RenameFolder:
					break;
				case MoveMessageToAnotherFolder:
					break;
				case Update:
					break;
				case CloseSocket:
					break closeSocket;
				}
			} catch (ClassNotFoundException e) {
				
				System.out.println("ClassNotFoundException: Class of a serialized object cannot be found");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				break closeSocket;
			}
		}	
	}
	
	private boolean createInputAndOutputStreams() {
		try {		
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			input = new ObjectInputStream(clientSocket.getInputStream());					
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return testConnectionBetweenServerAndClient();
	}
	
	private boolean testConnectionBetweenServerAndClient()
	{
		try {
			output.writeObject("Connection set");
			System.out.println((String) input.readObject());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: Class of a serialized object cannot be found");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
