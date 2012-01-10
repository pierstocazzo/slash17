package planning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import env.Env;

public class PlanGenerator {
	Plan plan;

	Env env;
	
	// goals
	String mealReady = "mealReady";
	String suitUp = "suitUp";
	String tableReady = "tableReady";
	String flowersTaken = "flowersTaken";
	String waitRenata = "waitRenata";

	public PlanGenerator(Env env){
		this.env = env;
	}

	public String executePlan(){
		createInitDL();
		
		if(!env.isMealReady()){
			return executeTemplatePlan(mealReady);
		}
		if(!env.isSuitUp()){
			return executeTemplatePlan(suitUp);
		}
		if(!env.isTableReady()){
			return executeTemplatePlan(tableReady);
		}
		if(!env.isFlowerTaken()){
			return executeTemplatePlan(flowersTaken);
		}
		if(!env.isWaitRenata()){
			return executeTemplatePlan(waitRenata);
		}
		return null;
	}
	
	private String executeTemplatePlan(String goal) {
		String initially = createInitiallyString();

		String fileName = "";
		if (goal == mealReady)
			fileName = "1prepareMeal.plan";
		else if (goal == suitUp)
			fileName = "2suitUp.plan";
		else if (goal == tableReady)
			fileName = "3prepareTable.plan";
		else if (goal == flowersTaken)
			fileName = "4takeFlowers.plan";
		else if (goal == waitRenata)
			fileName = "5waitRenata.plan";
		
		String planDesc = readFileAsString("k/input/" + fileName);

		planDesc = planDesc.concat("initially:" + "\n\t" + initially + "\n\n");
		planDesc = planDesc.concat("goal:" +"\n\t" + goal + " ?" +"\n\n");

		createFileFromString("k/" + fileName, planDesc);

		String cmd = "", out = "";
		int depth = 1, bound = env.posForRoom*(env.rooms-1);
		boolean planFound = false;
		
		while(!planFound && depth<=bound){
			cmd = "./k/dlv -FP -FPsec -silent ./k/init.dl ./k/" + fileName + " -planlength=" + depth;
			out = executeCommand(cmd);
			if(!out.isEmpty())
				planFound = true;
			depth++;
		}
		
		return out;
	}

	private void createInitDL() {
		String init = readFileAsString("k/input/init.dl");
		//System.out.println("init file\n\n" + init);
		for (int i = 0; i < env.rooms-1; i++) 
			init = init.concat("doorDown(" + (i+1) + "," + (env.doorsPosition[i]+1) + ").\n");
	
		//System.out.println("after init file\n\n" + init);
		createFileFromString("k/init.dl", init);
	}

	private String createInitiallyString() {
		String initially = "at(ciccio," + (env.player_i()+1) + "," + (env.player_j()+1) + ").\n";
		for(int i=0; i<env.rooms; i++) {
			for (int j = 0; j < env.posForRoom; j++) {
				if(env.matrix[i][j] == Env.MEAL)
					initially += "at(dinner," + (i+1) + "," + (j+1) + ").\n";
				if(env.matrix[i][j] == Env.FLOWERS)
					initially += "at(flowers," + (i+1) + "," + (j+1) + ").\n";
				if(env.matrix[i][j] == Env.SUIT)
					initially += "at(smoking," + (i+1) + "," + (j+1) + ").\n";
				if(env.matrix[i][j] == Env.TABLE)
					initially += "at(table," + (i+1) + "," + (j+1) + ").\n";
			}
		}
		if (env.isMealReady())
			initially += "mealReady.\n";
		if (env.isFlowerTaken())
			initially += "flowersTaken.\n";
		if (env.isSuitUp())
			initially += "suitUp.\n";
		if (env.isTableReady())
			initially += "tableReady.\n";
		if (env.isWaitRenata())
			initially += "waitRenata.\n";
				
		return initially;
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
				//System.out.println(inputLine);
				res += inputLine;
			}
			in.close();

		}//try
		catch (Exception e) {
			//System.out.println(e);
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
			FileWriter fstream = new FileWriter(filePath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(fileContent);
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
