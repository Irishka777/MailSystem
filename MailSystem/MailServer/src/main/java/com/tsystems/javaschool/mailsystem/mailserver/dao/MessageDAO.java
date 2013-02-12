package com.tsystems.javaschool.mailsystem.mailserver.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tsystems.javaschool.mailsystem.shareableObjects.Message;

public class MessageDAO {
	
	private Connection connection = null;
	
	private boolean openConnection() throws SQLException {
		String url = "jdbc:mysql://localhost/MailSystemDB";
		String userName = "root";
		String password = "admin";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		connection = DriverManager.getConnection(url, userName, password);
		
		return true;
	}
	
	public boolean createMessage(Message message) {
		
		try {
			if (openConnection()) {
				insertMessage(message);
			} else {
				return false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}		
		
		return true;
	}
	
	
	private synchronized void insertMessage(Message message) throws SQLException {
		insertMessageInMessageTable(message);
		insertMessageInFolderTable(message);	
	}
	
	private void insertMessageInMessageTable(Message message) throws SQLException {
		
		PreparedStatement prep = connection.prepareStatement("INSERT INTO message(sender,receiver,date," +
				"theme,messageBody) VALUES (?,?,?,?,?)");
		
		prep.setString(1, message.getSender());
		prep.setString(2, message.getReceiver());
		prep.setDate(3, message.getDate());
		prep.setString(4, message.getTheme());
		prep.setString(5, message.getMessageBody());
		
		prep.executeUpdate();
			
	}
	
	private void insertMessageInFolderTable(Message message) throws SQLException {
		
		PreparedStatement getIDMessage = connection.prepareStatement("SELECT max(idMessage) FROM Message;");
		getIDMessage.executeQuery().next();
		int idMessage = getIDMessage.getResultSet().getInt(1);
		
		PreparedStatement insertFolder = connection.prepareStatement("INSERT INTO folder(folderName,emailAddress,idMessage)" +
				" VALUES (?,?,?)");
		
		insertFolder.setString(1, "Incoming messages");
		insertFolder.setString(2, message.getSender());
		insertFolder.setInt(3,idMessage);
		
		insertFolder.executeUpdate();
		
		insertFolder.setString(1, "Outcoming messages");
		insertFolder.setString(2, message.getReceiver());
		insertFolder.setInt(3,idMessage);
		
		insertFolder.executeUpdate();			
	}
}
