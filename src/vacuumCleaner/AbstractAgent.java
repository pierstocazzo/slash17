package vacuumCleaner;

import java.util.ArrayList;

/**
 * Agent abstraction;
 * every agent has to implement those methods
 */
public abstract class AbstractAgent {
	
	
	public enum VisibilityType {
		MY_CELL,
		MY_NEIGHBOURS,
		ALL
	}
	
	Perception perception;
	public int x;
	public int y;
	public int opBound;
	public VisibilityType visType;
	int squaresCleanedByMe = 0;
	boolean goalReached = false;
	Action.Type currAction;
	public ArrayList<Action> actionList;
	
	public AbstractAgent(int x, int y, VisibilityType visType, int opBound){
		this.x = x;
		this.y = y;
		this.visType = visType;
		this.opBound = opBound;
		currAction = Action.Type.NOOP;
		actionList = new ArrayList<Action>();
	}
	
	/**
	 * @return true if the agent has reached the goal
	 */
	public abstract boolean goalReached();

	/**
	 * Make the agent perceives from environment
	 * @param perception current perception from the environment
	 */
	public abstract void perceives(Perception perception);

	/**
	 * Move the agent according with the visibility
	 */
	public abstract void update();
	
	/**
	 * Print the performed actions by the agent
	 */
	public abstract void showActions();

	/** 
	 * Perform an action
	 * @return the action the agent wants to perform
	 */
	public abstract Action.Type action();

}
