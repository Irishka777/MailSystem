package com.tsystems.javaschool.mailsystem.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.tsystems.javaschool.mailsystem.shareableObjects.Message;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;


public class Client {

	private String serverHost;
	private int serverPort;
	private Socket clientSocket = null;
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	public Client()
	{
		serverHost = "localhost";
		serverPort = 9090;
	}
	
	public Client(String host, int port)
	{
		serverHost = host;
		serverPort = port;
	}
	
	public static void main(String[] args) {
		Client myClient = new Client();
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
			
			Message message = createMessage("Mail1", "Mail2", "Test", "text");
			
			sendMessage(message);
			
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
		} catch (UnknownHostException e1) {
			System.out.println("UnknownHostException: the IP address of the host could not be determined");
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			System.out.println("IOException: an I/O error occurs when creating the socket");
			e1.printStackTrace();
			return false;
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
		
		return testConnectionBetweenClientAndServer();
	}
	
	private boolean testConnectionBetweenClientAndServer()
	{
		try {
			System.out.println((String) input.readObject());
			output.writeObject("Connection set");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean closeClientSocket()
	{
		try {
			output.writeObject(ToDo.CloseSocket);
			clientSocket.close();
			return true;
		} catch (IOException e) {
			System.out.println("IOException in process of closeing clientSocket");
			e.printStackTrace();
			return false;
		}
	}
	
	public Message createMessage(String sender, String receiver, String theme, String messageBody) {
		Message message = new Message();
		message.setSender(sender);
		message.setReceiver(receiver);		
		message.setDate(new java.sql.Date(System.currentTimeMillis()));
		message.setTheme(theme);
		message.setMessageBody(messageBody);
		return message;
	}
	
	public boolean sendMessage(Message message) {
		try {
			output.writeObject(ToDo.SendMessage);
			output.writeObject(message);		
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;		
		}	
	}
	
	public boolean saveMessage(Message message) {
		try {
			output.writeObject(ToDo.SaveMessage);
			output.writeObject(message);		
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;		
		}
	}
}