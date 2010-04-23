package tests;

import input.DefaultInputHandler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import core.Host;
import core.Interface;
import core.Topology;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolox.PFrame;
import gui.GCollisionDomain;
import gui.GHost;
import gui.GLink;
import gui.GNode;

public class GUITest extends PFrame {
	private static final long serialVersionUID = 1L;
	
	PLayer mainLayer;
	PLayer secondLayer;
	Topology topology;
	LinkedList<GNode> hosts;
	LinkedList<GCollisionDomain> collisionDomains;
	
	public static void main(String[] args) {
		new GUITest();
	}
	
	public GUITest() {
		setSize(1000, 600);
		setTitle("NetKitGUI");
	}
	
	public void beforeInitialize() {
		this.topology = new Topology();
		hosts = new LinkedList<GNode>();
		collisionDomains = new LinkedList<GCollisionDomain>();
	}
	
	public void initialize() {	
		mainLayer = getCanvas().getLayer();
		secondLayer = new PLayer();
		mainLayer.addChild(secondLayer);
		
		getCanvas().getZoomEventHandler().setMaxScale(1.4);
		getCanvas().getZoomEventHandler().setMinScale(0.8);
		
		DefaultInputHandler handler = new DefaultInputHandler();
		mainLayer.addInputEventListener(handler);
		
		for( Host host : topology.getHosts() ) {
			Random r = new Random();
			int x = r.nextInt(700);
			int y = r.nextInt(600);
			GHost gh = new GHost( host.getId(), x, y );
			hosts.add(gh);
			mainLayer.addChild(gh);
			
			for( Interface i : host.getInterfaces().values() ) {
				String cdName = i.getCollisionDomain().getName();
				
				GCollisionDomain cd = searchCollisionDomain( cdName );
				
				if( cd == null ) {
					// sistemare in modo intelligente vicino all'host
					cd = new GCollisionDomain( cdName, x + 300, y );
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


	private GCollisionDomain searchCollisionDomain(String cdName) {
		Iterator<GCollisionDomain> it = collisionDomains.iterator();
		while( it.hasNext() ) {
			GCollisionDomain cd = it.next();
			if( cd.getId().equals(cdName)) {
				return cd;
			}
		}
		return null;
	}
}
