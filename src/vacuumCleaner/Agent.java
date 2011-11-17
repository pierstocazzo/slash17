package vacuumCleaner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import demo.UndirectedWeightedGraph;


/**
 * Agent function for the vacuum-cleaner environment
 * @see Environment
 */

public class Agent extends AbstractAgent {

	private UndirectedWeightedGraph graph;
	private Floor myWorld;

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
		case MY_NEIGHBOURS:intelligentBehaviour();break;
		case ALL:stupidBehaviour();break;
		}
	}

	private void intelligentBehaviour() {
		if(actionList.size() == 0)
			calculateHC();
		else{

		}
	}

	public int dist(int i1, int j1, int i2, int j2){
		return Math.abs(i1-i2) + Math.abs(j1-j2);
	}
	
	public int dist(String v1, String v2){
		int i1 = Integer.parseInt(v1.split(",")[0]);
		int j1 = Integer.parseInt(v1.split(",")[1]);
		int i2 = Integer.parseInt(v2.split(",")[0]);
		int j2 = Integer.parseInt(v2.split(",")[1]);
		return dist(i1, j1, i2, j2);
	}

	private void calculateHC() {
		graph = new UndirectedWeightedGraph();

		ArrayList<String> dirtyCells = new ArrayList<String>();

		for (int i = 0; i < myWorld.length; i++)
			for (int j = 0; j < myWorld.length; j++)
				if(myWorld.get(i, j) == Square.Type.DIRTY)
						dirtyCells.add(i + "," + j);

		for (int i = 0; i < dirtyCells.size(); i++) {
			for (int j = i+1; j < dirtyCells.size(); j++) {
				int w = dist(dirtyCells.get(i), dirtyCells.get(j));
				graph.addWeightedEdge(dirtyCells.get(i),
										dirtyCells.get(j),
										 w);
			}
		}
		
		System.out.println(graph);
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
