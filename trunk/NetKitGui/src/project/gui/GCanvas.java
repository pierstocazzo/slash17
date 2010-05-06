package project.gui;

import java.awt.Cursor;
import java.awt.geom.Point2D;

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
	PLayer areaLayer;
	
	GFrame frame;
	
	DefaultInputHandler defaultHandler;
	DeleteInputHandler deleteHandler;
	AddNodeInputHandler addNodeHandler;
	AddLinkInputHandler addLinkHandler;
	
	PBasicInputEventHandler currentHandler;
	
	ConfPanel confPanel;
	
	public GCanvas( GFrame gFrame, ConfPanel confPanel ) {
		this.frame = gFrame;
		this.confPanel = confPanel;
		
		project = GFactory.getInstance().createProject("notsetted", "notsetted");
		workspace = new Workspace( project );
		createCanvas();
	}
	
	public void createCanvas() {	
		mainLayer = getLayer();
		secondLayer = new PLayer();
		areaLayer = new PLayer();
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
			GCollisionDomain collsionDomain = GFactory.getInstance().createCollisionDomain(pos.getX(), pos.getY(), mainLayer);
			mainLayer.addChild(collsionDomain);
			project.addCollisionDomain(collsionDomain.getLogic());
		} else {
			GHost host = GFactory.getInstance().createGHost( nodeType, pos.getX(), pos.getY(), mainLayer );
			mainLayer.addChild(host);
			project.addHost(host.getLogic());
		}
		
		switchToDefaultHandler();
	}
	
	public void addLink( GHost host, GCollisionDomain collisionDomain ) {
		GLink link = GFactory.getInstance().createLink( host, collisionDomain, secondLayer );
		
		if( link == null ) {
			JOptionPane.showMessageDialog(this, "Can't add another link");
		} else {
			host.addLink(link);
			collisionDomain.addLink(link);
			secondLayer.addChild(link);
		}
		
		switchToDefaultHandler();
	}
	
	public void deleting() {
		switchToDeleteHandler();
	}

	public void delete( PNode node ) {
		try {
			if( node instanceof GLink ) {
				((GLink) node).delete();
				switchToDefaultHandler();
			} else if( node instanceof GCollisionDomain ) {
				((GCollisionDomain) node).delete();
				switchToDefaultHandler();
			} else if( node instanceof GHost ){
				((GHost) node).delete();
				switchToDefaultHandler();
			}
		} catch (Exception e) {
			switchToDefaultHandler();
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
		frame.setCursor( new Cursor(Cursor.CROSSHAIR_CURSOR));
		if( !currentHandler.equals(deleteHandler) ) {
			mainLayer.removeInputEventListener(defaultHandler);
			addInputEventListener(deleteHandler);
			currentHandler = deleteHandler;
		}
	}
	
	private void switchToDefaultHandler() {
		frame.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
		removeInputEventListener(addNodeHandler);
		removeInputEventListener(addLinkHandler);
		removeInputEventListener(deleteHandler);
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

	public PLayer getNodeLayer() {
		return mainLayer;
	}

	public PLayer getAreaLayer() {
		return areaLayer;
	}

	public PLayer getLinkLayer() {
		return secondLayer;
	}
}
