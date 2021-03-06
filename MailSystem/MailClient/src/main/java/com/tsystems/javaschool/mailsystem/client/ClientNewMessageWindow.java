package com.tsystems.javaschool.mailsystem.client;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.entities.MessageEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

import java.awt.Font;

@SuppressWarnings("serial")
public class ClientNewMessageWindow extends JDialog {
	
	private ClientMainWindow mainWindow;
	private ClientNewMessageWindow newMessageWindow;
	private JTextField receiverTextField;
	private JTextField themeTextField;
	private JTextArea messageBodyTextArea;

	/**
	 * Create the dialog.
	 */
	public ClientNewMessageWindow(ClientMainWindow clientMainWindow) {
		newMessageWindow = this;
		mainWindow = clientMainWindow;
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Create a new message");
		setBounds(100, 100, 600, 600);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(createMessageDataPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				cancelActionPerformed();
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
			public void actionPerformed(ActionEvent e) {
				sendMessageActionPerformed();
			}
		});
		
		JButton saveMessageButton = new JButton("Save message");
		saveMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		saveMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveMessageActionPerformed();
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelActionPerformed();
			}
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(sendMessageButton);
		buttonsPanel.add(saveMessageButton);
		buttonsPanel.add(cancelButton);
		
		return buttonsPanel;
	}
	
	private void sendMessageActionPerformed() {
		
		MailBoxEntity senderMailBox = mainWindow.getUserMailBox();
		ServerResponse response = mainWindow.getClientProcess().getClientMessageService()
				.createMessage(senderMailBox, receiverTextField.getText(), themeTextField.getText()
						, messageBodyTextArea.getText());
		
		if (response.isError()) {
			JOptionPane.showMessageDialog(newMessageWindow,response.getExceptionMessage(),
					"Error",JOptionPane.ERROR_MESSAGE);
			mainWindow.getClientProcess().stopClient();
			mainWindow.dispose();
			dispose();
		} else {
			if (response.isException()) {
				JOptionPane.showMessageDialog(newMessageWindow,response.getExceptionMessage(),
						"Error",JOptionPane.ERROR_MESSAGE);
			} else {
				response = mainWindow.getClientProcess().getClientMessageService().sendMessage((MessageEntity) response.getResult());
				
				if (response.isError()) {
					JOptionPane.showMessageDialog(newMessageWindow,response.getExceptionMessage(),
							"Error",JOptionPane.ERROR_MESSAGE);
					mainWindow.getClientProcess().stopClient();
					mainWindow.dispose();
					dispose();
				} else {
					if (response.isException()) {
						JOptionPane.showMessageDialog(newMessageWindow,response.getExceptionMessage(),
								"Error",JOptionPane.ERROR_MESSAGE);
					} else {
						dispose();
						JOptionPane.showMessageDialog(newMessageWindow,response.getResult(),
								"Information",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	}
	
	private void saveMessageActionPerformed() {
		
		MailBoxEntity senderMailBox = mainWindow.getUserMailBox();
		ServerResponse response = mainWindow.getClientProcess().getClientMessageService()
				.createMessage(senderMailBox, receiverTextField.getText(), themeTextField.getText()
						, messageBodyTextArea.getText());
		
		if (response.isError()) {
			JOptionPane.showMessageDialog(newMessageWindow,response.getExceptionMessage(),
					"Error",JOptionPane.ERROR_MESSAGE);
			mainWindow.getClientProcess().stopClient();
			mainWindow.dispose();
			dispose();
		} else {
			if (response.isException()) {
				JOptionPane.showMessageDialog(newMessageWindow,response.getExceptionMessage(),
						"Information",JOptionPane.INFORMATION_MESSAGE);
			}
			
			response = mainWindow.getClientProcess().getClientMessageService().saveMessage((MessageEntity) response.getResult());
			
			if (response.isError()) {
				JOptionPane.showMessageDialog(newMessageWindow,response.getExceptionMessage(),
						"Error",JOptionPane.ERROR_MESSAGE);
				mainWindow.getClientProcess().stopClient();
				mainWindow.dispose();
				dispose();
			} else {
				if (response.isException()) {
					JOptionPane.showMessageDialog(newMessageWindow,response.getExceptionMessage(),
							"Error",JOptionPane.ERROR_MESSAGE);
				} else {
					dispose();
					JOptionPane.showMessageDialog(mainWindow,response.getResult(),
							"Information",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	private void cancelActionPerformed() {
		int i = JOptionPane.showConfirmDialog(newMessageWindow,"Save the message?",
			    "Question",JOptionPane.YES_NO_CANCEL_OPTION);
		switch (i) {
		case JOptionPane.YES_OPTION:
			saveMessageActionPerformed();
			break;
		case JOptionPane.NO_OPTION:
			dispose();
			break;
		case JOptionPane.CANCEL_OPTION:
			break;
		}
	}
}
