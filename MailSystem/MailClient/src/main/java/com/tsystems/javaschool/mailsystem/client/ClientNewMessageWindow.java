package com.tsystems.javaschool.mailsystem.client;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.EmptyBorder;

import com.tsystems.javaschool.mailsystem.entities.MessageEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

import java.awt.Font;
import java.awt.Color;

public class ClientNewMessageWindow extends JDialog {
	
	private ClientMainWindow mainWindow;
	private JTextField receiverTextField;
	private JTextField themeTextField;
	private JTextArea messageBodyTextArea;

	/**
	 * Create the dialog.
	 */
	public ClientNewMessageWindow(ClientMainWindow clientMainWindow) {
		
		mainWindow = clientMainWindow;
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Create a new message");
		setBounds(100, 100, 600, 600);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(createMessageDataPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				
			}
		});
	}
	
	private  JPanel createMessageDataPanel() {
		
		JPanel messageDataPanel = new JPanel();
		messageDataPanel.setLayout(new BorderLayout(0, 5));
		messageDataPanel.add(createReceiverAndThemePanel(), BorderLayout.NORTH);
		messageDataPanel.add(createMessageBodyPanel(), BorderLayout.CENTER);
		
		return messageDataPanel;
	}

	private JPanel createReceiverAndThemePanel() {
		
		JPanel receiverAndThemePanel = new JPanel();
		receiverAndThemePanel.setLayout(new GridLayout(0, 1, 0, 5));
		receiverAndThemePanel.add(createReceiverPanel());
		receiverAndThemePanel.add(createThemePanel());
		
		return receiverAndThemePanel;
	}

	private JPanel createReceiverPanel() {
		
		receiverTextField = new JTextField();
		receiverTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		receiverTextField.setColumns(10);
		
		JLabel receiverLabel = new JLabel("Receiver:");
		receiverLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));		
		receiverLabel.setHorizontalAlignment(SwingConstants.CENTER);
		receiverLabel.setPreferredSize(new Dimension(100,30));
		receiverLabel.setLabelFor(receiverTextField);

		JPanel receiverPanel = new JPanel();
		receiverPanel.setLayout(new BorderLayout(5, 0));
		receiverPanel.add(receiverLabel, BorderLayout.WEST);
		receiverPanel.add(receiverTextField, BorderLayout.CENTER);
		
		return receiverPanel;
	}
	
	private JPanel createThemePanel() {
		
		themeTextField = new JTextField();
		themeTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		themeTextField.setColumns(10);
		
		JLabel themeLabel = new JLabel("Theme:");
		themeLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		themeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		themeLabel.setPreferredSize(new Dimension(100,30));
		themeLabel.setLabelFor(themeTextField);
		
		JPanel themePanel = new JPanel();
		themePanel.setLayout(new BorderLayout(5, 0));
		themePanel.add(themeLabel, BorderLayout.WEST);
		themePanel.add(themeTextField, BorderLayout.CENTER);
		
		return themePanel;
	}	
	
	private JPanel createMessageBodyPanel() {

		messageBodyTextArea = new JTextArea();
		messageBodyTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		messageBodyTextArea.setLineWrap(true);
		JScrollPane messageBodyScrollPane = new JScrollPane(messageBodyTextArea);		
		
		JLabel messageBodyLabel = new JLabel("Message:");
		messageBodyLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		messageBodyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageBodyLabel.setPreferredSize(new Dimension(100,30));
		messageBodyLabel.setLabelFor(messageBodyScrollPane);
		
		JPanel messageBodyPanel = new JPanel();
		messageBodyPanel.setLayout(new BorderLayout(5, 0));
		messageBodyPanel.add(messageBodyLabel, BorderLayout.WEST);
		messageBodyPanel.add(messageBodyScrollPane, BorderLayout.CENTER);
		
		return messageBodyPanel;
	}
	
	private JPanel createButtonsPanel() {
		
		JButton sendMessageButton = new JButton("Send message");
		sendMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		sendMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				ServerResponse response = clientProcess.getClientMessageService().createMessage
//						(clientProcess.getUserMailBox(),receiverTextField.getText() ,
//								themeTextField.getText(),messageBodyTextArea.getText());
//				if (response.isException()) {
//					response.getExceptionMessage();
//				} else {
//					clientProcess.getClientMessageService().sendMessage((MessageEntity)response.getResult());
//					dispose();
//				}
//				sendMessageActionPerformed(arg0,clientProcess);
			}
		});
		
		JButton saveMessageButton = new JButton("Save message");
		saveMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		saveMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(sendMessageButton);
		buttonsPanel.add(saveMessageButton);
		buttonsPanel.add(cancelButton);
		
		return buttonsPanel;
	}
	
	private void sendMessageActionPerformed(ActionEvent arg0, ClientProcess clientProcess) {
		ServerResponse response = clientProcess.getClientMessageService().createMessage
				(clientProcess.getUserMailBox(),this.receiverTextField.getText() ,
						this.themeTextField.getText(),this.messageBodyTextArea.getText());
		if (response.isException()) {
			response.getExceptionMessage();
		} else {
			clientProcess.getClientMessageService().sendMessage((MessageEntity)response.getResult());
			dispose();
		}
	}
}
