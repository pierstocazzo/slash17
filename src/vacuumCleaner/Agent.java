package vacuumCleaner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.HamiltonianCycle;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;

import demo.UndirectedWeightedGraph;


/**
 * Agent function for the vacuum-cleaner environment
 * @see Environment
 */

public class Agent extends AbstractAgent {

	private UndirectedWeightedGraph walkableGraph;
	private Floor myWorld;
	private UndirectedWeightedGraph tspGraph;

	public Agent(int x, int y, VisibilityType visType, int opBound){
		super(x, y, visType, opBound);
	}

	/**
	 * @return true if the agent has reached the goal
	 */
	public boolean goalReached(){
		updateGoal();
		return goalReached;
	}

	/** 
	 * Make the agent perceives from environment
	 * @param	perception	current perception from the environment
	 */
	public void perceives(Perception perception){
		this.perception = perception;
		if(actionList.size() == 0)
			this.myWorld = new Floor(perception.floor.length,
					perception.floor.width,
					Square.Type.UNKNOWN);
		for (int i = 0; i < myWorld.length; i++) {
			for (int j = 0; j < myWorld.width; j++) {
				if(perception.floor.get(i, j) != Square.Type.UNKNOWN)
					myWorld.set(i, j, perception.floor.get(i, j));
				System.out.print(myWorld.get(i, j));
			}
			System.out.println();
		}

	}

	/**
	 * Move the agent according with the visibility
	 */
	public void update(){
		switch (visType) {
		case MY_CELL:stupidBehaviour();break;
		case MY_NEIGHBOURS:;intelligentBehaviour();break;
		case ALL:intelligentBehaviour();break;
		}
	}

	private void intelligentBehaviour() {
		if(actionList.size() == 0)
			calculateHC();
		else{

		}
	}

	private void calculateHC() {
		System.out.println("Calculate HC");

		ArrayList<String> dirtyCells = new ArrayList<String>();
		dirtyCells.add("0-0");
		
		// Create walkable cells's graph
		walkableGraph = new UndirectedWeightedGraph();
		for (int i = 0; i < myWorld.length; i++)
			for (int j = 0; j < myWorld.length; j++){
				if(myWorld.get(i, j) != Square.Type.OBSTACLE){
					if( i-1 >= 0 && myWorld.get(i-1, j) != Square.Type.OBSTACLE){
						String v1 = i + "-" + j;
						String v2 = (i-1) + "-" + j;
						walkableGraph.addWeightedEdge(v1, v2, 1);
					}
					if( j-1 >= 0 && myWorld.get(i, j-1) != Square.Type.OBSTACLE){
						String v1 = i + "-" + j;
						String v2 = i + "-" + (j-1);
						walkableGraph.addWeightedEdge(v1, v2, 1);
					}
				}
				if(myWorld.get(i, j) == Square.Type.DIRTY)
					dirtyCells.add(i + "-" + j);
			}
		System.out.println("Walkable Graph");
		System.out.println(walkableGraph);
		
//		Calculate TSp Graph which contains only dirty cells and the home cell
		tspGraph = new UndirectedWeightedGraph();
		
		System.out.println("Dirty Cells");
		for (int i = 0; i < dirtyCells.size(); i++) {
			System.out.print(dirtyCells.get(i) + " ");
		}
		System.out.println();
		
		for (int i = 0; i < dirtyCells.size(); i++) {
			for (int j = i+1; j < dirtyCells.size(); j++) {
				System.out.println("Path da " + dirtyCells.get(i) + " a " + dirtyCells.get(j));
				double weight = new DijkstraShortestPath<String, DefaultWeightedEdge>(walkableGraph, dirtyCells.get(i), dirtyCells.get(j)).getPathLength();
				tspGraph.addWeightedEdge(dirtyCells.get(i), dirtyCells.get(j), weight);
			}
		}
		System.out.println("TSP Graph");
		System.out.println(tspGraph);
		
//		ArrayList<String> list = new ArrayList<String>(HamiltonianCycle.getApproximateOptimalForCompleteGraph(graph));
		
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print(list.get(i) + " ");
//		}
//		System.out.println();
//		System.out.println("Size " + list.size());
		
		//Set home as first element of solution
//		int homeIndex = list.indexOf("0,0");
//		if(homeIndex != 0){
//			List<String> first = list.subList(homeIndex, list.size());
//			List<String> second = list.subList(0, homeIndex);
//			list = new ArrayList<String>(first);
//			list.addAll(second);
//		}
//		
//		System.out.println("Solution");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print(list.get(i) + " ");
//		}
//		System.out.println();
//		System.out.println("Size " + list.size());
		
		
	}

	/**
	 * Suck the tile if is dirty and moves around randomly if it is clean
	 */
	public void stupidBehaviour(){
		System.out.println("MY CELL " + x + "," + y + ": " + perception.floor.get(x,y));
		if(perception.floor.get(x,y) == Square.Type.DIRTY)
			currAction = Action.Type.SUCK;
		else{
			LinkedList<Action.Type> list = new LinkedList<Action.Type>();
			list.add(Action.Type.NORTH);
			list.add(Action.Type.SOUTH);
			list.add(Action.Type.EAST);
			list.add(Action.Type.WEST);
			Collections.shuffle(list);
			currAction = list.getFirst();
		}
	}

	/**
	 * Print the list of agent actions 
	 */
	public void showActions(){
		for(int i=0; i<actionList.size(); i++)
			System.out.println(actionList.get(i).type);
	}

	/**
	 * Counts the number of cleaned tiles in the floor
	 * @return current number of cleaned tiles
	 */
	public int squaresNowCleaned(){
		int cleanedSquare = 0;
		for (int i = 0; i < perception.floor.length; i++)
			for (int j = 0; j < perception.floor.width; j++)
				if(perception.floor.get(i,j) == Square.Type.CLEAN)
					cleanedSquare++;
		return cleanedSquare;
	}
	
	/**
	 * Counts the number of dirty tiles in the floor
	 * @return current number of dirty tiles
	 */
	public int dirtySquares(){
		int dirtySquare = 0;
		for (int i = 0; i < perception.floor.length; i++)
			for (int j = 0; j < perception.floor.width; j++)
				if(perception.floor.get(i,j) == Square.Type.DIRTY)
					dirtySquare++;
		return dirtySquare;
	}

	public void updateGoal(){
		if(actionList.size()>=opBound){
			System.out.println("GOAL REACHED");
			goalReached = true;
		}
	}

	/**
	 * Add the current action to the agent action list
	 * @return current action of the agent
	 */
	public Action.Type action(){
		actionList.add(new Action(currAction, x, y));
		return currAction;
	}
}
