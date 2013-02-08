package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket server = null;
	private int port;
	private Socket clientSocket = null;
	private boolean serverStoped = false;
	
	private MailServer mailServer = new MailServer();
	private FolderServer folderServer = new FolderServer();
	private LoginServer loginServer = new LoginServer();
	
	public Server() {
		port = 9090;
	}
	
	public Server(int portNumber) {
		port = portNumber;
	}
	
	public static void main(String[] args) {		
		Server myServer = new Server();
		myServer.startServer();
	}
	
	public void startServer() {
		
		System.out.println("Server is started...");
			
		if (!openServerSocket()) {
			return;
		}
		
		while (!serverStoped) {
			try {
				clientSocket = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			new Thread(new ServerProcess(clientSocket,mailServer,folderServer,loginServer)).start();
		}
		
		if (!closeServerSocket()) {
			return;
		}
		
		System.out.println("Server stoped");
	}
	
	private boolean openServerSocket() {
		try {
			server = new ServerSocket(port);
			return true;
		} catch (IOException e) {
			System.out.println("IOException: an I/O error occurs when opening the socket");
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean closeServerSocket() {
		try {
			server.close();
			return true;
		} catch (IOException e) {
			System.out.println("IOException: an I/O error occurs when closing the socket.");
			e.printStackTrace();
			return false;
		}
	}
}
