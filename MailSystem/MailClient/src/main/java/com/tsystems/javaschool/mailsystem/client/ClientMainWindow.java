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

public class ClientMainWindow {

	private ClientProcess clientProcess;
	
	private JFrame mailClientFrame;
	private JTree foldersTree;
	private JTable messagesTable;
	
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
					window.mailClientFrame.pack();
					window.mailClientFrame.setLocationRelativeTo(null); // the window is centered on screen
					window.mailClientFrame.setVisible(true);
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
		mailClientFrame = new JFrame();
		mailClientFrame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				clientProcess = new ClientProcess();
				if (!clientProcess.startClient()) {
					JOptionPane.showMessageDialog(mailClientFrame,"Cannot get connection to server",
							"Error",JOptionPane.ERROR_MESSAGE);
					mailClientFrame.dispose();
					return;
				}
				loginWindow = new ClientLoginWindow(clientProcess);
				loginWindow.setLocationRelativeTo(mailClientFrame);
				loginWindow.setVisible(true);
				
				if (!fillFoldersTreeWithFolders()) {
					mailClientFrame.dispose();
				}
				
			}
			public void windowClosing(WindowEvent arg0) {
				clientProcess.stopClient();
			}
		});
		mailClientFrame.setTitle("Mail client");
		mailClientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newMessageWindow = new ClientNewMessageWindow(clientProcess);
		
		JSplitPane foldersAndMessagesSplitPane = new JSplitPane();
		foldersAndMessagesSplitPane.setResizeWeight(0.5);
		foldersAndMessagesSplitPane.setOneTouchExpandable(true);
		mailClientFrame.getContentPane().add(foldersAndMessagesSplitPane, BorderLayout.CENTER);
		
		JPanel foldersPanel = new JPanel();
		foldersAndMessagesSplitPane.setLeftComponent(foldersPanel);
		foldersPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane foldersTreeScrollPane = new JScrollPane();
		foldersPanel.add(foldersTreeScrollPane, BorderLayout.CENTER);
		
		foldersTree = new JTree();
		foldersTree.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		foldersTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
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
		
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(renderer.getDefaultClosedIcon());
		foldersTree.setCellRenderer(renderer);
		
		foldersTree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Messages") {
				{
				}
			}
		));
		foldersTreeScrollPane.setViewportView(foldersTree);
		
		JPanel folderButtonsPanel = new JPanel();
		foldersPanel.add(folderButtonsPanel, BorderLayout.SOUTH);
		
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
		folderButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		folderButtonsPanel.add(createFolderButton);
		folderButtonsPanel.add(renameFolderButton);
		folderButtonsPanel.add(deleteFolderButton);
		
		JSplitPane messagesSplitPane = new JSplitPane();
		messagesSplitPane.setResizeWeight(0.5);
		messagesSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		foldersAndMessagesSplitPane.setRightComponent(messagesSplitPane);
		
		JPanel messagesPanel = new JPanel();
		messagesSplitPane.setLeftComponent(messagesPanel);
		messagesPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane messugesScrollPane = new JScrollPane();
		messagesPanel.add(messugesScrollPane, BorderLayout.CENTER);
		
		messagesTable = new JTable();
		messagesTable.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		messugesScrollPane.setViewportView(messagesTable);
		
		JPanel messageButtonsPanel = new JPanel();
		messagesPanel.add(messageButtonsPanel, BorderLayout.SOUTH);
		
		JButton createMessageButton = new JButton("Create message");
		createMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		createMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newMessageWindow.setLocationRelativeTo(mailClientFrame);
				newMessageWindow.setVisible(true);
			}
		});
		messageButtonsPanel.add(createMessageButton);
		
		JButton deleteMessageButton = new JButton("Delete message");
		deleteMessageButton.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		deleteMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		messageButtonsPanel.add(deleteMessageButton);
		
		JScrollPane messageBodyScrollPane = new JScrollPane();
		messagesSplitPane.setRightComponent(messageBodyScrollPane);
		
		JTextArea messageBodyTextArea = new JTextArea();
		messageBodyTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
		messageBodyTextArea.setEditable(false);
		messageBodyScrollPane.setViewportView(messageBodyTextArea);
	}
	
	private boolean fillFoldersTreeWithFolders() {
		ServerResponse response = clientProcess.getClientFolderService()
				.findFoldersForMailBox(clientProcess.getUserMailBox());
		if (response.isException()) {
			JOptionPane.showMessageDialog(mailClientFrame,response.getExceptionMessage(),
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

}
