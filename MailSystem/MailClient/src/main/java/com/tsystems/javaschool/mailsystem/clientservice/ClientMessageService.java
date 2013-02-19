package com.tsystems.javaschool.mailsystem.clientservice;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.entities.MessageEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.CommandAndDataObject;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;
import com.tsystems.javaschool.mailsystem.shareableObjects.ToDo;

public class ClientMessageService {
	private ObjectInputStream input  = null;
	private ObjectOutputStream output = null;
	
	public ClientMessageService(ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
	}
	
	public ServerResponse createMessage(MailBoxEntity sender, String receiver, String theme, String messageBody) {
		
		try {
			output.writeObject(new CommandAndDataObject(ToDo.GetMailBoxEntityByMailAddress, receiver));
			ServerResponse response = (ServerResponse) input.readObject();
			if (response.isException()) {
				return response;
			}		
			return new ServerResponse(false,false,new MessageEntity(sender,((MailBoxEntity)response.getResult()),theme,messageBody),null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");
		}
	}
	
	public ServerResponse sendMessage(MessageEntity message) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.SendMessage, message));
			return (ServerResponse) input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");			
		}	
	}
	
	public ServerResponse saveMessage(MessageEntity message) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.SaveMessage, message));
			return (ServerResponse) input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");			
		}	
	}
	
	public ServerResponse deleteMessage(MessageEntity message) {
		try {
			output.writeObject(new CommandAndDataObject(ToDo.DeleteMessage, message));
			return (ServerResponse) input.readObject();			
		} catch (Exception e) {
			e.printStackTrace();
			return new ServerResponse(true, true, null, "System error, program will be closed");		
		}	
	}
}
