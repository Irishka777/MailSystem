package com.tsystems.javaschool.mailsystem.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.tsystems.javaschool.mailsystem.entities.FolderEntity;
import com.tsystems.javaschool.mailsystem.entities.MailBoxEntity;
import com.tsystems.javaschool.mailsystem.entities.UserEntity;

@SuppressWarnings("serial")
public class ClientWindow  extends JFrame {
	
	private JFrame frame; 
	private JMenuBar menuBar;
	private JMenuItem createMessageMenuItem;
	private JSplitPane folderAndMessageSplitPane;
	private JPanel foldersPanel;
	private JScrollPane folderTreeScrollPane;
	private JTree folderTree;
	private JPanel folderButtonsPanel;
	private JButton newFolderButton;
	private JButton deleteFolderButton;
	private JSplitPane messageSplitPane;
	private JScrollPane messageListScrollPane;
	private JTable messageListTable;
	private JScrollPane messageBodyScrollPane;
	private JTextArea messageBodyTextArea;
	
	public ClientWindow() {
		initComponents();
	}
	
	private void initComponents() {	
		frame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Mail cliet");
		setJMenuBar(createMenuBar());
		setContentPane(createFolderAndMessageSplitPane());
		
		pack();
		setLocationRelativeTo(null); // the window is centered on screen
		setVisible(true);
	}
	
	private JMenuBar createMenuBar() {
		menuBar = new JMenuBar();
		createMessageMenuItem = new JMenuItem();
		createMessageMenuItem.setText("New massage");
		menuBar.add(createMessageMenuItem);
		return menuBar;
	}
	
	private JSplitPane createFolderAndMessageSplitPane() {
		folderAndMessageSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,createFoldersPanel(),
				createMessageSplitPane());
		folderAndMessageSplitPane.setOneTouchExpandable(true);
		folderAndMessageSplitPane.setResizeWeight(0.5);
		return folderAndMessageSplitPane;
	}
	
	private JPanel createFoldersPanel() {
		foldersPanel = new JPanel(new BorderLayout());
		foldersPanel.add(createFolderTreeScrollPan(),BorderLayout.CENTER);
		foldersPanel.add(createFolderButtonsPanel(),BorderLayout.PAGE_END);
//		foldersPanel.setMinimumSize(new Dimension(100, 100));
		return foldersPanel;
	}
	
	private JScrollPane createFolderTreeScrollPan() {
		
		DefaultMutableTreeNode messages = new DefaultMutableTreeNode("Messages");
		DefaultMutableTreeNode incoming = new DefaultMutableTreeNode(new FolderEntity("incoming messages",
				new MailBoxEntity("address", "123", new UserEntity("name","surname",1990,12,3,"phoneNumber"))));
		messages.add(incoming);
		folderTree = new JTree(messages);
//		folderTree.setMinimumSize(new Dimension(300,1000));
//		folderTree.setPreferredSize(new Dimension(300,1000));
//		folderTree.setMaximumSize(new Dimension(300,1000));
		folderTreeScrollPane = new JScrollPane(folderTree);
//		folderTreeScrollPane.setMinimumSize(new Dimension(300,1000));
//		folderTreeScrollPane.setPreferredSize(new Dimension(300,1000));
//		folderTreeScrollPane.setMaximumSize(new Dimension(300,1000));
		return folderTreeScrollPane;
	}
	
	private JPanel createFolderButtonsPanel() {
		newFolderButton = new JButton("Create");
		newFolderButton.setToolTipText("Click this button to create a new folder");
		newFolderButton.setFocusPainted(false);
		
		newFolderButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String s = (String)JOptionPane.showInputDialog(frame,"Enter new folder name",
		    			"Create a new folder",JOptionPane.PLAIN_MESSAGE,null,null,"ham");
		    }
//		    	//If a string was returned, say so.
//		        if ((s != null) && (s.length() > 0)) {
//		            setLabel("Green eggs and... " + s + "!");
//		            return;
//		        }
//
//		        //If you're here, the return value was null/empty.
//		        setLabel("Come on, finish the sentence!");
//		    		    	newFolderButtonActionPerformed(e);
//		    		    }
		});
//		newFolderButton.setSize(100, 30);
//		newFolderButton.setMaximumSize(new Dimension(100,30));
//		newFolderButton.setPreferredSize(new Dimension(100,30));
//		newFolderButton.setMinimumSize(new Dimension(100,30));

		deleteFolderButton = new JButton("Delete");
		deleteFolderButton.setToolTipText("Click this button to delete selected folder");
		deleteFolderButton.setFocusPainted(false);
		deleteFolderButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	int n = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this folder?",
		    			"Delete folder", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                	folderTree.collapseRow(0);
                } else {}
//		    	JOptionPane.showMessageDialog(frame, "Eggs are not supposed to be green.");
		    }
		});
		
//		deleteFolderButton.setSize(100, 30);
//		deleteFolderButton.setMaximumSize(new Dimension(100,30));
//		deleteFolderButton.setPreferredSize(new Dimension(100,30));
//		deleteFolderButton.setMinimumSize(new Dimension(100,30));
	
//		folderButtonsPanel = new JPanel(new GridLayout());
		folderButtonsPanel = new JPanel();
		folderButtonsPanel.add(newFolderButton);
		folderButtonsPanel.add(deleteFolderButton);
//		folderButtonsPanel.setSize(200, 60);
//		folderButtonsPanel.setMinimumSize(new Dimension(250, 40));
//		folderButtonsPanel.setPreferredSize(new Dimension(250, 40));
//		folderButtonsPanel.setMaximumSize(new Dimension(250, 40));
		return folderButtonsPanel;
	}
	
	private JSplitPane createMessageSplitPane() {
		messageSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,createMessageListScrollPane(),
				createMessageBodyScrollPane());
		messageSplitPane.setOneTouchExpandable(true);
		messageSplitPane.setMinimumSize(new Dimension(500, 100));
		return messageSplitPane;
	}
	
	private JScrollPane createMessageListScrollPane() {
		messageListTable = new JTable();
		messageListScrollPane = new JScrollPane(messageListTable);
		return messageListScrollPane;
	}
	
	private JScrollPane createMessageBodyScrollPane() {
		messageBodyTextArea = new JTextArea();
		messageBodyScrollPane = new JScrollPane(messageBodyTextArea);
		return messageBodyScrollPane;
	}
	
	private void newFolderButtonActionPerformed(ActionEvent e) {
		JDialog newFolderDialodWindow = new JDialog(this,"Enter new folder name");
		
		JTextField folderName = new JTextField();
		folderName.setPreferredSize(new Dimension(200,20));
		
		JButton okButton = new JButton("OK");
		okButton.setFocusPainted(false);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 okButtonActionPerformed(e);
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFocusPainted(false);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				cancelButtonActionPerformed(e);
			}
		});
		
		JPanel panel = new JPanel();	
		panel.add(folderName);
		panel.add(okButton);
		panel.add(cancelButton);
		
		newFolderDialodWindow.setContentPane(panel);
		newFolderDialodWindow.pack();
		newFolderDialodWindow.setLocationRelativeTo(this); // the window is centered on screen
		newFolderDialodWindow.setVisible(true);	
	}
	
	private void okButtonActionPerformed(ActionEvent e) {
		
	}
	
	private void cancelButtonActionPerformed(ActionEvent e) {
		setVisible(false);
		dispose();
	}
   
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new ClientWindow();
            }
        });	
	}
}
