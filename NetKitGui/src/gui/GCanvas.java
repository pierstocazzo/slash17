package gui;

import input.AddInputHandler;
import input.DefaultInputHandler;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import common.ItemType;

import core.Host;
import core.Interface;
import core.Topology;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;

public class GCanvas extends PCanvas {
	private static final long serialVersionUID = 1L;
	
	PLayer mainLayer;
	PLayer secondLayer;
	Topology topology;
	LinkedList<GNode> hosts;
	LinkedList<GNode> collisionDomains;
	
	GPanel panel;
	
	DefaultInputHandler defaultHandler;

	AddInputHandler addHandler;
	
	public GCanvas( GPanel panel ) {
		this.topology = new Topology();
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
		getZoomEventHandler().setMinScale(0.8);
		
		defaultHandler = new DefaultInputHandler();
		mainLayer.addInputEventListener(defaultHandler);
		
		for( Host host : topology.getHosts() ) {
			Random r = new Random();
			int x = r.nextInt(700);
			int y = r.nextInt(600);
			GNode gh = GNodeFactory.createGNode(host.getType(), x, y);
			hosts.add(gh);
			mainLayer.addChild(gh);
			
			for( Interface i : host.getInterfaces().values() ) {
				String cdName = i.getCollisionDomain().getName();
				
				GNode cd = searchCollisionDomain( cdName );
				
				if( cd == null ) {
					// sistemare in modo intelligente vicino all'host
					cd = GNodeFactory.createGNode( ItemType.COLLISIONDOMAIN, x + 400, y );
					i.getCollisionDomain().setName( cd.getName() );
					collisionDomains.add(cd);
					mainLayer.addChild(cd);
				} else {
					// sposta l'host in modo che sia vicino al dominio di collisione
				}
				
				GLink l = new GLink(gh, cd);
				gh.addLink(l);
				cd.addLink(l);
				secondLayer.addChild(l);
			}
		}
	}

	public void addNode( ItemType type ) {
		// TODO aggiornamento logica
		GNode node = GNodeFactory.createGNode(type, 0, 0);
		hosts.add(node);
		
		addHandler = new AddInputHandler(this, node);
		
		switchToAddHandler();
	}

	private GNode searchCollisionDomain(String cdName) {
		Iterator<GNode> it = collisionDomains.iterator();
		while( it.hasNext() ) {
			GNode cd = it.next();
			if( cd.getName().equals(cdName)) {
				return cd;
			}
		}
		return null;
	}

	public void addNode( GNode node, Point2D pos ) {
		node.centerFullBoundsOnPoint( pos.getX(), pos.getY() );
		mainLayer.addChild(node);
		
		switchToDefaultHandler();
	}
	
	private void switchToAddHandler() {
		mainLayer.removeInputEventListener(defaultHandler);
		this.addInputEventListener(addHandler);
	}
	
	private void switchToDefaultHandler() {
		mainLayer.addInputEventListener(defaultHandler);
		this.removeInputEventListener(addHandler);
	}
}
