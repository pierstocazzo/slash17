package interfaces;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import vacuumCleaner.AbstractAgent;
import vacuumCleaner.AbstractAgent.VisibilityType;
import vacuumCleaner.Agent;
import vacuumCleaner.Environment;
import vacuumCleaner.Environment.Type;
import vacuumCleaner.Floor;
import vacuumCleaner.Square;

import com.csvreader.CsvWriter;

public class Batch {
	AbstractAgent agent;
	Environment env;
	@SuppressWarnings("rawtypes")
	Serializzatore ser;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		ArrayList<ItemCsv> itemcsv = new ArrayList<ItemCsv>();

		VisibilityType[] visibilita = {VisibilityType.ALL, 
				VisibilityType.MY_NEIGHBOURS,
				VisibilityType.MY_CELL};

		Batch batch = new Batch();
		batch.newConfig(5, Type.STATIC, VisibilityType.ALL, 200);
		Floor floor = null;

		Class classEnv = batch.env.getClass();
		Class classSerializzatore = batch.ser.getClass();

		try {
			Method updateEnv = classEnv.getMethod("update");
			Method showEnv = classEnv.getMethod("show");
			Method loadFloor1 = classSerializzatore.getMethod("caricaFile", String.class);

			File folder = new File("instances");
			File[] listaIstanze = folder.listFiles();

			for (VisibilityType v : visibilita) {
				batch.agent.visType = v;
				System.out.println("VISIBILITA " + v);
				for (File s : listaIstanze) {
					if(s.isFile()){
						System.out.println("Istanza " + s);
						ItemCsv item = new ItemCsv();
						item.visibilita = v;
						item.nomeIstanza = s.getName();
						Field f = classEnv.getDeclaredField("floor");
						floor = (Floor) f.get(batch.env);
						floor = (Floor) loadFloor1.invoke(batch.ser, s.getAbsolutePath());
						f.set(batch.env, floor);
						showEnv.invoke(batch.env);

						boolean eccezione = false;
						while (!batch.agent.goalReached() && batch.agent.energy > 0 && !eccezione) {
							try {
								long inizio = System.currentTimeMillis();
								updateEnv.invoke(batch.env);
								long fine = System.currentTimeMillis();
								if (((fine-inizio)/1000) > 5) {
									throw new Exception("Time");
								}
								showEnv.invoke(batch.env);
							} catch (Exception e) {
								eccezione = true;
								item.punteggio = 0;
							}

						}
						if (!eccezione) {
							item.punteggio = batch.env.performanceMeasure();
							item.descrizione = "ok";
						} else {
							item.descrizione = "Error";
						}
						item.num_step = batch.agent.actionList.size(); 
						itemcsv.add(item);
						batch.newConfig(5, Type.STATIC, v, 200);
					}
				}
			}
			generaCsv(itemcsv);
		} catch (SecurityException e2) {
			e2.printStackTrace();
		} catch (NoSuchMethodException e2) {
			e2.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static void generaCsv(ArrayList<ItemCsv> itemCsv) {
		CsvWriter writer = new CsvWriter("csv.csv");
		try {

			for (ItemCsv csv : itemCsv) {
				writer.write(csv.nomeIstanza);
				writer.write(String.valueOf(csv.visibilita));
				writer.write(String.valueOf(csv.punteggio));
				writer.write(String.valueOf(csv.num_step));
				writer.write(String.valueOf(csv.descrizione));
				writer.endRecord();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void newConfig(int newSize, Environment.Type envType, VisibilityType visType, int energy) {
		agent = new Agent(0, 0, visType, energy);
		env = new Environment(newSize, newSize, agent, Environment.Type.STATIC);
		ser = new Serializzatore();
		env.floor = new Floor(newSize, newSize, Square.Type.CLEAN);
		env.floor.set(agent.x, agent.y, Square.Type.CLEAN);
		env.agent = agent;
		env.type = envType;
		env.floor.initialDirt = 10;
	}
}