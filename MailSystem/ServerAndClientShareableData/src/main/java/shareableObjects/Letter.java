package shareableObjects;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Letter implements Serializable {
	
	private String sender;
	private String receiver;
	private Date date;
	private String theme;
	private String[] letterBody;
	
	public Letter() {
		sender = "default";
		receiver = "default";
		date = new java.util.Date();
		theme = "default";
		letterBody = null;
	}
	
	public Letter(String sender, String receiver, Date date, String theme, String[] letterBody) {
		this.sender = sender;
		this.receiver = receiver;
		this.date = date;
		this.theme = theme;
		this.letterBody = letterBody;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getReceiver() {
		return receiver;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getTheme() {
		return theme;
	}
	
	public String[] getLetterBody() {
		return letterBody;
	}
	
	public String toString() {
		return "from " + sender + " to " + receiver;
	}
}
