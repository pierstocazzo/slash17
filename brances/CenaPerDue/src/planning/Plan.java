package planning;

import java.util.LinkedList;

public class Plan {
	LinkedList<Action> actions;

	public Plan() {
		actions = new LinkedList<Action>();
	}

	public LinkedList<Action> getActions() {
		return actions;
	}
}
