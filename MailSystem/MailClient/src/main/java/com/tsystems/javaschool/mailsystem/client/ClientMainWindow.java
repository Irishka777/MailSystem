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
import java.awt.FlowLayout;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.shareableObjects.ServerResponse;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Font;
import java.util.List;

public class ClientMainWindow extends JFrame{

	private ClientProcess clientProcess;
	
	private ClientMainWindow mainWindow;
	private JTree foldersTree;
	private JTable messagesTable;
	JTextArea messageBodyTextArea;
	
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
					window.setLocationRelativeTo(null); // the window is centered on screen
					window.mainWindow.setVisible(true);
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
		initialize();
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
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				clientProcess = new ClientProcess();
				if (!clientProcess.startClient()) {
					JOptionPane.showMessageDialog(mainWindow,"Cannot get connection to server",
							"Error",JOptionPane.ERROR_MESSAGE);
					mainWindow.dispose();
					return;
				}
				loginWindow = new ClientLoginWindow(mainWindow);
				loginWindow.setLocationRelativeTo(mainWindow); // the window is centered on screen
				loginWindow.setVisible(true);
				
				if (!mainWindow.isDisplayable()) {
					return;
				}
				if (!fillFoldersTreeWithFolders()) {
					clientProcess.stopClient();
					mainWindow.dispose();
				}
				
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
		
		// creates foldersTree root element
		DefaultMutableTreeNode messages = new DefaultMutableTreeNode("Messages");
		
		// creates foldersTree, sets root element and properties
		foldersTree = new JTree(messages);
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
				if (node.isLeaf()) {
//					FolderEntity folder = (FolderEntity)node.getUserObject();
//					folder.getListOfMessages();
					// show messages in this folder in the right table
				}
				else {
					// show empty table
				}
			}
		});
		
		return new JScrollPane(foldersTree);
	}
	
	private JPanel createFolderButtonsPanel() {
		
		JButton createFolderButton = new JButton("Create folder");
		createFolderButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		createFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		JButton renameFolderButton = new JButton("Rename folder");
		renameFolderButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		renameFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		JButton deleteFolderButton = new JButton("Delete folder");
		deleteFolderButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		deleteFolderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
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
		
		return messagesSplitPane;
	}
	
	private JPanel createMessagesPanel() {
		
		// creates messages table and sets it into scroll pane
		messagesTable = new JTable();
		messagesTable.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));	
		JScrollPane messugesScrollPane = new JScrollPane(messagesTable);
		
		JButton createMessageButton = new JButton("Create message");
		createMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		createMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newMessageWindow = new ClientNewMessageWindow(mainWindow);
				newMessageWindow.setLocationRelativeTo(mainWindow); // the window is centered on screen
				newMessageWindow.setVisible(true);
			}
		});
		
		JButton deleteMessageButton = new JButton("Delete message");
		deleteMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		deleteMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		JPanel messageButtonsPanel = new JPanel();
		messageButtonsPanel.add(createMessageButton);
		messageButtonsPanel.add(deleteMessageButton);
		
		JPanel messagesPanel = new JPanel();
		messagesPanel.setLayout(new BorderLayout(0, 0));
		messagesPanel.add(messugesScrollPane, BorderLayout.CENTER);
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
	
	private boolean fillFoldersTreeWithFolders() {
		ServerResponse response = clientProcess.getClientFolderService()
				.findFoldersForMailBox(clientProcess.getUserMailBox());
		if (response.isException()) {
			JOptionPane.showMessageDialog(mainWindow,response.getExceptionMessage(),
					"Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		List<FolderEntity> foldersList = (List<FolderEntity>)response.getResult();
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)foldersTree.getPathForLocation(0, 0)
				.getLastPathComponent();
		for (FolderEntity folder:foldersList) {
			parent.add(new DefaultMutableTreeNode(folder));
		}
		foldersTree.expandRow(0);
		return true;
	}
	
	public ClientProcess getClientProcess() {
		return clientProcess;
	}
}
