package vacuumCleaner;

/**
 * Describe the possible actions that could be performed by the agent
 *
 */

public class Action {
	
	public enum Type {
		NOOP,
		SUCK,
		NORTH,
		SOUTH,
		EAST,
		WEST
	}
	
	Action.Type type;
	
	/**
	 * 
	 * @param type type of action that could be performed by the agent 
	 * @param sx
	 * @param sy
	 */
	public Action(Action.Type type, int sx, int sy) {
		super();
		this.type = type;
	}
	
	
	/**
	 * 
	 * @param actionType
	 * @return according to the action return the displacement in unit over axis Y
	 */
	static public int xVar(Action.Type actionType){
		switch (actionType){
			case NORTH:return -1;
			case SOUTH:return 1;
		}
		return 0;
	}
	
	/**
	 * 
	 * @param actionType
	 * @return according to the action return the displacement in unit over axis Y
	 */
	static public int yVar(Action.Type actionType){
		switch (actionType){
			case EAST:return 1;
			case WEST:return -1;
		}
		return 0;
	}
}
