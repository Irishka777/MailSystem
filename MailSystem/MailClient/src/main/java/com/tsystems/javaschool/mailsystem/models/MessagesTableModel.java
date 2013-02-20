package com.tsystems.javaschool.mailsystem.models;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.tsystems.javaschool.mailsystem.entities.MessageEntity;

@SuppressWarnings("serial")
public class MessagesTableModel extends AbstractTableModel {
	
	private String[] columnNames = {"Theme","Sender","Receiver", "Data"};
	private List<MessageEntity> listOfMessages;
	
	public void setListOfMessages(List<MessageEntity> listOfMessages) {
		this.listOfMessages = listOfMessages;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		if (listOfMessages == null)
			return 0;
		return listOfMessages.size();
	}

	public Object getValueAt(int row, int col) {
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		MessageEntity message = listOfMessages.get(row);
	    switch (col) {
	      case 0: return message.getTheme();
	      case 1: return message.getSender();
	      case 2: return message.getReceiver();
	      case 3: return dateFormat.format(message.getDate().getTimeInMillis());
	      default: return "";
	    }
	}
	
	public MessageEntity getMessageAt(int row) {
		return listOfMessages.get(row);
	}
	
	public String getMessageBody(int row) {
		return listOfMessages.get(row).getMessageBody();
	}
	
	public MessageEntity deleteMessageAt(int row) {
		return listOfMessages.remove(row);
	}
}
