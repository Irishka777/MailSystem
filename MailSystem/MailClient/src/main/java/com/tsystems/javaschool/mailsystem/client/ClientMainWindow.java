package com.tsystems.javaschool.mailsystem.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.entities.MessageEntity;
import com.tsystems.javaschool.mailsystem.models.MessagesTableModel;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Font;
import java.util.List;

@SuppressWarnings("serial")
public class ClientMainWindow extends JFrame{

	private ClientProcess clientProcess;
	
	private ClientMainWindow mainWindow;
	private JTree foldersTree;
	private JTable messagesTable;
	JTextArea messageBodyTextArea;
	
	public DefaultTreeModel getFoldersTreeModel() {
		return (DefaultTreeModel) foldersTree.getModel();
	}
	
	public MailBoxEntity getUserMailBox() {
		return (MailBoxEntity) ((DefaultMutableTreeNode) getFoldersTreeModel().getRoot()).getUserObject();
	}
	
	public MessagesTableModel getMessagesTableModel() {
		return (MessagesTableModel) messagesTable.getModel();
	}
	
	private ClientLoginWindow loginWindow;
	private ClientNewMessageWindow newMessageWindow;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientMainWindow window = new ClientMainWindow();
					if (window.isDisplayable()) {
						window.setLocationRelativeTo(null);  // the window is centered on screen
						window.mainWindow.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientMainWindow() {
		clientProcess = new ClientProcess();
		if (!clientProcess.startClient()) {
			JOptionPane.showMessageDialog(mainWindow,"Cannot get connection to server",
					"Error",JOptionPane.ERROR_MESSAGE);
		} else {
			initialize();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		mainWindow = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Mail client");
		
		setContentPane(createFoldersAndMessagesSplitPane());
		pack();
		setMinimumSize(getPreferredSize());
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {			
				loginWindow = new ClientLoginWindow(mainWindow);
				loginWindow.setLocationRelativeTo(mainWindow); // the window is centered on screen
				loginWindow.setVisible(true);
			}
			public void windowClosing(WindowEvent arg0) {
				clientProcess.stopClient();
			}
		});
	}
	
