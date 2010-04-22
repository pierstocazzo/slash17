package tests;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;

public class Edge extends PNode {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Node> nodes;
	PPath edge;
	
	/** 
	 * Create a graph edge
	 */
	public Edge() {
		nodes = new ArrayList<Node>();
		edge = PPath.createLine(0, 0, 0, 0);
		this.addChild(edge);
	}
	
	/** 
	 * Create a graph edge between two node
	 * 
	 * @param node1
	 * @param node2
	 */
	public Edge( Node node1, Node node2 ) {
		nodes = new ArrayList<Node>();
		nodes.add( node1 );
		nodes.add( node2 );
		node1.addEdge(this);
		node2.addEdge(this);
		
		Point2D.Double bound1 = (Point2D.Double) node1.getFullBounds().getCenter2D();
		Point2D.Double bound2 = (Point2D.Double) node2.getFullBounds().getCenter2D();
		
		edge = PPath.createLine( (float) bound1.getX(), (float) bound1.getY(), (float) bound2.getX(), (float) bound2.getY() );
		this.addChild(edge);
	}

	public void update() {
		edge.reset();
		Point2D.Double bound1 = (Point2D.Double) nodes.get(0).getFullBounds().getCenter2D();
		Point2D.Double bound2 = (Point2D.Double) nodes.get(1).getFullBounds().getCenter2D();
		
		edge.moveTo((float) bound1.getX(), (float) bound1.getY());
		edge.lineTo((float) bound2.getX(), (float) bound2.getY());
	}
	
	public Node getNode1() {
		return nodes.get(0);
	}
	
	public Node getNode2() {
		return nodes.get(1);
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public void setNodes( ArrayList<Node> nodes ) {
		this.nodes = nodes;
	}
	
	public boolean addNode( Node node, int index ) {
		if( index < 0 || index > 1 ) 
			return false;
		
		nodes.add( index, node );
		return true;
	}
}
