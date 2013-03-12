package com.tsystems.javaschool.mailsystem.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

@SuppressWarnings("serial")
public class ClientPersonalDataWindow extends JDialog {
	
	private ClientMainWindow mainWindow;
	private JTextField nameTextField;
	private JTextField surnameTextField;
	private JSpinner dateOfBirthSpinner;
	private JFormattedTextField phoneNumberTextField;
	
public ClientPersonalDataWindow(ClientMainWindow clientMainWindow) {
		
		mainWindow = clientMainWindow;
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Personal data");
		setBounds(100, 100, 450, 250);
		
		getContentPane().setLayout(new BorderLayout(0, 5));
		getContentPane().add(createDataPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel createDataPanel() {

		JPanel dataPanel = new JPanel();
		dataPanel.setBorder(new EmptyBorder(5, 5, 5, 5));	
		dataPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		dataPanel.add(createNamePanel());
		dataPanel.add(createSurnamePanel());
		dataPanel.add(createDateOfBirthPanel());
		dataPanel.add(createPhoneNumberPanel());
		
		return dataPanel;
	}
	
	private JPanel createNamePanel() {
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		nameTextField.setText(mainWindow.getUserMailBox().getUser().getName());
		
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
		surnameTextField.setText(mainWindow.getUserMailBox().getUser().getLastName());
		
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
		dateOfBirthSpinner.setModel(new SpinnerDateModel(mainWindow.getUserMailBox().getUser().getDate(),
				new Date(-2209003200000L), Calendar.getInstance().getTime(), Calendar.DAY_OF_YEAR));
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
		phoneNumberTextField.setText(mainWindow.getUserMailBox().getUser().getPhoneNumber());
		
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
		JButton saveChangesButton = new JButton("Save changes");
		saveChangesButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		saveChangesButton.setEnabled(false);
		saveChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveChangesActionPerformed();
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
		buttonsPanel.add(saveChangesButton);
		buttonsPanel.add(cancelButton);
		
		return buttonsPanel;	
	}
	
	private void saveChangesActionPerformed() {
		if (!fieldsValidation()) {
			return;
		}
		mainWindow.getUserMailBox().getUser().setName(nameTextField.getText());
		mainWindow.getUserMailBox().getUser().setLastName(surnameTextField.getText());
		mainWindow.getUserMailBox().getUser().setDate((Date) dateOfBirthSpinner.getValue());
		mainWindow.getUserMailBox().getUser().setPhoneNumber(phoneNumberTextField.getText());
		ServerResponse response = mainWindow.getClientProcess().getClientMailBoxService()
				.updateMailBoxData(mainWindow.getUserMailBox());;
		
		if (response.isError()) {
			JOptionPane.showMessageDialog(this,response.getExceptionMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			mainWindow.getClientProcess().stopClient();
			mainWindow.dispose();
			dispose();
		} else {
			if (response.isException()) {
				JOptionPane.showMessageDialog(this,response.getExceptionMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			} else {
				mainWindow.getFoldersTreeModel().setRoot(new DefaultMutableTreeNode(response.getResult()));
				mainWindow.setFoldersInFoldersTree();
				dispose();
			}
		}
	}
	
	private boolean fieldsValidation() {
		if ((nameTextField.getText() == null) || (nameTextField.getText().trim().length() == 0)) {
			JOptionPane.showMessageDialog(this, "You should specify a name",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		nameTextField.setText(nameTextField.getText().trim());
		if ((surnameTextField.getText() == null) || (surnameTextField.getText().trim().length() == 0)) {
			JOptionPane.showMessageDialog(this, "You should specify a surname",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		surnameTextField.setText(surnameTextField.getText().trim());
		if ((phoneNumberTextField.getText() == null) 
				|| (phoneNumberTextField.getText().trim().length() == 0)) {
			JOptionPane.showMessageDialog(this, "You should specify a phone number",
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
