package tests;

import java.util.ArrayList;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;

public class Host extends PNode {
	private static final long serialVersionUID = 1L;

	private ArrayList<Link> edges;
	private PPath circle;
	
	public Host() {
		this.edges = new ArrayList<Link>();
		this.setCircle(PPath.createEllipse( 0, 0, 20, 20 ));
	}
	
	public Host( ArrayList<Link> edges ) {
		this.edges = new ArrayList<Link>( edges );
		this.setCircle(PPath.createEllipse( 0, 0, 20, 20 ));
	}
	
	public Host( int x, int y ) {
		this.edges = new ArrayList<Link>();
		this.setCircle(PPath.createEllipse( x, y, 20, 20 ));
	}
		
	public Link getEdge( int index ) {
		return edges.get( index );
	}
	
	public void addEdge( Link edge ) {
		edges.add(edge);
	}
	
	public ArrayList<Link> getEdges() {
		return edges;
	}

	public void setCircle( PPath circle ) {
		this.circle = circle;
		this.addChild(circle);
	}

	public PPath getCircle() {
		return circle;
	}
}
