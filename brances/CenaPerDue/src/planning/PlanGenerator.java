package planning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

import env.Ciccio;
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
		String ciccioAtom = "at(ciccio," + (env.player_i()+1) + "," + (env.player_j()+1) + ")";
		String dinnerAtom = "";
		for(int i=0; i<env.rooms; i++)
			for (int j = 0; j < env.posForRoom; j++) 
				if(env.matrix[i][j] == Env.MEAL)
					dinnerAtom = "at(dinner," + (i+1) + "," + (j+1) + ")";
//		System.out.println("Ciccio: " + ciccioAtom);
//		System.out.println("Dinner: " + dinnerAtom);

		String planDesc = readFileAsString("k/input/1prepareMeal.plan");

		planDesc = planDesc.concat("initially:" + "\n\t" + ciccioAtom + ". " + dinnerAtom + ".\n\n");
		planDesc = planDesc.concat("goal:" +"\n\t" + "mealReady ?" +"\n\n");

		createFileFromString("k/1prepareMeal.plan", planDesc);

		String cmd = "k/dlv -silent -FP -FPsec k/init.dl k/1prepareMeal.plan -planlenght=10";
		// dlv init.dl 1prepareMeal.plan -silent -FP -FPsec
		
		String out = executeCommand(cmd);
		System.out.println(out);
	}

	private String executeCommand(String cmd) {
		String res = "";
		try {
			Runtime r = Runtime.getRuntime();
			Process p = r.exec(cmd);

			BufferedReader in = new BufferedReader(new
					InputStreamReader(p.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				res += inputLine;
			}
			in.close();

		}//try
		catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}

	private static String readFileAsString(String filePath) {
		byte[] buffer = new byte[(int) new File(filePath).length()];
		FileInputStream f;
		try {
			f = new FileInputStream(filePath);
			f.read(buffer);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return new String(buffer);
	}

	private static void createFileFromString(String filePath, String fileContent) {
		try{
			// Create file 
			FileWriter fstream = new FileWriter("k/1prepareMeal.plan");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(fileContent);
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
