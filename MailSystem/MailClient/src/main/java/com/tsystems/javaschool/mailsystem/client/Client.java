package com.tsystems.javaschool.mailsystem.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

//import com.tsystems.javaschool.mailsystem.shareableObjects.MailBox;
import com.tsystems.javaschool.mailsystem.shareableObjects.MailBoxEntity;
//import com.tsystems.javaschool.mailsystem.shareableObjects.Message;
import com.tsystems.javaschool.mailsystem.shareableObjects.MessageEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;
//import com.tsystems.javaschool.mailsystem.shareableObjects.User;
import com.tsystems.javaschool.mailsystem.shareableObjects.UserEntity;


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
			
			MailBoxEntity mailBox = createMailBox("name1", "surname1", 1967, 12, 3, "888", "ert", "123");
			registration(mailBox);
			
			registration(createMailBox("name2", "surname2", 1986, 9, 3, "111", "mail", "123"));
			
			registration(createMailBox("name3", "surname3", 1990, 2, 2, "56-4", "yre", "123"));
			
			registration(createMailBox("name4", "surname4", 1980, 5, 3, "642", "ree", "123"));
			
			sendMessage(createMessage(mailBox,"mail","theme", "text"));
			
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
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
	
	public MessageEntity createMessage(MailBoxEntity sender, String receiver, String theme, String messageBody) {
		MailBoxEntity receiverMailBox = null;
		try {
			output.writeObject(ToDo.GetMailBoxEntityByMailAddress);
			output.writeObject(receiver);
			receiverMailBox = (MailBoxEntity) input.readObject();
			return new MessageEntity(sender,receiverMailBox,theme,messageBody);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {		
			e.printStackTrace();
			return null;
		}	
	}
	
	public boolean sendMessage(MessageEntity message) {		
		try {
			output.writeObject(ToDo.SendMessage);
			output.writeObject(message);
			if (input.readObject().toString().equals("false")) {			
				System.out.println("there was an error in process of sending your message");
				System.out.println("Reseiver with such email address does not exist");
				return false;
			}
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;		
		}
	}
	
//	public boolean saveMessage(Message message) {
//		try {
//			output.writeObject(ToDo.SaveMessage);
//			output.writeObject(message);		
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;		
//		}
//	}
	
	public boolean registration(MailBoxEntity mailBox) {
		try {
			output.writeObject(ToDo.Registration);
			output.writeObject(mailBox);
			if (input.readObject().toString().equals("false")) {			
				System.out.println("try to use another email address");
				return false;
			}		
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;		
		}
	}
	
	public MailBoxEntity createMailBox(String name, String surname, int year,int month, int day,
			String phoneNumber, String emailAddress, String password) {
		return new MailBoxEntity(emailAddress, password, new UserEntity(name,surname,year,month,day,phoneNumber));
	}
}