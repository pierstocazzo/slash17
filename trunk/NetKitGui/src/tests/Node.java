package tests;

import java.util.ArrayList;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;

public class Node extends PNode {
	private static final long serialVersionUID = 1L;

	private ArrayList<Edge> edges;
	private PPath circle;
	
	public Node() {
		this.edges = new ArrayList<Edge>();
		this.setCircle(PPath.createEllipse( 0, 0, 20, 20 ));
	}
	
	public Node( ArrayList<Edge> edges ) {
		this.edges = new ArrayList<Edge>( edges );
		this.setCircle(PPath.createEllipse( 0, 0, 20, 20 ));
	}
	
	public Node( int x, int y ) {
		this.edges = new ArrayList<Edge>();
		this.setCircle(PPath.createEllipse( x, y, 20, 20 ));
	}
		
	public Edge getEdge( int index ) {
		return edges.get( index );
	}
	
	public void addEdge( Edge edge ) {
		edges.add(edge);
	}
	
	public ArrayList<Edge> getEdges() {
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
