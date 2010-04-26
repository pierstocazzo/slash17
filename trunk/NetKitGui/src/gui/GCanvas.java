package gui;

import gui.input.AddInputHandler;
import gui.input.DefaultInputHandler;
import gui.input.DeleteInputHandler;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import common.ItemType;

import core.Project;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;

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
	AddInputHandler addHandler;
	
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

		// remove previously created addhandler
		if( currentHandler.equals(addHandler) ) {
			removeInputEventListener(addHandler);
		}
		
		// create a new addhandler for this node type
		addHandler = new AddInputHandler(this, type);
		
		switchToAddHandler();
	}

	public void addNode( ItemType nodeType, Point2D pos ) {
		GNode node = GNodeFactory.createGNode( nodeType, pos.getX(), pos.getY() );
		mainLayer.addChild(node);
		
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

	private void switchToAddHandler() {
		if( !currentHandler.equals(addHandler) ) {
			mainLayer.removeInputEventListener(defaultHandler);
			addInputEventListener(addHandler);
			currentHandler = addHandler;
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
		removeInputEventListener(addHandler);
		mainLayer.removeInputEventListener(deleteHandler);
		mainLayer.addInputEventListener(defaultHandler);
		currentHandler = defaultHandler;
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
