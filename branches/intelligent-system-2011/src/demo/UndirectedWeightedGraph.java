package demo;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class UndirectedWeightedGraph extends SimpleWeightedGraph<Integer, DefaultWeightedEdge> {

	private static final long serialVersionUID = 1L;

	public UndirectedWeightedGraph () {
		super(DefaultWeightedEdge.class);
	}
	
	public void addWeightedEdge (Integer v1, Integer v2, double weight) {
		if (!containsVertex(v1))
			addVertex(v1);
		if (!containsVertex(v2))
			addVertex(v2);
		DefaultWeightedEdge e = new DefaultWeightedEdge();
		addEdge(v1, v2, e);
		setEdgeWeight(e, weight);
	}
}
