package com.tsystems.javaschool.mailsystem.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.tsystems.javaschool.mailsystem.clientservice.ClientFolderService;
import com.tsystems.javaschool.mailsystem.clientservice.ClientMailBoxService;
import com.tsystems.javaschool.mailsystem.clientservice.ClientMessageService;
import com.tsystems.javaschool.mailsystem.shareableObjects.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;
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
	
	public static void main(String[] args) {
		ClientProcess myClient = new ClientProcess();
		myClient.startClient();
	}
	
	public void startClient() {
		
		System.out.println("Client is started...");
		
		if (!openClientSocket()) {
			return;
		}
		
		try {
			if (!createInputAndOutputStreams()) {
				closeClientSocket();
				return;
			}
			
			createClientServices();
			
			MailBoxEntity mailBox = clientMailBoxService.
					createMailBox("name1", "surname1", 1967, 12, 3, "888", "ert", "123");
			System.out.println(clientMailBoxService.registration(mailBox));
			
			System.out.println(clientMailBoxService.registration(
					clientMailBoxService.createMailBox("name2", "surname2", 1986, 9, 3, "111", "mail", "123")));
			
			System.out.println(clientMailBoxService.registration(
					clientMailBoxService.createMailBox("name3", "surname3", 1990, 2, 2, "56-4", "yre", "123")));
			
			System.out.println(clientMailBoxService.registration(
					clientMailBoxService.createMailBox("name4", "surname4", 1980, 5, 3, "642", "ree", "123")));
			
			System.out.println(clientMailBoxService.registration(
					clientMailBoxService.createMailBox("name4", "surname1", 1981, 5, 3, "61", "ree", "121")));
			
			System.out.println(clientMessageService.sendMessage(
					clientMessageService.createMessage(mailBox,"mail","theme", "text")));
			
			System.out.println(clientMessageService.saveMessage(
					clientMessageService.createMessage(mailBox,"mail","theme", "text")));
			
			System.out.println(clientFolderService.createFolder(new FolderEntity("new folder",mailBox)));
			System.out.println(clientFolderService.createFolder(new FolderEntity("Draft messages",mailBox)));
			
		} finally {
			if (!closeClientSocket()) {
				return;
			}			
			System.out.println("Client stoped");
		}	
	}
	
	private boolean openClientSocket() {
		try {
			clientSocket = new Socket(serverHost,serverPort);
			return true;
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException: the IP address of the host could not be determined");
			return false;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	private boolean createInputAndOutputStreams() {
		try {	
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			input = new ObjectInputStream(clientSocket.getInputStream());
			return testConnectionBetweenClientAndServer();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}				
	}
	
	private boolean testConnectionBetweenClientAndServer()
	{
		try {
			System.out.println((String) input.readObject());
			output.writeObject("Connection set");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
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
			return true;
		} catch (IOException e) {
			System.out.println("IOException in process of closing clientSocket");
			return false;
		}
	}	
}