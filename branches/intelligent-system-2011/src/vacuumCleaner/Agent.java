package vacuumCleaner;

import interfaces.PopupGrafo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.ListenableGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.HamiltonianCycle;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.ListenableUndirectedGraph;

import vacuumCleaner.Action.Type;

import demo.UndirectedWeightedGraph;


/**
 * Agent function for the vacuum-cleaner environment
 * @see Environment
 */

public class Agent extends AbstractAgent {

	private UndirectedWeightedGraph walkableGraph;
	private Floor myWorld;
	private UndirectedWeightedGraph tspGraph;

	public ArrayList<Action> calculatedAction;

	/** matrice dei punteggi delle celle (in visibilità neighbours) */
	int[][] scores;
	int[][] visited;
	private boolean visitedInitialized = false;

	public Agent(int x, int y, VisibilityType visType, int opBound){
		super(x, y, visType, opBound);
		calculatedAction = new ArrayList<Action>();
		
	}

	/**
	 * @return true if the agent has reached the goal
	 */
	public boolean goalReached(){
		updateGoal();
		return goalReached;
	}

	/** 
	 * Make the import org.jgrapht.ext.JGraphModelAdapteragent perceives from environment
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
		if(!visitedInitialized ){
			visitedInitialized = true;
			visited = new int[myWorld.length][myWorld.width];
			for (int i = 0; i < myWorld.length; i++)
				for (int k = 0; k < myWorld.width; k++)
					visited[i][k] = 0;
		}
	}

	/**
	 * Move the agent according with the visibility
	 */
	public void update(){
		switch (visType) {
		case MY_CELL:stupidBehaviour();break;
		case MY_NEIGHBOURS:;neighboursBehaviour();break;
		case ALL:intelligentBehaviour();break;
		}
	}

	private void intelligentBehaviour() {
		if(actionList.size() == 0)
			calculateHC();
		else{
			if(myWorld.get(x, y) == Square.Type.DIRTY)
				currAction = Action.Type.SUCK;
			else {
				if(!calculatedAction.isEmpty())
					currAction = calculatedAction.remove(0).type;
				else
					goalReached = true;
			}
		}
	}

