package planning;

import env.Ciccio;
import env.Env;

public class PlanGenerator {
	Plan plan;
	
	Env env;
	Ciccio ciccio;
	
	public PlanGenerator(Env env, Ciccio ciccio){
		this.plan = new Plan();
		this.env = env;
		this.ciccio = ciccio;
	}
	
	public void generatePlan(){
		if(!env.isMealReady()){
			//generate plan
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
}
