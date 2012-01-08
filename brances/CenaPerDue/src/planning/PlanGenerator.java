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

	public PlanGenerator(Env env){
		this.env = env;
	}

	public String generatePlan(){
		if(!env.isMealReady()){
			return generatePlanForMealReady();
		}
		if(!env.isSuitUp()){
			return generatePlanForSuitUp();
		}
		if(!env.isTableReady()){
			return generatePlanForTableReady();
		}
		if(!env.isFlowerTaken()){
			return generatePlanForFlowerTaken();
		}
		if(!env.isWaitRenata()){
			return generatePlanForWaitRenata();
		}
		return null;
	}

	private String generatePlanForWaitRenata() {
		// TODO Auto-generated method stub
		return null;
	}

	private String generatePlanForFlowerTaken() {
		// TODO Auto-generated method stub
		return null;
	}

	private String generatePlanForTableReady() {
		// TODO Auto-generated method stub
		return null;
	}

	private String generatePlanForSuitUp() {
		// TODO Auto-generated method stub
		return null;
	}

	private String generatePlanForMealReady() {
		//init.dl
		String init = readFileAsString("k/input/init.dl");
		//System.out.println("init file\n\n" + init);
		
		for (int i = 0; i < env.rooms-1; i++) 
			init = init.concat("doorDown(" + (i+1) + "," + (env.doorsPosition[i]+1) + ").\n");
		//System.out.println("after init file\n\n" + init);
		createFileFromString("k/init.dl", init);
		
		// va reso piu' efficiente
		// possiamo provare se esiste un piano di 1 azione,
		// se non esiste proviamo con lunghezza 2 e così via fino ad un massimo ragionevole
		
		//prepareMeal.plan
		String ciccioAtom = "at(ciccio," + (env.player_i()+1) + "," + (env.player_j()+1) + ")";
		String dinnerAtom = "";
		for(int i=0; i<env.rooms; i++)
			for (int j = 0; j < env.posForRoom; j++) 
				if(env.matrix[i][j] == Env.MEAL)
					dinnerAtom = "at(dinner," + (i+1) + "," + (j+1) + ")";

		String planDesc = readFileAsString("k/input/1prepareMeal.plan");

		planDesc = planDesc.concat("initially:" + "\n\t" + ciccioAtom + ". " + dinnerAtom + ".\n\n");
		planDesc = planDesc.concat("goal:" +"\n\t" + "mealReady ?" +"\n\n");

		createFileFromString("k/1prepareMeal.plan", planDesc);

		String cmd = "", out = "";
		int depth = 1, bound = env.posForRoom*(env.rooms-1);
		boolean planFound = false;
		
		while(!planFound && depth<=bound){
			cmd = "./k/dlv -FP -FPsec -silent ./k/init.dl ./k/1prepareMeal.plan -planlength=" + depth;
			//System.out.println("COMMAND: " + cmd);
			out = executeCommand(cmd);
//			System.out.println(i + "---" + out + "---");
			if(!out.isEmpty())
				planFound = true;
			depth++;
		}
		return out;
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
