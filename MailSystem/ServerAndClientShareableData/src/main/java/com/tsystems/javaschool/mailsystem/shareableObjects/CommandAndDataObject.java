package com.tsystems.javaschool.mailsystem.shareableObjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CommandAndDataObject implements Serializable {
	private ToDo command;
	private Object data;
	
	public CommandAndDataObject() {}
	
	public CommandAndDataObject(ToDo command, Object data) {
		this.command = command;
		this.data = data;
	}
	
	public void setCommand(ToDo command) {
		this.command = command;
	}
	public ToDo getCommand() {
		return command;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	public Object getData() {
		return data;
	}
}
