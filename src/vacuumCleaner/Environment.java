package vacuumCleaner;

import java.util.Random;

/**
 * 
 * This class implement an environment that interact with the vacuum-cleaner agent.
 * 
 * @see AbstractAgent
 * @see Agent
 * 
 *
 */
public class Environment {

	
	public enum Type {
		STATIC,
		DYNAMIC
	}
	
	public AbstractAgent agent;
	public Action.Type currAction;
	public Type type;
	public Floor floor;
	
	public Environment(int length, int width, AbstractAgent agent, Environment.Type dynType){
		this.floor = new Floor(length, width, Square.Type.CLEAN);
		this.agent = agent;
		this.type = dynType;
	}
	
	/**
	 * 
	 *  Set the perceptions according with the agent visibility.
	 *
	 * 		If agent's visibility is MY_CELL, just the state of cell where the agent is located,
	 * 		will be add to the perception.
	 *		If agent's visibility is MY_NEIGHBOURS, the state of cell where the agent is located
	 *		and the 8 cells in its Moore neighborhood will be add to the perception.
	 *		If agent's visibility is ALL, all the cells will be add to the perception.
	 * 	
	 * 		
	 * 
	 * @return  the current perception 
	 * 
	 * @see Perception
	 */
	public Perception getPerceptions() {
		/* create a perception with a floor of unknown state */
		Perception perception = new Perception(new Floor(floor.width, floor.length, Square.Type.UNKNOWN));
		/* then add informations according to the agent visibility */
		switch (agent.visType) {
			case MY_CELL:
				/* create a perception with a floor of unknown state */
				perception.floor.set(agent.x, agent.y, floor.get(agent.x, agent.y));
				break;
				
			case MY_NEIGHBOURS:
				/* add informations according to the agent visibility.*/
				perception.floor.set(agent.x, agent.y, floor.get(agent.x, agent.y));
				perception.floor.set(agent.x-1, agent.y, floor.get(agent.x-1, agent.y));
				perception.floor.set(agent.x+1, agent.y, floor.get(agent.x+1, agent.y));
				perception.floor.set(agent.x, agent.y-1, floor.get(agent.x, agent.y-1));
				perception.floor.set(agent.x-1, agent.y-1, floor.get(agent.x-1, agent.y-1));
				perception.floor.set(agent.x+1, agent.y-1, floor.get(agent.x+1, agent.y-1));
				perception.floor.set(agent.x, agent.y+1, floor.get(agent.x, agent.y+1));
				perception.floor.set(agent.x-1, agent.y+1, floor.get(agent.x-1, agent.y+1));
				perception.floor.set(agent.x+1, agent.y+1, floor.get(agent.x+1, agent.y+1));
				break;
			case ALL:
				/* add informations about all the squares */
				for (int i = 0; i < floor.length; i++)
		            for (int j = 0; j < floor.width; j++)
		                perception.floor.set(i,j,floor.get(i, j));
				break;
		}
		return perception;
	}

	/**
	 * Update the environment state according to the action performed by the agent
	 */

	public void update() {
		agent.perceives(getPerceptions());
		agent.update();
		getAction(agent.action());
		System.out.println("Action received: " + currAction);
		
		if(type == Environment.Type.DYNAMIC){
			Random randomGen = new Random();
			int random = Math.abs(randomGen.nextInt()) % 5;
			if(random == 1)
				floor.generateObject(1, 0);
		}
		
		if(currAction == Action.Type.SUCK){
			System.out.println("MY CELL BEFORE: " + agent.x + "," + agent.y + ": " + floor.get(agent.x,agent.y));
			floor.set(agent.x, agent.y, Square.Type.CLEAN);
			System.out.println("MY CELL AFTER: " + agent.x + "," + agent.y + ": " + floor.get(agent.x,agent.y));
		}
		if(currAction == Action.Type.NORTH && agent.x-1>=0 && !floor.obstacle(agent.x-1,agent.y))
			agent.x--;
		if(currAction == Action.Type.SOUTH && agent.x+1<floor.length && !floor.obstacle(agent.x+1,agent.y))
			agent.x++;
		if(currAction == Action.Type.EAST && agent.y+1<floor.width && !floor.obstacle(agent.x,agent.y+1))
			agent.y++;
		if(currAction == Action.Type.WEST && agent.y-1>=0 && !floor.obstacle(agent.x,agent.y-1))
			agent.y--;
	}

	public void getAction(Action.Type action) {
		currAction = action;
	}
	
	public int performanceMeasure(){
		int homeDistance = (int) Math.sqrt(agent.x*agent.x + agent.y*agent.y);
		int numOp = agent.actionList.size();
		double currDirt = floor.dirtySquares();
		double cleanedByAgent = floor.initialDirt - floor.dirtySquares();
		int sparsity = floor.getSparsity();
		
//		System.out.println("Sparsity: " + sparsity);
		/*
		 * Performance Measure
		 * 
		 * 2^(cleanedByAgent/(currDirt+numOp+1)) - n1*homeDistance - n2*sparsity
		 * 
		 * cleanedByAgent = dirtyCell0 - dirtyCellT
		 */
		return (int) (100*Math.pow(2,cleanedByAgent/(currDirt+numOp+1)) - homeDistance - sparsity);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<floor.length; i++){
			for(int j=0; j<floor.width; j++)
				if(agent.x == i && agent.y == j)
					sb.append("[[=]] ");
				else
					if(floor.get(i, j) == Square.Type.DIRTY)
						sb.append("XXXXX ");
				else if(floor.get(i, j) == Square.Type.OBSTACLE)
					sb.append("OOOOO ");
				else
					sb.append("----- ");
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public void show(){
		System.out.println(this);
	}
}
