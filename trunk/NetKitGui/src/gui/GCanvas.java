package gui;

import gui.input.AddLinkInputHandler;
import gui.input.AddNodeInputHandler;
import gui.input.DefaultInputHandler;
import gui.input.DeleteInputHandler;

import java.awt.Cursor;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import common.ItemType;

import core.Host;
import core.Project;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.nodes.PPath;

public class GCanvas extends PCanvas {
	private static final long serialVersionUID = 1L;
	
	Project project;
	Workspace workspace;
	
	PLayer mainLayer;
	PLayer secondLayer;
	LinkedList<GNode> hosts;
	LinkedList<GNode> collisionDomains;
	
	GPanel panel;
	
	DefaultInputHandler defaultHandler;
	DeleteInputHandler deleteHandler;
	AddNodeInputHandler addNodeHandler;
	AddLinkInputHandler addLinkHandler;
	
	PBasicInputEventHandler currentHandler;
	
	public GCanvas( GPanel panel ) {
		this.panel = panel;
		hosts = new LinkedList<GNode>();
		collisionDomains = new LinkedList<GNode>();
		project = new Project("notsetted", "notsetted");
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
		GNode node = GNodeFactory.createGNode( nodeType, pos.getX(), pos.getY() );
		if(  project == null ) 
			System.out.println("fuck");
		project.addHost( new Host( node.getName(), node.getType() ) );
		mainLayer.addChild(node);
		
		switchToDefaultHandler();
	}
	
	public void addLink( GNode node, GNode collisionDomain ) {
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

	public void deleteNode(GNode node) {
		// TODO aggiornamento logica
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

	
//	private GNode searchCollisionDomain(String cdName) {
//	Iterator<GNode> it = collisionDomains.iterator();
//	while( it.hasNext() ) {
//		GNode cd = it.next();
//		if( cd.getName().equals(cdName)) {
//			return cd;
//		}
//	}
//	return null;
//}
}
