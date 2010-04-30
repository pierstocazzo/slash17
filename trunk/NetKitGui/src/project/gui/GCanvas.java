package project.gui;

import java.awt.Cursor;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import project.common.ItemType;
import project.core.Project;
import project.gui.input.AddLinkInputHandler;
import project.gui.input.AddNodeInputHandler;
import project.gui.input.DefaultInputHandler;
import project.gui.input.DeleteInputHandler;


import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.nodes.PPath;

public class GCanvas extends PCanvas {
	private static final long serialVersionUID = 1L;
	
	Project project;
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
	
	GFactory factory;
	
	public GCanvas( GPanel panel, GFactory gFactory ) {
		this.panel = panel;
		this.factory = gFactory;
		
		hosts = new LinkedList<GHost>();
		collisionDomains = new LinkedList<GHost>();
//		project = new Project("notsetted", "notsetted"); TODO
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
			GCollisionDomain collsionDomain = factory.createCollisionDomain(pos.getX(), pos.getY());
			mainLayer.addChild(collsionDomain);
		} else {
			GHost host = factory.createGHost( nodeType, pos.getX(), pos.getY() );
			mainLayer.addChild(host);
		}
		
//		project.addHost( new Host( node.getName(), node.getType() ) ); TODO
		
		switchToDefaultHandler();
	}
	
	public void addLink( GHost node, GCollisionDomain collisionDomain ) {
		// TODO aggiornamento logica
		GLink link = new GLink( node, collisionDomain );
		node.addLink(link);
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
}
