package planning;

import env.Env;

public class PlanGenerator {
	Plan plan;
	
	Env env;
	
	public PlanGenerator(Env env){
		this.plan = new Plan();
		this.env = env;
	}
	
	public void generatePlan(){
		if(!env.isMealReady()){
			generatePlanPrepareMeal();
			return;
		}
		if(!env.isSuitUp()){
			//generate plan
			return;
		}
		if(!env.isTableReady()){
			//generate plan
			return;
		}
		if(!env.isFlowerTaken()){
			//generate plan
			return;
		}
		if(!env.isWaitRenata()){
			//generate plan
			return;
		}
	}

	private void generatePlanPrepareMeal() {
		String ciccioAtom = "at(ciccio," + env.player_i() + "," + env.player_j() + ")";
		String dinnerAtom = "";
		for(int i=0; i<env.rooms; i++)
			for (int j = 0; j < env.posForRoom; j++) 
				dinnerAtom = "at(dinner," + i + "," + j + ")";
		System.out.println("Ciccio: " + ciccioAtom);
		System.out.println("Dinner: " + dinnerAtom);
	}
}