	private void neighboursBehaviour() {
		visited[x][y]++;
		
		if(myWorld.get(x, y) == Square.Type.DIRTY)
			currAction = Action.Type.SUCK;
		else {
			scores = new int[myWorld.width][myWorld.length];
			System.out.println("Scores: ");
			for (int i = 0; i < myWorld.width; i++) {
				for (int j = 0; j < myWorld.length; j++) {
					scores[i][j] = 9 - cleanNeighbours(i,j) - isBorder(i,j) - isCorner(i,j) + knownDirty(i,j) + isDirty(i,j) - isOstacle(i,j) -isVisited(i,j);
					System.out.print(scores[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();

			currAction = findBetterAction();
		}
	}

	private int isVisited(int i, int j) {
		return visited[i][j];
	}

	private int isDirty(int i, int j) {
		if (myWorld.get(i, j) == Square.Type.DIRTY)
			return 5;
		return 0;
	}

	private int isOstacle(int i, int j) {
		if(myWorld.get(i, j) == Square.Type.OBSTACLE)
			return Integer.MAX_VALUE;
		return 0;
	}

	private Type findBetterAction() {
		int max = Integer.MIN_VALUE;

		Action a = new Action(Action.Type.NOOP);

		int i = x;
		int j = y;

		if (i > 0)
			if (scores[i-1][j] >= max) {
				max = scores[i-1][j];
				a = new Action(Action.Type.NORTH);
			}

		if (i < myWorld.width-1) 
			if (scores[i+1][j] >= max) {
				max = scores[i+1][j];
				a = new Action(Action.Type.SOUTH);
			}

		if (j > 0)
			if (scores[i][j-1] >= max) {
				max = scores[i][j-1];
				a = new Action(Action.Type.WEST);
			}

		if (j < myWorld.length-1)
			if (scores[i][j+1] >= max) {
				max = scores[i][j+1];
				a = new Action(Action.Type.EAST);
			}

		return a.type;
	}

	private int knownDirty(int i, int j) {
		int count = 0;
		if (myWorld.get(i, j) == Square.Type.DIRTY) count++;

		if (i > 0 && j > 0)
			if (myWorld.get(i-1, j-1) == Square.Type.DIRTY) count++;

		if (i > 0)
			if (myWorld.get(i-1, j) == Square.Type.DIRTY) count++;

		if (i < myWorld.width-1) 
			if (myWorld.get(i+1, j) == Square.Type.DIRTY) count++;

		if (i < myWorld.width-1 && j < myWorld.length-1)
			if (myWorld.get(i+1, j+1) == Square.Type.DIRTY) count++;

		if (j > 0)
			if (myWorld.get(i, j-1) == Square.Type.DIRTY) count++;

		if (j < myWorld.length-1)
			if (myWorld.get(i, j+1) == Square.Type.DIRTY) count++;

		if (i > 0 && j < myWorld.length-1)
			if (myWorld.get(i-1, j+1) == Square.Type.DIRTY) count++;

		if (j > 0 && i < myWorld.width-1)
			if (myWorld.get(i+1, j-1) == Square.Type.DIRTY) count++;

		return count;
	}

	private int isCorner(int i, int j) {
		int c = 0;
		if ((i == 0 && j == 0) || 
				(i == 0 && j == myWorld.length-1) ||
				(i == myWorld.width-1 && j == 0) || 
				(i == myWorld.width-1 && j == myWorld.length-1))
			c = 2;
		return c;
	}

	private int isBorder(int i, int j) {
		int c = 0;
		if (i == 0 || i == myWorld.width-1 || j == 0 || j == myWorld.length-1)
			c = 3;
		return c;
	}

	private int cleanNeighbours(int i, int j) {
		int count = 0;
		if (myWorld.get(i, j) == Square.Type.CLEAN) count++;

		if (i > 0 && j > 0)
			if (myWorld.get(i-1, j-1) == Square.Type.CLEAN) count++;

		if (i > 0)
			if (myWorld.get(i-1, j) == Square.Type.CLEAN) count++;

		if (i < myWorld.width-1) 
			if (myWorld.get(i+1, j) == Square.Type.CLEAN) count++;

		if (i < myWorld.width-1 && j < myWorld.length-1)
			if (myWorld.get(i+1, j+1) == Square.Type.CLEAN) count++;

		if (j > 0)
			if (myWorld.get(i, j-1) == Square.Type.CLEAN) count++;

		if (j < myWorld.length-1)
			if (myWorld.get(i, j+1) == Square.Type.CLEAN) count++;

		if (i > 0 && j < myWorld.length-1)
			if (myWorld.get(i-1, j+1) == Square.Type.CLEAN) count++;

		if (j > 0 && i < myWorld.width-1)
			if (myWorld.get(i+1, j-1) == Square.Type.CLEAN) count++;

		return count;
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

		ArrayList<String> removedVertex = new ArrayList<String>();

		//Remove unreachable cells from home
		GraphPath<String, DefaultWeightedEdge> path = null;
		for (int j = 1; j < dirtyCells.size(); j++) {
			try{
				System.out.println("Search Path "+dirtyCells.get(0) + "," +dirtyCells.get(j));
				path = new DijkstraShortestPath<String, DefaultWeightedEdge>(walkableGraph, dirtyCells.get(0), dirtyCells.get(j)).getPath();
				if(path == null){
					removedVertex.add(dirtyCells.get(j));
					System.out.println("Prepare Delete " + dirtyCells.get(j));
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				removedVertex.add(dirtyCells.get(j));
				System.out.println("Prepare Delete " + dirtyCells.get(j));
			}
		}
		for (int i = 0; i < removedVertex.size(); i++){
			if(walkableGraph.containsVertex(removedVertex.get(i)))
				walkableGraph.removeVertex(removedVertex.get(i));
			if(dirtyCells.contains(removedVertex.get(i)))
				dirtyCells.remove(removedVertex.get(i));
		}
//		System.out.println("Walkable Graph");
//		System.out.println(walkableGraph);

//		new PopupGrafo().show(walkableGraph, myWorld.length);
		
		//		Calculate TSp Graph which contains only dirty cells and the home cell
		tspGraph = new UndirectedWeightedGraph();

		System.out.println("Dirty Cells");
		for (int i = 0; i < dirtyCells.size(); i++) {
			System.out.print(dirtyCells.get(i) + " ");
		}
		System.out.println();

		for (int i = 0; i < dirtyCells.size(); i++) {
			for (int j = i+1; j < dirtyCells.size(); j++) {
				//				System.out.println("Path da " + dirtyCells.get(i) + " a " + dirtyCells.get(j));
				double weight = new DijkstraShortestPath<String, DefaultWeightedEdge>(walkableGraph, dirtyCells.get(i), dirtyCells.get(j)).getPathLength();
				tspGraph.addWeightedEdge(dirtyCells.get(i), dirtyCells.get(j), weight);
			}
		}
		//		System.out.println("TSP Graph");
		//		System.out.println(tspGraph);

		ArrayList<String> list = new ArrayList<String>(HamiltonianCycle.getApproximateOptimalForCompleteGraph(tspGraph));

		//		System.out.println("Solution");
		//		for (int i = 0; i < list.size(); i++) {
		//			System.out.print(list.get(i) + " ");
		//		}
		//		System.out.println();
		//		System.out.println("Size " + list.size());

		//Set home as first element of solution
		int homeIndex = list.indexOf("0-0");
		if(homeIndex != 0 && list.size()>1){
			List<String> first = list.subList(homeIndex, list.size());
			List<String> second = list.subList(0, homeIndex);
			list = new ArrayList<String>(first);
			list.addAll(second);
			list.add("0-0");
		}

		System.out.println("Tour Solution");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
		System.out.println();
		System.out.println("Size " + list.size());

		// Convert Tour Solution in Cells's list
		ArrayList<String> cellList = new ArrayList<String>();
		for (int i = 0; i < list.size()-1; i++) {
			if(cellList.isEmpty() || !cellList.get(cellList.size()-1).equals(list.get(i))){
				cellList.add(list.get(i));
				ArrayList<DefaultWeightedEdge> edgeList = 
						new ArrayList<DefaultWeightedEdge>(
								new DijkstraShortestPath<String, DefaultWeightedEdge>(walkableGraph, list.get(i), list.get(i+1)).getPathEdgeList());
				System.out.println(edgeList);
				String curr = list.get(i);
				for (int j = 0; j < edgeList.size()-1; j++) {
					DefaultEdge edge = edgeList.get(j);
					Field sourceRef  = null, targetRef = null;
					try {
						sourceRef = DefaultEdge.class.getSuperclass().getDeclaredField("source");
						targetRef = DefaultEdge.class.getSuperclass().getDeclaredField("target");
						sourceRef.setAccessible(true);
						targetRef.setAccessible(true);
						String source = (String) sourceRef.get(edge);
						String target = (String) targetRef.get(edge);
						//System.out.println("Source "+source);
						//System.out.println("Target "+target);
						if(source.equals(curr)){
							curr = target;
							cellList.add(target);
						}
						else{
							curr = source;
							cellList.add(source);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		cellList.add("0-0");

		System.out.println("Cells's List");
		for (int i = 0; i < cellList.size(); i++) {
			System.out.print(cellList.get(i) + " ");
		}
		System.out.println();
		// Convert Cell's list in operations's list
		for (int i = 0; i < cellList.size()-1; i++) {
			int i1 = Integer.parseInt(cellList.get(i).split("-")[0]);
			int j1 = Integer.parseInt(cellList.get(i).split("-")[1]);
			int i2 = Integer.parseInt(cellList.get(i+1).split("-")[0]);
			int j2 = Integer.parseInt(cellList.get(i+1).split("-")[1]);

			if(i1!=i2){
				if(i1<i2)
					calculatedAction.add(new Action(Action.Type.SOUTH));
				else
					calculatedAction.add(new Action(Action.Type.NORTH));
			}
			if(j1!=j2){
				if(j1<j2)
					calculatedAction.add(new Action(Action.Type.EAST));
				else
					calculatedAction.add(new Action(Action.Type.WEST));
			}
		}
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
		actionList.add(new Action(currAction));
		return currAction;
	}

}
