package project.gui;

import java.awt.Cursor;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import project.common.ItemType;
import project.core.AbstractProject;
import project.gui.input.AddLinkInputHandler;
import project.gui.input.AddNodeInputHandler;
import project.gui.input.DefaultInputHandler;
import project.gui.input.DeleteInputHandler;
import project.gui.netconf.ConfPanel;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.nodes.PPath;

public class GCanvas extends PCanvas {
	private static final long serialVersionUID = 1L;
	
	AbstractProject project;
	Workspace workspace;
	
	PLayer mainLayer;
	PLayer secondLayer;
	LinkedList<GHost> hosts;
	LinkedList<GHost> collisionDomains;
	
	GPanel panel;
	
	DefaultInputHandler defaultHandler;
	DeleteInputHandler deleteHandler;
	AddNodeInputHandler addNodeHandler;
	AddLinkInputHandler addLinkHandler;
	
	PBasicInputEventHandler currentHandler;
	
	ConfPanel confPanel;
	
	public GCanvas( GPanel panel, ConfPanel confPanel ) {
		this.panel = panel;
		this.confPanel = confPanel;
		
		hosts = new LinkedList<GHost>();
		collisionDomains = new LinkedList<GHost>();
		project = GFactory.getInstance().createProject("notsetted", "notsetted");
		workspace = new Workspace( project );
		createCanvas();
	}
	
	public void createCanvas() {	
		mainLayer = getLayer();
		secondLayer = new PLayer();
		mainLayer.addChild(secondLayer);
		
		getZoomEventHandler().setMaxScale(1.4);
		getZoomEventHandler().setMinScale(0.4);
		
		defaultHandler = new DefaultInputHandler();
		deleteHandler = new DeleteInputHandler(this);
		addLinkHandler = new AddLinkInputHandler(this);
		
		switchToDefaultHandler();
	}
	
	public void saveProject() {
		workspace.saveProject(this);
	}

	public void adding( ItemType type ) {
		if( type != ItemType.LINK ) {
			// remove previously created addhandler
			if( currentHandler.equals(addNodeHandler) ) {
				removeInputEventListener(addNodeHandler);
			}
			
			// create a new addhandler for this node type
			addNodeHandler = new AddNodeInputHandler(this, type);
		}
		
		switchToAddHandler( type );
	}

	public void addNode( ItemType nodeType, Point2D pos ) {
		if( nodeType == ItemType.COLLISIONDOMAIN ) {
			GCollisionDomain collsionDomain = GFactory.getInstance().createCollisionDomain(pos.getX(), pos.getY());
			mainLayer.addChild(collsionDomain);
			project.addCollisionDomain(collsionDomain.getLogic());
		} else {
			GHost host = GFactory.getInstance().createGHost( nodeType, pos.getX(), pos.getY() );
			mainLayer.addChild(host);
			project.addHost(host.getLogic());
		}
		
		switchToDefaultHandler();
	}
	
	public void addLink( GHost host, GCollisionDomain collisionDomain ) {
		GLink link = GFactory.getInstance().createLink( host, collisionDomain );
		
		if( link == null ) {
			JOptionPane.showMessageDialog(this, "A netkit host can't have more then 4 interfaces");
			switchToDefaultHandler();
			return;
		}
		
		host.addLink(link);
		
		collisionDomain.addLink(link);
		secondLayer.addChild(link);
		
		switchToDefaultHandler();
	}
	
	public void deleting() {
		switchToDeleteHandler();
	}

	public void deleteNode( PNode node ) {
		try {
			mainLayer.removeChild(node);
			switchToDefaultHandler();
		} catch (Exception e) {
		}
	}

	private void switchToAddHandler( ItemType type ) {
		if( type == ItemType.LINK ) {
			if( !currentHandler.equals(addLinkHandler) ) {
				mainLayer.removeInputEventListener(defaultHandler);
				addInputEventListener(addLinkHandler);
				currentHandler = addLinkHandler;
			}
		} else {
			if( !currentHandler.equals(addNodeHandler) ) {
				mainLayer.removeInputEventListener(defaultHandler);
				addInputEventListener(addNodeHandler);
				currentHandler = addNodeHandler;
			}
		}
	}
	
	private void switchToDeleteHandler() {
		panel.setCursor( new Cursor(Cursor.CROSSHAIR_CURSOR));
		if( !currentHandler.equals(deleteHandler) ) {
			mainLayer.removeInputEventListener(defaultHandler);
			mainLayer.addInputEventListener(deleteHandler);
			currentHandler = deleteHandler;
		}
	}
	
	private void switchToDefaultHandler() {
		panel.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
		removeInputEventListener(addNodeHandler);
		removeInputEventListener(addLinkHandler);
		mainLayer.removeInputEventListener(deleteHandler);
		mainLayer.addInputEventListener(defaultHandler);
		currentHandler = defaultHandler;
	}

	public void addLine( PPath line ) {
		secondLayer.addChild(line);
	}

	public void deleteLink(PPath link) {
		try {
			secondLayer.removeChild(link);
		} catch (Exception e) {
		}
	}

	public void setConfPanel(ConfPanel confpanel) {
		this.confPanel = confpanel;
	}
}
