package demo;

import java.util.LinkedList;

import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.HamiltonianCycle;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TestJGraph {

	public static void main(String[] args) {
		
		UndirectedWeightedGraph g = new UndirectedWeightedGraph();
		
		g.addWeightedEdge(1, 2, 1);
		g.addWeightedEdge(1, 3, 1);
		g.addWeightedEdge(1, 4, 3);
		g.addWeightedEdge(2, 3, 3);
		g.addWeightedEdge(2, 4, 1);
		g.addWeightedEdge(3, 4, 1);
		
		LinkedList<Integer> list = (LinkedList<Integer>) HamiltonianCycle.getApproximateOptimalForCompleteGraph(g);
		System.out.println("Tour");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

}
