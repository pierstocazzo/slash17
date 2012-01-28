package planning;

import java.util.LinkedList;

public class Plan {
	LinkedList<Action> actions;

	public Plan(String out) {
		actions = new LinkedList<Action>();
		// out = PLAN: move(ciccio,right):1; move(ciccio,down):2; move(ciccio,left):3; move(ciccio,down):4; move(ciccio,right):5; move(ciccio,right):6; prepareMeal:1; (no action); (no action); (no action)  COST: 22PLAN: move(ciccio,right):1; move(ciccio,down):2; move(ciccio,left):3; move(ciccio,down):4; move(ciccio,right):5; move(ciccio,right):6; (no action); prepareMeal:1; (no action); (no action)
		String[] ss = out.split("COST:")[0].split(" ");
		for (int i = 0; i < ss.length; i++) {
			if (ss[i].contains("move")) {
				String mov = ss[i].split(",")[1].split("\\)")[0];
				if (mov.equals("left"))
					actions.add(Action.MOVE_LEFT);
				else if (mov.equals("right"))
					actions.add(Action.MOVE_RIGHT);
				else if (mov.equals("up"))
					actions.add(Action.MOVE_UP);
				else if (mov.equals("down"))
					actions.add(Action.MOVE_DOWN);
			} else if (ss[i].contains("prepareMeal")) {
				actions.add(Action.PREPARE_MEAL);
			} else if (ss[i].contains("dress")) {
				actions.add(Action.SUIT_UP);
			} else if (ss[i].contains("prepareTable")) {
				actions.add(Action.PREPARE_TABLE);
			} else if (ss[i].contains("takeFlowers")) {
				actions.add(Action.TAKE_FLOWERS);
			} else if (ss[i].contains("stopCiccio")) {
				actions.add(Action.WAIT_RENATA);
			} else if (ss[i].contains("takeMeal")) {
				actions.add(Action.TAKE_MEAL);
			}
		}
		
	}

	public LinkedList<Action> getActions() {
		return actions;
	}

	public boolean isEmpty() {
		return actions.isEmpty();
	}
	
	public void reset(){
		actions.clear();
	}
}
