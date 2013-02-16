//package com.tsystems.javaschool.mailsystem.mailserver.entity;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//import javax.persistence.UniqueConstraint;
//
//import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAO;
//import com.tsystems.javaschool.mailsystem.mailserver.dao.MailBoxDAOImpl;
//import com.tsystems.javaschool.mailsystem.shareableObjects.Folder;
//import com.tsystems.javaschool.mailsystem.shareableObjects.Message;
//
//@Entity
//@Table(name = "Folder",uniqueConstraints = @UniqueConstraint(columnNames = { "folderName", "emailAddress" }))
//public class FolderEntity {
//	@Id
//	@GeneratedValue
//	private long id;
//	
//	private String folderName;
//	
//	@OneToOne
//	private MailBoxEntity emailAddress;
//	
//	@OneToMany(cascade = CascadeType.ALL)
//	private List<MessageEntity> listOfMessages;
//	
//	public FolderEntity() {}
//	
//	public FolderEntity(Folder folder) {
//		MailBoxDAO mailBoxDAO = new MailBoxDAOImpl();
//		folderName = folder.getFolderName();
//		for(Message m : folder.getListOfMessages()) {
//			listOfMessages.add(new MessageEntity(m));
//		}	
//		emailAddress = mailBoxDAO.findByEmailAddress(folder.getEmailAddress());
//	}
//	
//	public void setId(long id) {
//		this.id = id;
//	}
//	public long getId() {
//		return id;
//	}
//	
//	public void setFolderName(String folderName) {
//		this.folderName = folderName;
//	}
//	public String getFolderName() {
//		return folderName;
//	}
//	
//	public void setEmailAddress(MailBoxEntity emailAddress) {
//		this.emailAddress = emailAddress;
//	}
//	public MailBoxEntity getEmailAddress() {
//		return emailAddress;
//	}
//	
//	public void setListOfMessages(List<MessageEntity> listOfMessages) {
//		this.listOfMessages = listOfMessages;
//	}
//	public List<MessageEntity> getListOfMessages() {
//		return listOfMessages;
//	}
//	
//}
