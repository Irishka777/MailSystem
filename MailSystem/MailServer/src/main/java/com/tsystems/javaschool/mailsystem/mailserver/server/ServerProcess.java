package com.tsystems.javaschool.mailsystem.mailserver.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.tsystems.javaschool.mailsystem.shareableObjects.Message;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;


 /*
  * object ServerProcess creates for every server client
  */

public class ServerProcess implements Runnable {
	
	private Socket clientSocket = null;
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	private MessageServer messageServer = null;
	private FolderServer folderServer = null;
	private LoginServer loginServer = null;
	
	public ServerProcess(Socket clientSocket, MessageServer messageServer, FolderServer folderServer, LoginServer loginServer) {
		this.clientSocket = clientSocket;
		this.messageServer = messageServer;
		this.folderServer = folderServer;
		this.loginServer = loginServer;
	}
	
	public void run() {	
		
		if (!createInputAndOutputStreams()) {
			return;
		}
		
		closeSocket: while (true) {
			try {
				switch ((ToDo) input.readObject()) {
				case Login:
					break;
				case SendMessage:
					messageServer.sendMessage(input);
					break;
				case DeleteMessage:
					messageServer.deleteMessage(input);
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
				
//				output.writeObject("Ready");
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
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
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
