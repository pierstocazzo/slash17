package gui;

import gui.input.AddLinkInputHandler;
import gui.input.AddNodeInputHandler;
import gui.input.DefaultInputHandler;
import gui.input.DeleteInputHandler;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import common.ItemType;

import core.Project;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.nodes.PPath;

public class GCanvas extends PCanvas {
	private static final long serialVersionUID = 1L;
	
	PLayer mainLayer;
	PLayer secondLayer;
	Project topology;
	LinkedList<GNode> hosts;
	LinkedList<GNode> collisionDomains;
	
	GPanel panel;
	
	DefaultInputHandler defaultHandler;
	DeleteInputHandler deleteHandler;
	AddNodeInputHandler addNodeHandler;
	AddLinkInputHandler addLinkHandler;
	
	PBasicInputEventHandler currentHandler;
	
	public GCanvas( GPanel panel ) {
		this.topology = new Project();
		this.panel = panel;
		hosts = new LinkedList<GNode>();
		collisionDomains = new LinkedList<GNode>();
		
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
		
//		for( Node node : topology.getNodes() ) {
//			Random r = new Random();
//			int x = r.nextInt(700);
//			int y = r.nextInt(600);
//			GNode gh = GNodeFactory.createGNode(node.getType(), x, y);
//			hosts.add(gh);
//			mainLayer.addChild(gh);
//			
//			for( Interface i : node.getInterfaces().values() ) {
//				String cdName = i.getCollisionDomain().getName();
//				
//				GNode cd = searchCollisionDomain( cdName );
//				
//				if( cd == null ) {
//					// sistemare in modo intelligente vicino all'host
//					cd = GNodeFactory.createGNode( ItemType.COLLISIONDOMAIN, x + 400, y );
//					i.getCollisionDomain().setName( cd.getName() );
//					collisionDomains.add(cd);
//					mainLayer.addChild(cd);
//				} else {
//					// sposta l'host in modo che sia vicino al dominio di collisione
//				}
//				
//				GLink l = new GLink(gh, cd);
//				gh.addLink(l);
//				cd.addLink(l);
//				secondLayer.addChild(l);
//			}
//		}
	}

	public void adding( ItemType type ) {
		// TODO aggiornamento logica

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
		mainLayer.addChild(node);
		
		switchToDefaultHandler();
	}
	
	public void addLink( GNode node, GNode collisionDomain ) {
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
		if( !currentHandler.equals(deleteHandler) ) {
			mainLayer.removeInputEventListener(defaultHandler);
			mainLayer.addInputEventListener(deleteHandler);
			currentHandler = deleteHandler;
		}
	}
	
	private void switchToDefaultHandler() {
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
