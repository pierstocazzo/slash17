package logica;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FactoryJolly {

	static public ArrayList<Jolly> jolly;

	public static void main(String[] args) {
		acquisizioneJolly();
	}
	
	public static void acquisizioneJolly() {
		try {
			InputStream is = FactoryJolly.class.getClassLoader().getResource("text/Jolly.txt").openStream();
			BufferedReader fbr = new BufferedReader(new InputStreamReader(is));
			jolly = new ArrayList<Jolly>();

			String s = fbr.readLine();

			while (s != null) {
				String[] ss = s.split("_");
				Jolly imprevisto;
				String testoPiccolo = ss[0];
				String testoGrande = ss[1];
				if (testoGrande.contains("dado")) {
					imprevisto = new Jolly(testoPiccolo, testoGrande, true);
				} else {
					int begin = testoGrande.lastIndexOf("ACCHIAPPI ") + 10;
					int end = testoGrande.lastIndexOf(" kg");
					int qnt = Integer.parseInt(testoGrande.substring(begin, end));
					begin = testoGrande.lastIndexOf(' ')+1;
					String rifiuto = testoGrande.substring(begin);
					imprevisto = new Jolly(testoPiccolo, testoGrande, qnt, rifiuto);
				}
				jolly.add(imprevisto);
				s = fbr.readLine();
			}

			fbr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Jolly dammiJolly() {
		int k = (int) (Math.random() * jolly.size());
		return jolly.get(k);
	}
}
