package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import shareableObjects.Letter;
import shareableObjects.ToDo;

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
		
		if (!createInputAndOutputStreams()) {
			closeClientSocket();
			return;
		}
		
			String[] l = new String[2];
			l[0] = "text1";
			l[1] = "text2";
			Letter letter = new Letter("Name1","Name2",new java.util.Date(),"theme",l);
			sendLetter(letter);
		
			
		if (!closeClientSocket()) {
			return;
		}
		
		System.out.println("Client stoped");
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
			clientSocket.close();
			return true;
		} catch (IOException e) {
			System.out.println("IOException in process of closeing clientSocket");
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendLetter(Letter letter) {
		try {
			output.writeObject(ToDo.SendLetter);
			output.writeObject(letter);		
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}