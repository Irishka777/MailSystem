package com.tsystems.javaschool.mailsystem.mailserver.server;

import java.net.ServerSocket;
import java.net.Socket;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.tsystems.javaschool.mailsystem.mailserver.service.FolderService;
import com.tsystems.javaschool.mailsystem.mailserver.service.MailBoxService;
import com.tsystems.javaschool.mailsystem.mailserver.service.MessageService;

public class Server {
	
	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("mailsystem");
	public static Logger logger = Logger.getLogger(Server.class);
	
	private ServerSocket server = null;
	private int port;
	private Socket clientSocket = null;
	private boolean serverStoped = false;
	
	private MessageService messageService = new MessageService();
	private FolderService folderService = new FolderService();
	private MailBoxService loginService = new MailBoxService();
	
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
		
		Server.logger.info("Server is started...");
			
		if (!openServerSocket()) {
			return;
		}
		try {
			while (!serverStoped) {
				try {
					clientSocket = server.accept();
				} catch (Exception e) {
					Server.logger.error("Error occurs when waiting for a connection", e);
				}
				new Thread(new ServerProcess(clientSocket,messageService,folderService,loginService)).start();
			}
		} finally {
			if (!closeServerSocket()) {
				return;
			}
			Server.logger.info("Server stoped");
		}	
	}
	
	private boolean openServerSocket() {
		try {
			server = new ServerSocket(port);
			Server.logger.info("Server socked is opened");
			return true;
		} catch (Exception e) {
			Server.logger.error("Error occurs when opening the socket", e);
			return false;
		}
	}
	
	private boolean closeServerSocket() {
		try {
			server.close();
			Server.logger.info("Server socked is closed");
			return true;
		} catch (Exception e) {
			Server.logger.error("Error occurs when closing the socket", e);
			return false;
		}
	}
}
