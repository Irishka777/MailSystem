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
import javax.swing.tree.DefaultMutableTreeNode;

import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class ClientLoginWindow extends JDialog {
	
	private final ClientMainWindow mainWindow;
	private JDialog loginWindow;
	private JTextField addressTextField;
	private JPasswordField passwordField;

	/**
	 * Create the dialog.
	 */
	public ClientLoginWindow(ClientMainWindow clientMainWindow) {
		
		mainWindow = clientMainWindow;
		loginWindow = this;
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Login");
		setBounds(100, 100, 550, 200);

		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(createAddressAndPasswordPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				mainWindow.getClientProcess().stopClient();
				mainWindow.dispose();
			}
		});
	}
	
	private JPanel createAddressAndPasswordPanel() {
		
		JPanel addressAndPasswordPanel = new JPanel();
		addressAndPasswordPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		addressAndPasswordPanel.setLayout(new GridLayout(0, 1, 0, 20));
		
		addressAndPasswordPanel.add(createAddressPanel());
		addressAndPasswordPanel.add(createPasswordPanel());
		
		return addressAndPasswordPanel;	
	}
	
	private JPanel createAddressPanel() {
		addressTextField = new JTextField();
		addressTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		addressTextField.setColumns(10);
		
		JLabel addressLabel = new JLabel("Email address:");
		addressLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addressLabel.setPreferredSize(new Dimension(100,20));
		addressLabel.setLabelFor(addressTextField);
		
		JPanel addressPanel = new JPanel();
		addressPanel.setLayout(new BorderLayout(5, 0));
		addressPanel.add(addressLabel, BorderLayout.WEST);
		addressPanel.add(addressTextField, BorderLayout.CENTER);
		
		return addressPanel;
	}
	
	private JPanel createPasswordPanel() {
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setPreferredSize(new Dimension(100,20));
		passwordLabel.setLabelFor(passwordField);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new BorderLayout(5, 0));
		passwordPanel.add(passwordLabel, BorderLayout.WEST);
		passwordPanel.add(passwordField, BorderLayout.CENTER);
		
		return passwordPanel;
	}
	
	private JPanel createButtonsPanel() {
		
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
		loginButton.setToolTipText("Press this button after entering email address and password to enter mail client program");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loginButtonActionPerformed(arg0);
			}
		});
		
		JButton registrationButton = new JButton("Registration");
		registrationButton.setFont(new Font("Arial", Font.PLAIN, 16));
		registrationButton.setEnabled(false);
		registrationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(loginButton);
		buttonsPanel.add(registrationButton);
		
		return buttonsPanel;
	}
	
	@SuppressWarnings("deprecation")
	private void loginButtonActionPerformed(ActionEvent arg0) {
		MailBoxEntity mailBox = mainWindow.getClientProcess().getClientMailBoxService()
				.createMailBox(addressTextField.getText(), passwordField.getText(), null);
		
		ServerResponse response = mainWindow.getClientProcess().getClientMailBoxService().login(mailBox);
		
		if (response.isError()) {
			JOptionPane.showMessageDialog(loginWindow,response.getExceptionMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			mainWindow.getClientProcess().stopClient();
			mainWindow.dispose();
			dispose();
		} else {
			if (response.isException()) {
				JOptionPane.showMessageDialog(loginWindow,response.getExceptionMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			} else {
				mainWindow.getFoldersTreeModel().setRoot(new DefaultMutableTreeNode(response.getResult()));
				dispose();
			}
		}
	}
}
