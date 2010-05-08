package project.gui.listeners;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import project.common.ItemType;
import project.gui.GuiManager;
import project.gui.ProjectHandler;
import project.netkit.Shell;

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
			String message;
			if( ProjectHandler.getInstance().saveProject() ) 
				message = "Project saved";
			else
				message = "Unable to save the project";
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), message);
			break;
			
		case startLab:
			Shell.startLab( GuiManager.getInstance().getProject().getDirectory() );
			break;
			
		case stopLab:
			Shell.stopLab( GuiManager.getInstance().getProject().getDirectory(), false );
			break;
			
		case addPc:
			manager.getCanvas().adding(ItemType.PC);
			break;
			
		case addRouter:
			manager.getCanvas().adding(ItemType.ROUTER);
			break;
			
		case addFirewall:
			manager.getCanvas().adding(ItemType.FIREWALL);
			break;
			
		case addArea:
			manager.getCanvas().adding(ItemType.AREA);
			break;
			
		case addCollisionDomain:
			manager.getCanvas().adding(ItemType.COLLISIONDOMAIN);
			break;
			
		case addTap:
			manager.getCanvas().adding(ItemType.TAP);
			break;
			
		case addLink:
			manager.getCanvas().adding(ItemType.LINK);
			break;
			
		case addNattedServer:
			manager.getCanvas().adding(ItemType.NATTEDSERVER);
			break;
			
		case addServer: 
			manager.getCanvas().adding(ItemType.SERVER);
			break;
		case delete:
			manager.getCanvas().deleting();
			break;
			
		case showInfo:
			showInfo();
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

		JOptionPane.showMessageDialog( GuiManager.getInstance().getFrame(), editorPane, "Info", 
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/images/images/GNU.png"));
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
		showInfo,
		exit;
	}
}
