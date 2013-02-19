package com.tsystems.javaschool.mailsystem.client;

import javax.swing.JDialog;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientLoginWindow extends JDialog {
	private JDialog loginWindow;
	private JTextField addressTextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ClientLoginWindow dialog = new ClientLoginWindow();
//					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//					dialog.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the dialog.
	 */
	public ClientLoginWindow(final ClientProcess client) {
		loginWindow = this;
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setTitle("Login");
		setBounds(100, 100, 550, 200);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel loginPanel = new JPanel();
		getContentPane().add(loginPanel);
		loginPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel addressAndPasswordPanel = new JPanel();
		addressAndPasswordPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		loginPanel.add(addressAndPasswordPanel, BorderLayout.CENTER);
		addressAndPasswordPanel.setLayout(new GridLayout(0, 1, 0, 20));
		
		JPanel addressPanel = new JPanel();
		addressAndPasswordPanel.add(addressPanel);
		addressPanel.setLayout(new BorderLayout(5, 0));
		
		JLabel addressLabel = new JLabel("Email address:");
		addressPanel.add(addressLabel, BorderLayout.WEST);
		addressLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addressLabel.setPreferredSize(new Dimension(100,20));
		
		addressTextField = new JTextField();
		addressLabel.setLabelFor(addressTextField);
		addressPanel.add(addressTextField);
		addressTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		addressTextField.setColumns(10);
		
		JPanel passwordPannel = new JPanel();
		addressAndPasswordPanel.add(passwordPannel);
		passwordPannel.setLayout(new BorderLayout(5, 0));
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordPannel.add(passwordLabel, BorderLayout.WEST);
		passwordLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setPreferredSize(new Dimension(100,20));
		
		passwordField = new JPasswordField();
		passwordLabel.setLabelFor(passwordField);
		passwordPannel.add(passwordField);
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JPanel buttonsPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonsPanel.getLayout();
		flowLayout.setHgap(10);
		loginPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				MailBoxEntity mailBox = client.getClientMailBoxService()
						.createMailBox(addressTextField.getText(),passwordField.getText(),null);
				ServerResponse response = client.getClientMailBoxService().login(mailBox);
				if (!response.isException()) {
					client.setUserMailBox((MailBoxEntity)response.getResult());
					dispose();
				} else {
					if (!response.isError()) {
						JOptionPane.showMessageDialog(loginWindow,response.getExceptionMessage(),"Error",JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(loginWindow,response.getExceptionMessage(),"Error",JOptionPane.ERROR_MESSAGE);
						setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
						dispose();
					}
					
				}
			}
		});
		buttonsPanel.add(loginButton);
		loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JButton registrationButton = new JButton("Registration");
		registrationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		buttonsPanel.add(registrationButton);
		registrationButton.setToolTipText("Press this button after entering email address and password to enter mail client program");
		registrationButton.setFont(new Font("Arial", Font.PLAIN, 16));
		registrationButton.setEnabled(false);

	}
	
//	private void clearAndHide() {
//		addressTextField.setText(null);
//		passwordField.setText(null);
//		dispose();
//		setVisible(false);
//	}

}
