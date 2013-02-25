package com.tsystems.javaschool.mailsystem.client;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Calendar;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.entities.UserEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ClientRegistrationWindow extends JDialog {
	
	private ClientMainWindow mainWindow;
	private ClientLoginWindow loginWindow;
//	private ClientRegistrationWindow registrationWindow;
	private JTextField emailAddressTextField;
	private JPasswordField passwordRepetionField;
	private JPasswordField passwordField;
	private JTextField nameTextField;
	private JTextField surnameTextField;
	private JSpinner dateOfBirthSpinner;
	private JFormattedTextField phoneNumberTextField;

	/**
	 * Create the dialog.
	 */
	public ClientRegistrationWindow(ClientMainWindow clientMainWindow, ClientLoginWindow clientLoginWindow) {
		
//		registrationWindow = this;
		mainWindow = clientMainWindow;
		loginWindow = clientLoginWindow;
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Registration");
		setBounds(100, 100, 450, 300);
		
		getContentPane().setLayout(new BorderLayout(0, 5));
		getContentPane().add(createDataPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel createDataPanel() {

		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new EmptyBorder(5, 5, 5, 5));	
		dataPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		dataPanel.add(createEmailAddressPanel());
		dataPanel.add(createPasswordPanel());
		dataPanel.add(createRepetionPasswordPanel());
		dataPanel.add(createNamePanel());
		dataPanel.add(createSurnamePanel());
		dataPanel.add(createDateOfBirthPanel());
		dataPanel.add(createPhoneNumberPanel());
		
		return dataPanel;
	}
	
	private JPanel createEmailAddressPanel() {
		
		emailAddressTextField = new JTextField();
		emailAddressTextField.setFont(new Font("Arial", Font.PLAIN, 14));
			
		JLabel addressLabel = new JLabel("Email address:");
		addressLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		addressLabel.setPreferredSize(new Dimension(120,30));		
		addressLabel.setLabelFor(emailAddressTextField);

		JPanel addressPanel = new JPanel();
		addressPanel.setLayout(new BorderLayout(5, 0));
		addressPanel.add(addressLabel, BorderLayout.WEST);
		addressPanel.add(emailAddressTextField, BorderLayout.CENTER);
		return addressPanel;
	}
	
	private JPanel createPasswordPanel() {
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		passwordLabel.setPreferredSize(new Dimension(120,30));
		passwordLabel.setLabelFor(passwordField);
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setLayout(new BorderLayout(5, 0));
		passwordPanel.add(passwordLabel, BorderLayout.WEST);
		passwordPanel.add(passwordField, BorderLayout.CENTER);
		return passwordPanel;		
	}
	
	private JPanel createRepetionPasswordPanel() {
		passwordRepetionField = new JPasswordField();
		passwordRepetionField.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JLabel passwordRepetionLabel = new JLabel("Password repetion:");
		passwordRepetionLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		passwordRepetionLabel.setPreferredSize(new Dimension(120,30));
		passwordRepetionLabel.setLabelFor(passwordRepetionField);
		
		JPanel passwordRepetionPanel = new JPanel();
		passwordRepetionPanel.setLayout(new BorderLayout(5, 0));
		passwordRepetionPanel.add(passwordRepetionLabel, BorderLayout.WEST);
		passwordRepetionPanel.add(passwordRepetionField, BorderLayout.CENTER);
		return passwordRepetionPanel;
	}
	
	private JPanel createNamePanel() {
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		nameTextField.setColumns(10);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		nameLabel.setPreferredSize(new Dimension(120,30));
		nameLabel.setLabelFor(nameTextField);
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout(5, 0));
		namePanel.add(nameLabel, BorderLayout.WEST);
		namePanel.add(nameTextField, BorderLayout.CENTER);
		return namePanel;
	}
	
	private JPanel createSurnamePanel() {
		surnameTextField = new JTextField();
		surnameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		surnameTextField.setColumns(10);
		
		JLabel surnameLabel = new JLabel("Surname:");
		surnameLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		surnameLabel.setPreferredSize(new Dimension(120,30));
		surnameLabel.setLabelFor(surnameTextField);
		
		JPanel surnamePanel = new JPanel();
		surnamePanel.setLayout(new BorderLayout(5, 0));
		surnamePanel.add(surnameLabel, BorderLayout.WEST);
		surnamePanel.add(surnameTextField, BorderLayout.CENTER);
		return surnamePanel;
	}
	
	private JPanel createDateOfBirthPanel() {
		dateOfBirthSpinner = new JSpinner();
		dateOfBirthSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
		dateOfBirthSpinner.setModel(new SpinnerDateModel(Calendar.getInstance().getTime()
				, new Date(-2209003200000L), Calendar.getInstance().getTime(), Calendar.DAY_OF_YEAR));
		dateOfBirthSpinner.setEditor(new JSpinner.DateEditor(dateOfBirthSpinner, "dd.MM.yyyy"));
		
		JLabel dateOfBirthLabel = new JLabel("Date of birth:");
		dateOfBirthLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		dateOfBirthLabel.setPreferredSize(new Dimension(120,30));
		dateOfBirthLabel.setLabelFor(dateOfBirthSpinner);
		
		JPanel dateOfBirthPanel = new JPanel();
		dateOfBirthPanel.setLayout(new BorderLayout(5, 0));
		dateOfBirthPanel.add(dateOfBirthLabel, BorderLayout.WEST);
		dateOfBirthPanel.add(dateOfBirthSpinner, BorderLayout.CENTER);
		return dateOfBirthPanel;
	}
	
	private JPanel createPhoneNumberPanel() {
		
		NumberFormat phoneNumberFormat = NumberFormat.getNumberInstance();
		phoneNumberFormat.setParseIntegerOnly(true);
		phoneNumberTextField = new JFormattedTextField(phoneNumberFormat);
		phoneNumberTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JLabel phoneNumberLabel = new JLabel("Phone number:");
		phoneNumberLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		phoneNumberLabel.setPreferredSize(new Dimension(120,30));
		phoneNumberLabel.setLabelFor(phoneNumberTextField);
		
		JPanel phoneNumberPanel = new JPanel();
		phoneNumberPanel.setLayout(new BorderLayout(5, 0));
		phoneNumberPanel.add(phoneNumberLabel, BorderLayout.WEST);
		phoneNumberPanel.add(phoneNumberTextField, BorderLayout.CENTER);
		return phoneNumberPanel;
	}
	
	private JPanel createButtonsPanel() {
		JButton registrationButton = new JButton("Registration");
		registrationButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		registrationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrationActionPerformed();
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(registrationButton);
		buttonsPanel.add(cancelButton);
		
		return buttonsPanel;	
	}
	
	@SuppressWarnings("deprecation")
	private void registrationActionPerformed() {
		if (!fieldsValidation()) {
			return;
		}
		MailBoxEntity mailBox = mainWindow.getClientProcess().getClientMailBoxService()
		.createMailBox(emailAddressTextField.getText(), passwordField.getText(), 
				new UserEntity(nameTextField.getText(), surnameTextField.getText(),
						(Date)dateOfBirthSpinner.getValue(), phoneNumberTextField.getText()));

		ServerResponse response = mainWindow.getClientProcess().getClientMailBoxService().registration(mailBox);
		
		if (response.isError()) {
			JOptionPane.showMessageDialog(loginWindow,response.getExceptionMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			mainWindow.getClientProcess().stopClient();
			mainWindow.dispose();
			loginWindow.dispose();
			dispose();
		} else {
			if (response.isException()) {
				JOptionPane.showMessageDialog(loginWindow,response.getExceptionMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			} else {
				loginWindow.setEmailAddressTextField(emailAddressTextField.getText());
				dispose();
				JOptionPane.showMessageDialog(loginWindow,response.getResult(),
						"Information",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private boolean fieldsValidation() {
		if ((!emailAddressValidation()) || (!passwordValidation())) {
			return false;
		}
		if ((nameTextField.getText() == null) || (nameTextField.getText().trim().length() == 0)) {
			JOptionPane.showMessageDialog(loginWindow, "You should specify a name",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		nameTextField.setText(nameTextField.getText().trim());
		if ((surnameTextField.getText() == null) || (surnameTextField.getText().trim().length() == 0)) {
			JOptionPane.showMessageDialog(loginWindow, "You should specify a surname",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		surnameTextField.setText(surnameTextField.getText().trim());
		if ((phoneNumberTextField.getText() == null) 
				|| (phoneNumberTextField.getText().trim().length() == 0)) {
			JOptionPane.showMessageDialog(loginWindow, "You should specify a phone number",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	private boolean emailAddressValidation() {
		if ((emailAddressTextField.getText() == null) || (emailAddressTextField.getText().trim().length() == 0)) {
			JOptionPane.showMessageDialog(loginWindow, "Email address field could not be empty",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		emailAddressTextField.setText(emailAddressTextField.getText().trim());
		if (emailAddressTextField.getText().contains(" ")) {
			JOptionPane.showMessageDialog(loginWindow, "Email address should not contain whitespace characters",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (emailAddressTextField.getText().startsWith("@")) {
			JOptionPane.showMessageDialog(loginWindow, "Email address could not start with should \'@\' character",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if ((!emailAddressTextField.getText().contains("@")) 
				|| (emailAddressTextField.getText().indexOf("@") != emailAddressTextField.getText().lastIndexOf("@"))){
			JOptionPane.showMessageDialog(loginWindow, "Email address should contain one \'@\' character",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!emailAddressTextField.getText().endsWith(".ru")) {
			JOptionPane.showMessageDialog(loginWindow, "Email address should ends with \".ru\"",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if ((emailAddressTextField.getText().indexOf("@")+1) == (emailAddressTextField.getText().indexOf(".ru"))) {
			JOptionPane.showMessageDialog(loginWindow, "Email address should contain correct domain name",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}	
		return true;
	}
	
	private boolean passwordValidation() {
		int passwordRequiredLength = 5;
		int passwordLength = passwordField.getPassword().length;
		if (passwordLength < passwordRequiredLength) {
			JOptionPane.showMessageDialog(loginWindow,
					"You should specify a password with no less then " + passwordRequiredLength + " characters",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (passwordLength != passwordRepetionField.getPassword().length) {
			JOptionPane.showMessageDialog(loginWindow,"You should enter the same password in both password fields",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		for (int i = 0; i < passwordLength; i++) {
			if (passwordField.getPassword()[i] != passwordRepetionField.getPassword()[i]) {
				JOptionPane.showMessageDialog(loginWindow,"You should enter the same password in both password fields",
						"Error",JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
}
