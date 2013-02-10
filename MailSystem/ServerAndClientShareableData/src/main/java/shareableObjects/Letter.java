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
	
	public void setSender(String senderEmail) {
		sender = senderEmail;
	}
	public String getSender() {
		return sender;
	}
	
	public void setReceiver(String receiverEmail) {
		receiver = receiverEmail;
	}
	public String getReceiver() {
		return receiver;
	}
	
	public void setDate(Date currentDate) {
		date = currentDate;
	}
	public Date getDate() {
		return date;
	}
	
	public void setTheme(String letterTheme) {
		theme = letterTheme;
	}
	public String getTheme() {
		return theme;
	}
	
	public void setLetterBody(String[] letterBody) {
		this.letterBody = letterBody;
	}
	public String[] getLetterBody() {
		return letterBody;
	}
}
