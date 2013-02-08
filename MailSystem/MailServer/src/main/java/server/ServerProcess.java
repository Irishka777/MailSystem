package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shareableObjects.Letter;
import shareableObjects.ToDo;

 /*
  * object ServerProcess creates for every server client
  */

public class ServerProcess implements Runnable {
	
	private Socket clientSocket = null;
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	private MailServer mailServer = null;
	private FolderServer folderServer = null;
	private LoginServer loginServer = null;
	
	public ServerProcess(Socket clientSocket, MailServer mailServer, FolderServer folderServer, LoginServer loginServer) {
		this.clientSocket = clientSocket;
		this.mailServer = mailServer;
		this.folderServer = folderServer;
		this.loginServer = loginServer;
	}
	
	public void run() {	
		
		if (!createInputAndOutputStreams()) {
			return;
		}
		
//		while (!clientSocket.isClosed()) {
			try {
				switch ((ToDo) input.readObject()) {
				case Login:
					break;
				case SendLetter:
					mailServer.sendLetter(input);
					break;
				case DeleteLetter:
					mailServer.deleteLetter(input);
					break;
				case CreateFolder:
					break;
				case DeleteFolder:
					break;
				case RenameFolder:
					break;
				case MoveLetterToAnotherFolder:
					break;
				case Update:
					break;
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
//		}	
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
