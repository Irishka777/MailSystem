package com.tsystems.javaschool.mailsystem.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.tsystems.javaschool.mailsystem.clientservice.ClientFolderService;
import com.tsystems.javaschool.mailsystem.clientservice.ClientMailBoxService;
import com.tsystems.javaschool.mailsystem.clientservice.ClientMessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;


public class ClientProcess {
	
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
		System.out.println("Client started");
		
//		System.out.println(clientMailBoxService.registration(
//				clientMailBoxService.createMailBox("mail@mail.ru", "123","Vasia", "Petrov", 1986, 9, 3, "4325665")));
//		
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
			return true;
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException: the IP address of the host could not be determined");
			e.printStackTrace();
			return false;
		} catch (ConnectException e) {
			System.out.println("Connection refused: server do not started");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("Unknown error in proces of creatin client socket");
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean createInputAndOutputStreams() {
		try {	
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			input = new ObjectInputStream(clientSocket.getInputStream());
			return true;
		} catch (Exception e) {
			try {
				clientSocket.close();
			} catch (Exception e1) {
				System.out.println("Faild to close created socket");
				e1.printStackTrace();
			}
			System.out.println("Faild to create input and output streams");
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean createClientServices() {
		clientMailBoxService = new ClientMailBoxService(input,output);
		clientMessageService = new ClientMessageService(input,output);
		clientFolderService = new ClientFolderService(input,output);
		return true;
	}
	
	private boolean closeClientSocket()
	{
		try {			
			output.writeObject(new CommandAndDataObject(ToDo.CloseSocket, null));
			clientSocket.close();
			System.out.println("Client stoped");
			return true;
		} catch (Exception e) {
			System.out.println("Exception in process of closing clientSocket, socket did not closed correctly");
			e.printStackTrace();
			return false;
		}
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
}