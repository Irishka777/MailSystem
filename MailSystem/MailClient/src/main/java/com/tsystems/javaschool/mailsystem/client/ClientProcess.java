package com.tsystems.javaschool.mailsystem.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.tsystems.javaschool.mailsystem.clientservice.ClientFolderService;
import com.tsystems.javaschool.mailsystem.clientservice.ClientMailBoxService;
import com.tsystems.javaschool.mailsystem.clientservice.ClientMessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;


public class ClientProcess {
	
	public static Logger logger = Logger.getLogger(ClientProcess.class);
	private String serverHost;
	private int serverPort;
	private Socket clientSocket = null;
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	private ClientMailBoxService clientMailBoxService = null;
	private ClientMessageService clientMessageService = null;
	private ClientFolderService clientFolderService = null;
	
	public ClientProcess() {
		serverHost = "localhost";
		serverPort = 9090;	
	}
	
	public ClientProcess(String host, int port) {
		serverHost = host;
		serverPort = port;
	}
	
	public ClientMailBoxService getClientMailBoxService() {
		return clientMailBoxService;
	}
	
	public ClientMessageService getClientMessageService() {
		return clientMessageService;
	}
	
	public ClientFolderService getClientFolderService() {
		return clientFolderService;
	}
	
	public boolean startClient() {
		if (!openClientSocket()) {
			return false;
		}
		if (!createInputAndOutputStreams()) {
			return false;
		}
		if (!createClientServices()) {
			return false;
		}
		
		ClientProcess.logger.info("Client started...");
		
//		System.out.println(clientMailBoxService.registration(
//				clientMailBoxService.createMailBox("mail@mail.ru", "123","Vasia", "Petrov", 1986, 9, 3, "4325665")));
//		
//		System.out.println(clientMailBoxService.registration(
//				clientMailBoxService.createMailBox("ivanov@mail.ru", "password", "Petr", "Ivanov", 1990, 2, 2, "6543214")));
//		
//		System.out.println(clientMailBoxService.registration(
//				clientMailBoxService.createMailBox("mishin@mail.ru", "111","Misha", "Mishin", 1980, 5, 3, "6425443")));
//		
//		System.out.println(clientMailBoxService.registration(
//				clientMailBoxService.createMailBox("sashin@mail.ru", "111","Sasha", "sSashin", 1981, 5, 3, "1234567")));
		return true;
	}
	
	public boolean stopClient() {
		return closeClientSocket();
	}
	
	private boolean openClientSocket() {
		try {
			clientSocket = new Socket(serverHost,serverPort);
			ClientProcess.logger.info("Client socket is opened");
			return true;
		} catch (UnknownHostException e) {
			ClientProcess.logger.error("UnknownHostException: the IP address of the host could not be determined",e);
			return false;
		} catch (ConnectException e) {
			ClientProcess.logger.error("Connection refused: server do not started",e);
			return false;
		} catch (Exception e) {
			ClientProcess.logger.error("Unexpected error in proces of creatin client socket",e);
			return false;
		}
	}
	
	private boolean createInputAndOutputStreams() {
		try {	
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			input = new ObjectInputStream(clientSocket.getInputStream());
			ClientProcess.logger.info("Input and output streams are created");
			return true;
		} catch (Exception e) {
			ClientProcess.logger.error("Faild to create input and output streams",e);
			try {
				clientSocket.close();
			} catch (Exception e1) {
				ClientProcess.logger.error("Faild to close created socket",e1);
			}
			return false;
		}
	}
	
	private boolean createClientServices() {
		clientMailBoxService = new ClientMailBoxService(input,output);
		clientMessageService = new ClientMessageService(input,output);
		clientFolderService = new ClientFolderService(input,output);
		return true;
	}
	
	private boolean closeClientSocket() {
		try {			
			output.writeObject(new CommandAndDataObject(ToDo.CloseSocket, null));
			clientSocket.close();
			ClientProcess.logger.info("Client stoped");
			return true;
		} catch (Exception e) {
			ClientProcess.logger.error(
					"Exception in process of closing clientSocket, socket have not closed correctly",e);
			return false;
		}
	}
}