	private JSplitPane createFoldersAndMessagesSplitPane() {
		JSplitPane foldersAndMessagesSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,createFoldersPanel(),
				createMessageSplitPane());
		foldersAndMessagesSplitPane.setResizeWeight(0.5);
		foldersAndMessagesSplitPane.setOneTouchExpandable(true);
		return foldersAndMessagesSplitPane;
	}
	
	private JPanel createFoldersPanel() {
		
		JPanel foldersPanel = new JPanel();
		foldersPanel.setLayout(new BorderLayout(0, 0));
		foldersPanel.add(createFoldersTreeScrollPane(), BorderLayout.CENTER);		
		foldersPanel.add(createFolderButtonsPanel(), BorderLayout.SOUTH);
		
		return foldersPanel;
	}
	
	private JScrollPane createFoldersTreeScrollPane() {	
	
		// creates foldersTree
		foldersTree = new JTree(new DefaultTreeModel(null, true));
		foldersTree.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		foldersTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		// sets folder icon for list nodes
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(renderer.getDefaultClosedIcon());
		foldersTree.setCellRenderer(renderer);
		
		// adds TreeSelectionListener
		foldersTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent();
				
				if (node == null) {
					//Nothing is selected.
					return;
				}
				if (node.isRoot()) {
					setFoldersInFoldersTree();
					return;
				}

				if (node.isLeaf()) {
					showFolderContent(node);
					return;
				}
			}
		});
		
		return new JScrollPane(foldersTree);
	}
	
	private JPanel createFolderButtonsPanel() {
		
		JButton createFolderButton = new JButton("Create folder");
		createFolderButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		createFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFolderActionPerformed();
			}
		});
		
		JButton renameFolderButton = new JButton("Rename folder");
		renameFolderButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		renameFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				renameFolderActionPerformed();
			}
		});
		
		JButton deleteFolderButton = new JButton("Delete folder");
		deleteFolderButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		deleteFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteFolderActionPerformed();
			}
		});
		
		JPanel folderButtonsPanel = new JPanel();
		folderButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		folderButtonsPanel.add(createFolderButton);
		folderButtonsPanel.add(renameFolderButton);
		folderButtonsPanel.add(deleteFolderButton);
		
		return folderButtonsPanel;
	}

	private JSplitPane createMessageSplitPane() {
	
		JSplitPane messagesSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				createMessagesPanel(),createMessageBodyScrollPane());
		messagesSplitPane.setResizeWeight(0.5);
		messagesSplitPane.setDividerLocation(250);
		
		return messagesSplitPane;
	}
	
	private JPanel createMessagesPanel() {	
		// creates messages table and sets it into scroll pane
		messagesTable = new JTable(new MessagesTableModel());
		messagesTable.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		messagesTable.setShowVerticalLines(false);
		messagesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		messagesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (messagesTable.getSelectedRow() == -1) {
					return;
				}
				messageBodyTextArea.setText(((MessagesTableModel)messagesTable.getModel())
						.getMessageBody(messagesTable.getSelectedRow()));
			}
		});
		
		JScrollPane messagesScrollPane = new JScrollPane(messagesTable);
		
		JButton createMessageButton = new JButton("Create message");
		createMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		createMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newMessageWindow = new ClientNewMessageWindow(mainWindow);
				newMessageWindow.setLocationRelativeTo(mainWindow);
				newMessageWindow.setVisible(true);
				if ((DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent() != null) {
					showFolderContent((DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent());
				}	
			}
		});
		
		JButton moveMessageToAnotherFolderButton = new JButton("Move message to another folder");
		moveMessageToAnotherFolderButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		moveMessageToAnotherFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {;
				moveMessageToAnotherFolderActionPerformed();
			}
		});
		
		JButton deleteMessageButton = new JButton("Delete message");
		deleteMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		deleteMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMessageActionPerformed();
			}
		});
		
		JPanel messageButtonsPanel = new JPanel();		
		messageButtonsPanel.add(createMessageButton);
		messageButtonsPanel.add(moveMessageToAnotherFolderButton);
		messageButtonsPanel.add(deleteMessageButton);
		
		JPanel messagesPanel = new JPanel();
		messagesPanel.setLayout(new BorderLayout(0, 0));
		messagesPanel.add(messagesScrollPane, BorderLayout.CENTER);
		messagesPanel.add(messageButtonsPanel, BorderLayout.SOUTH);
		
		return messagesPanel;
	}
	
	private JScrollPane createMessageBodyScrollPane() {	
		messageBodyTextArea = new JTextArea();
		messageBodyTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		messageBodyTextArea.setEditable(false);
		messageBodyTextArea.setLineWrap(true);
		
		return new JScrollPane(messageBodyTextArea);
	}
	
	@SuppressWarnings("unchecked")
	public void setFoldersInFoldersTree() {
		
		ServerResponse response = clientProcess.getClientFolderService().findFoldersForMailBox(getUserMailBox());
		
		if (response.isError()) {
			JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
					"Error",JOptionPane.ERROR_MESSAGE);
			getClientProcess().stopClient();
			dispose();
			return;
		}
		
		if (response.isException()) {
			JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
					"Error",JOptionPane.ERROR_MESSAGE);
		} else {
			List<FolderEntity> foldersList = (List<FolderEntity>) response.getResult();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)getFoldersTreeModel().getRoot();
			
			root.removeAllChildren();
			messageBodyTextArea.setText(null);
			getMessagesTableModel().setListOfMessages(null);
			getMessagesTableModel().fireTableDataChanged();
			
			for (FolderEntity folder:foldersList) {
				root.add(new DefaultMutableTreeNode(folder,false));
			}
			getFoldersTreeModel().reload();
		}
	}
	
	public boolean updateFolder(DefaultMutableTreeNode node) {
		ServerResponse response = clientProcess.getClientFolderService().getFolder((FolderEntity) node.getUserObject());
		
		if (response.isError()) {
			JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
					"Error",JOptionPane.ERROR_MESSAGE);
			getClientProcess().stopClient();
			dispose();
			return false;
		}
		
		if (response.isException()) {
			JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			if (response.getResult() == null) {
				setFoldersInFoldersTree();
				return false;
			}
			node.setUserObject(response.getResult());
			return true;
		}
	}
	
	private void createFolderActionPerformed() {
		String newFolderName = ((String)JOptionPane.showInputDialog(mainWindow, "Enter a name for the new folder",
				"Creat a new folder",JOptionPane.PLAIN_MESSAGE));
		if (newFolderName == null) {
			return;
		}
		newFolderName = newFolderName.trim();
		if (newFolderName.equals("")) {
			JOptionPane.showMessageDialog(mainWindow,"Folder without a name can not be created",
					"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		setFoldersInFoldersTree();
		Object root = getFoldersTreeModel().getRoot();
		int foldersCount = getFoldersTreeModel().getChildCount(root);
		for (int index = 0; index < foldersCount; index++) {
			if (getFoldersTreeModel().getChild(root, index).toString().toLowerCase().equals(newFolderName.toLowerCase())) {
				JOptionPane.showMessageDialog(mainWindow,"Folder with such a name already exists",
						"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		ServerResponse response = getClientProcess().getClientFolderService()
				.createFolder(new FolderEntity(newFolderName,getUserMailBox()));
		if (response.isError()) {
			JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
					"Error",JOptionPane.ERROR_MESSAGE);
			getClientProcess().stopClient();
			dispose();
		} else {
			if (response.isException()) {
				JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
						"Error",JOptionPane.ERROR_MESSAGE);
			} else {
				setFoldersInFoldersTree();
				JOptionPane.showMessageDialog(mainWindow,response.getResult(),
						"Information",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private void renameFolderActionPerformed() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent();
		if ((node == null) || (node.isRoot())) {
			JOptionPane.showMessageDialog(mainWindow,"You should select a folder for renaming",
					"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if ((node.getUserObject().toString().equals("Outgoing messages")) 
				|| (node.getUserObject().toString().equals("Incoming messages")) 
				|| (node.getUserObject().toString().equals("Draft messages"))) {
			JOptionPane.showMessageDialog(mainWindow,"You can not rename \"Outgoing messages\"" +
					", \"Incoming messages\" and \"Draft messages\" folders",
					"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}

		String newFolderName = ((String)JOptionPane.showInputDialog(mainWindow,
				"Enter a new name for the folder" + node.getUserObject().toString(),
				"Renaming a folder"+node.getUserObject().toString(),JOptionPane.PLAIN_MESSAGE));
		
		if (!updateFolder((DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent())) {
			JOptionPane.showMessageDialog(mainWindow,"Current folder have been deleted",
					"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (newFolderName == null) {
			return;
		}
		newFolderName = newFolderName.trim();
		if (newFolderName.equals("")) {
			JOptionPane.showMessageDialog(mainWindow,"Folder without name can not exist",
					"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
//		setFoldersInFoldersTree();
		Object root = getFoldersTreeModel().getRoot();
		int foldersCount = getFoldersTreeModel().getChildCount(root);
		for (int index = 0; index < foldersCount; index++) {
			if (getFoldersTreeModel().getChild(root, index).toString().toLowerCase().equals(newFolderName.toLowerCase())) {
				JOptionPane.showMessageDialog(mainWindow,"Folder with such a name already exists",
						"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		((FolderEntity)node.getUserObject()).setFolderName(newFolderName);
		ServerResponse response = getClientProcess().getClientFolderService()
				.renameFolder(((FolderEntity)node.getUserObject()));
		if (response.isError()) {
			JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
					"Error",JOptionPane.ERROR_MESSAGE);
			getClientProcess().stopClient();
			dispose();
		} else {
			if (response.isException()) {
				JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
						"Error",JOptionPane.ERROR_MESSAGE);
			} else {
				setFoldersInFoldersTree();
				JOptionPane.showMessageDialog(mainWindow,response.getResult(),
						"Information",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private void deleteFolderActionPerformed() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent();
		if ((node == null) || (node.isRoot())) {
			JOptionPane.showMessageDialog(mainWindow,"You should select a folder for deletion at first",
					"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if ((node.getUserObject().toString().equals("Outgoing messages")) 
				|| (node.getUserObject().toString().equals("Incoming messages")) 
				|| (node.getUserObject().toString().equals("Draft messages"))) {
			JOptionPane.showMessageDialog(mainWindow,"You can not delete \"Outgoing messages\"" +
					", \"Incoming messages\" and \"Draft messages\" folders",
					"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
//		if (!updateFolder((DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent())) {
//			JOptionPane.showMessageDialog(mainWindow,"Current folder have been deleted",
//					"Error",JOptionPane.ERROR_MESSAGE);
//			return;
//		}
		
		int n = JOptionPane.showConfirmDialog(mainWindow,
				"Are you sure you want to delete \"" + node.getUserObject().toString() + "\" folder?",
			    "Question",JOptionPane.OK_CANCEL_OPTION);
		
		if (n == JOptionPane.OK_OPTION) {
			ServerResponse response = getClientProcess().getClientFolderService()
					.deleteFolder((FolderEntity)node.getUserObject());
			if (response.isError()) {
				JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
						"Error",JOptionPane.ERROR_MESSAGE);
				getClientProcess().stopClient();
				dispose();
			} else {
				if (response.isException()) {
					JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
							"Error",JOptionPane.ERROR_MESSAGE);
				} else {
					setFoldersInFoldersTree();
					JOptionPane.showMessageDialog(mainWindow,response.getResult(),
							"Information",JOptionPane.INFORMATION_MESSAGE);
				}
			}				
		}
	}
	
	private void moveMessageToAnotherFolderActionPerformed() {
		if (!updateFolder((DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent())) {
			JOptionPane.showMessageDialog(mainWindow,"Current folder have been deleted by someone",
					"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int row = messagesTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(mainWindow,"You should select a message for moving at first",
					"Error",JOptionPane.ERROR_MESSAGE);
		} else {
			
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)getFoldersTreeModel().getRoot();
			Object[] folderNames = new Object[getFoldersTreeModel().getChildCount(root)];
			for (int index = 0; index < folderNames.length; index++) {
				folderNames[index] = getFoldersTreeModel().getChild(root, index);				
			}
			DefaultMutableTreeNode newFolderName = ((DefaultMutableTreeNode)JOptionPane
					.showInputDialog(mainWindow, "Select a new folder for a message","Move to another folder",
							JOptionPane.PLAIN_MESSAGE,null,folderNames,
							(DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent()));
			
			if (!updateFolder(newFolderName)) {
				JOptionPane.showMessageDialog(mainWindow,"Folderwith such a name have been deleted by someone",
						"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if ((newFolderName == (DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent()) 
					|| (newFolderName == null)) {
				return; // folder have not changed
			}
			
			
			MessageEntity message = new MessageEntity(((MessagesTableModel)messagesTable.getModel())
					.getMessageAt(row));
			
			ServerResponse response = getClientProcess().getClientMessageService()
					.deleteMessage(((MessagesTableModel)messagesTable.getModel()).getMessageAt(row));
			if (response.isError()) {
				JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
						"Error",JOptionPane.ERROR_MESSAGE);
				getClientProcess().stopClient();
				dispose();
			} else {
				if (response.isException()) {
					JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
							"Error",JOptionPane.ERROR_MESSAGE);
				} else {
					((FolderEntity)newFolderName.getUserObject()).getListOfMessages().add(message);
					response = getClientProcess().getClientFolderService()
							.moveMessageToAnotherFolder((FolderEntity)newFolderName.getUserObject());
					if (response.isError()) {
						JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
								"Error",JOptionPane.ERROR_MESSAGE);
						getClientProcess().stopClient();
						dispose();
					} else {
						if (response.isException()) {
							JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
									"Error",JOptionPane.ERROR_MESSAGE);
						} else {
							showFolderContent((DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent());
							JOptionPane.showMessageDialog(mainWindow,response.getResult(),
									"Information",JOptionPane.INFORMATION_MESSAGE);	
						}
					}					
				}
			}		
		}
	}
	
	private void deleteMessageActionPerformed() {
		
		if (!updateFolder((DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent())) {
			JOptionPane.showMessageDialog(mainWindow,"Current folder have been deleted by someone",
					"Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int row = messagesTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(mainWindow,"You should select a message for deletion at first",
					"Error",JOptionPane.ERROR_MESSAGE);
		} else {
			int n = JOptionPane.showConfirmDialog(mainWindow,"Are you sure you want to delete selected message?",
				    "Question",JOptionPane.OK_CANCEL_OPTION);
			if (n == JOptionPane.OK_OPTION) {
				ServerResponse response = getClientProcess().getClientMessageService()
					.deleteMessage(((MessagesTableModel)messagesTable.getModel()).getMessageAt(row));
				if (response.isError()) {
					JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
							"Error",JOptionPane.ERROR_MESSAGE);
					getClientProcess().stopClient();
					dispose();
				} else {
					if (response.isException()) {
						JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
								"Error",JOptionPane.ERROR_MESSAGE);
					} else {;
						showFolderContent((DefaultMutableTreeNode)foldersTree.getLastSelectedPathComponent());
						JOptionPane.showMessageDialog(mainWindow,response.getResult(),
								"Information",JOptionPane.INFORMATION_MESSAGE);						
					}
				}				
			}
		}
	}
	
	private void showFolderContent(DefaultMutableTreeNode node) {
		if (updateFolder(node)) {
			FolderEntity folder = (FolderEntity)node.getUserObject();
			messageBodyTextArea.setText(null);
			getMessagesTableModel().setListOfMessages(folder.getListOfMessages());
			getMessagesTableModel().fireTableDataChanged();
		}
	}
	
	public ClientProcess getClientProcess() {
		return clientProcess;
	}
}
