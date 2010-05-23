package com.netedit.gui.input;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.netedit.common.ItemType;
import com.netedit.gui.GuiManager;
import com.netedit.gui.ProjectHandler;
import com.netedit.netkit.Shell;


public class GActionListener implements ActionListener {

	ActionType action;
	
	GuiManager manager;
	
	public GActionListener( ActionType action ) {
		super();
		this.action = action;
		
		manager = GuiManager.getInstance();
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		switch (action) {
		case newProject:
			ProjectHandler.getInstance().newProject();
			break;
		case openProject:
			ProjectHandler.getInstance().openProject();
			break;
		case saveProject:
			ProjectHandler.getInstance().saveProject();
			break;
		case startLab:
			Shell.startLab( GuiManager.getInstance().getProject() );
			break;
		case stopLab:
			Shell.stopLab( GuiManager.getInstance().getProject(), true );
			break;
		case addPc:
			manager.getHandler().adding(ItemType.PC);
			break;
		case addRouter:
			manager.getHandler().adding(ItemType.ROUTER);
			break;
		case addFirewall:
			manager.getHandler().adding(ItemType.FIREWALL);
			break;
		case addArea:
			manager.getHandler().adding(ItemType.AREA);
			break;
		case addCollisionDomain:
			manager.getHandler().adding(ItemType.COLLISIONDOMAIN);
			break;
		case addTap:
			manager.getHandler().adding(ItemType.TAP);
			break;
		case addLink:
			manager.getHandler().adding(ItemType.LINK);
			break;
		case addNattedServer:
			manager.getHandler().adding(ItemType.NATTEDSERVER);
			break;
		case addServer: 
			manager.getHandler().adding(ItemType.SERVER);
			break;
		case delete:
			manager.getHandler().deleting();
			break;
		case clear:
			manager.getCanvas().clear();
			break;
		case showInfo:
			showInfo();
			break;
		case showLicence:
			showLicence();
			break;
		case exportImage:
			if( manager.getCanvas() != null ) 
				manager.getCanvas().export();
			break;
		case zoomIn:
			if( manager.getCanvas() != null ) 
				manager.getCanvas().zoomIn();
			break;
		case zoomOut:
			if( manager.getCanvas() != null ) 
				manager.getCanvas().zoomOut();
			break;
		case zoomOriginal:
			if( manager.getCanvas() != null ) 
				manager.getCanvas().zoomOriginal();
			break;
		case exit:
			manager.getFrame().closeApplication();
			break;
		}
	}
	
	public void showInfo() {
		String credits = "<html><center><b><font size=6>NetKit GUI</font></b></center><br>" +
			"Released under GNU General Public License version 3. " +
			"<a href=http://www.gnu.org/licenses/gpl-3.0-standalone.html>GPLv3</a><br><br>" +
			"Copyright © 2010 <i>Loria Salvatore</i><br>" +
			"Visit <a href=http://slash17.googlecode.com>http://slash17.googlecode.com</a></html>";

		JEditorPane editorPane = new JEditorPane("text/html", credits);
		editorPane.setEditable(false);

		editorPane.addHyperlinkListener( new HyperlinkListener() {
			public void hyperlinkUpdate( HyperlinkEvent evt ) {
				if( evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED ) {
					try {
						Desktop.getDesktop().browse( new URI(evt.getDescription()) );
					} catch (Exception e) {
					}
				}
			}
		});

		JOptionPane.showMessageDialog( GuiManager.getInstance().getFrame(), editorPane, "Abaut...", 
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/images/big/GNU.png"));
	}
	
	public void showLicence() {
		File f = new File("licence.txt");
		String text = "";
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			String s;
			while( (s = r.readLine() ) != null ) {
				text = text + s + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDialog d = new JDialog(manager.getFrame());
		d.setTitle("Licence");
		d.setContentPane(new JPanel(new BorderLayout()));
		JLabel title = new JLabel("NetKit GUI Licence", JLabel.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		title.setFont(new Font("Times New Roman", Font.BOLD, 24));
		d.add(title, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(new JTextArea(text));
		d.add(scrollPane);
		d.setSize(600, 600);
		d.setLocationRelativeTo(manager.getFrame());
		d.setVisible(true);
	}
	
	public enum ActionType {
		newProject,
		saveProject,
		openProject,
		startLab,
		stopLab,
		addPc,
		addRouter,
		addFirewall,
		addServer,
		addNattedServer,
		addLink,
		addCollisionDomain,
		addArea,
		addTap,
		delete, 
		clear,
		showInfo,
		showLicence,
		exportImage,
		zoomIn,
		zoomOut,
		zoomOriginal,
		exit;
	}
}
