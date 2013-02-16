package com.tsystems.javaschool.mailsystem.mailserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.tsystems.javaschool.mailsystem.mailserver.service.FolderService;
import com.tsystems.javaschool.mailsystem.mailserver.service.LoginService;
import com.tsystems.javaschool.mailsystem.mailserver.service.MessageService;

public class Server {
	
	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("mailsystem");
	
	private ServerSocket server = null;
	private int port;
	private Socket clientSocket = null;
	private boolean serverStoped = false;
	
	private MessageService messageService = new MessageService();
	private FolderService folderService = new FolderService();
	private LoginService loginService = new LoginService();
	
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
		try {
			while (!serverStoped) {
				try {
					clientSocket = server.accept();
				} catch (IOException e) {
					System.out.println("IOException: an I/O error occurs when waiting for a connection");
					e.printStackTrace();
				}
				new Thread(new ServerProcess(clientSocket,messageService,folderService,loginService)).start();
			}
		} finally {
			if (!closeServerSocket()) {
				return;
			}
			System.out.println("Server stoped");
		}	
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
