package shareableObjects;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Email implements Serializable {
	private String emailAddress;
	private Date date;
	private User user;
	
	public void setEmail(String userEmail) {
		emailAddress = userEmail;
	}
	public String getEmail() {
		return emailAddress;
	}
	
	public void setDate(Date currentDate) {
		date = currentDate;
	}
	public Date getDate() {
		return date;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
}
