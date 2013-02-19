package com.tsystems.javaschool.mailsystem.client;

import java.awt.Dimension;
import java.awt.EventQueue;

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
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.EmptyBorder;

import com.tsystems.javaschool.mailsystem.entities.MessageEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

public class ClientNewMessageWindow extends JDialog {
	private JTextField receiverTextField;
	private JTextField themeTextField;
	private JTextArea messageBodyTextArea;

	/**
	 * Create the dialog.
	 */
	public ClientNewMessageWindow(final ClientProcess clientProcess) {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
			}
		});
		setTitle("Create a new message");
		setBounds(100, 100, 600, 600);
		
		JPanel messagePanel = new JPanel();
		getContentPane().add(messagePanel, BorderLayout.CENTER);
		messagePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel messageDataPanel = new JPanel();
		messageDataPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		messagePanel.add(messageDataPanel, BorderLayout.CENTER);
		messageDataPanel.setLayout(new BorderLayout(0, 5));
		
		JPanel messageReseiverAndThemePanel = new JPanel();
		messageDataPanel.add(messageReseiverAndThemePanel, BorderLayout.NORTH);
		messageReseiverAndThemePanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		JPanel receiverPanel = new JPanel();
		messageReseiverAndThemePanel.add(receiverPanel);
		receiverPanel.setLayout(new BorderLayout(5, 0));
		
		JLabel receiverLabel = new JLabel("Receiver:");
		receiverLabel.setForeground(Color.BLACK);
		receiverLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		receiverPanel.add(receiverLabel, BorderLayout.WEST);
		receiverLabel.setHorizontalAlignment(SwingConstants.CENTER);
		receiverLabel.setPreferredSize(new Dimension(100,30));
		
		receiverTextField = new JTextField();
		receiverTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		receiverLabel.setLabelFor(receiverTextField);
		receiverPanel.add(receiverTextField);
		receiverTextField.setColumns(10);
		
		JPanel themePanel = new JPanel();
		messageReseiverAndThemePanel.add(themePanel);
		themePanel.setLayout(new BorderLayout(5, 0));
		
		JLabel themeLabel = new JLabel("Theme:");
		themeLabel.setForeground(Color.BLACK);
		themeLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		themePanel.add(themeLabel, BorderLayout.WEST);
		themeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		themeLabel.setPreferredSize(new Dimension(100,30));
		
		themeTextField = new JTextField();
		themeTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		themeLabel.setLabelFor(themeTextField);
		themePanel.add(themeTextField);
		themeTextField.setColumns(10);
		
		JPanel messageBodyPanel = new JPanel();
		messageDataPanel.add(messageBodyPanel);
		messageBodyPanel.setLayout(new BorderLayout(5, 0));
		
		JLabel messageLabel = new JLabel("Message:");
		messageLabel.setForeground(Color.BLACK);
		messageLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		messageBodyPanel.add(messageLabel, BorderLayout.WEST);
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setPreferredSize(new Dimension(100,30));
		
		JScrollPane messageBodyScrollPane = new JScrollPane();
		messageLabel.setLabelFor(messageBodyScrollPane);
		messageBodyPanel.add(messageBodyScrollPane);
		
		messageBodyTextArea = new JTextArea();
		messageBodyTextArea.setLineWrap(true);
		messageBodyTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		messageBodyScrollPane.setViewportView(messageBodyTextArea);
		
		JPanel messageButtonsPanel = new JPanel();
		messagePanel.add(messageButtonsPanel, BorderLayout.SOUTH);
		
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
		messageButtonsPanel.add(sendMessageButton);
		
		JButton saveMessageButton = new JButton("Save message");
		saveMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		saveMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		messageButtonsPanel.add(saveMessageButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		messageButtonsPanel.add(cancelButton);
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
