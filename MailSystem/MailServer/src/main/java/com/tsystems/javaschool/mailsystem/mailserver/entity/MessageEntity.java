//package com.tsystems.javaschool.mailsystem.mailserver.entity;
//
//import java.util.Calendar;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAO;
//import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAOImpl;
//import com.tsystems.javaschool.mailsystem.shareableObjects.Message;
//
//@Entity
//@Table(name = "Message")
//public class MessageEntity {
//	
//	@Id
//	@GeneratedValue
//	private long id;
//
//	@OneToOne
//	private MailBoxEntity sender;
//	
//	@OneToOne
//	private MailBoxEntity receiver;
//	
//	private Calendar date;
//	private String theme;
//	
//	@Column(columnDefinition = "TEXT")
//	private String messageBody;
//	
//	
//	public MessageEntity() {}
//	
//	public MessageEntity(Message message) {
//		
//		MailBoxDAO mailBoxDAO = new MailBoxDAOImpl();
//		sender = mailBoxDAO.findByEmailAddress(message.getSender());
//		receiver = mailBoxDAO.findByEmailAddress(message.getReceiver());
//		date = message.getDate();
//		theme = message.getTheme();
//		messageBody = message.getMessageBody();
//	}
//	
//	public void setId(long id) {
//		this.id = id;
//	}
//	public long getId() {
//		return id;
//	}
//	
//	public void setSender(MailBoxEntity sender) {
//		this.sender = sender;
//	}
//	public MailBoxEntity getSender() {
//		return sender;
//	}
//	
//	public void setReceiver(MailBoxEntity receiver) {
//		this.receiver = receiver;
//	}
//	public MailBoxEntity getReceiver() {
//		return receiver;
//	}
//	
//	public void setDate(Calendar currentDate) {
//		date = currentDate;
//	}
//	public Calendar getDate() {
//		return date;
//	}
//	
//	public void setTheme(String theme) {
//		this.theme = theme;
//	}
//	public String getTheme() {
//		return theme;
//	}
//	
//	public void setMessageBody(String messageBody) {
//		this.messageBody = messageBody;
//	}
//	public String getMessageBody() {
//		return messageBody;
//	}	
//}