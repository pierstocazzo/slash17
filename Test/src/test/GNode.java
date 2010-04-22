package test;

import java.util.ArrayList;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.util.PPaintContext;

public class GNode extends PNode {
	private static final long serialVersionUID = 1L;

	private ArrayList<GEdge> edges;
	private PPath circle;
	
	public GNode() {
		this.edges = new ArrayList<GEdge>();
		this.setCircle(PPath.createEllipse( 0, 0, 20, 20 ));
	}
	
	public GNode( ArrayList<GEdge> edges ) {
		this.edges = new ArrayList<GEdge>( edges );
		this.setCircle(PPath.createEllipse( 0, 0, 20, 20 ));
	}
	
	public GNode( int x, int y ) {
		this.edges = new ArrayList<GEdge>();
		this.setCircle(PPath.createEllipse( x, y, 20, 20 ));
	}
		
	public GEdge getEdge( int index ) {
		return edges.get( index );
	}
	
	public void addEdge( GEdge edge ) {
		edges.add(edge);
	}
	
	public ArrayList<GEdge> getEdges() {
		return edges;
	}

	public void setCircle( PPath circle ) {
		this.circle = circle;
		this.addChild(circle);
	}

	public PPath getCircle() {
		return circle;
	}
	
	@Override
	protected void paint(PPaintContext paintContext) {
		super.paint(paintContext);
	}
}
