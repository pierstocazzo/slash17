package logica;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FactoryImprevisti {

	static public ArrayList<Imprevisto> imprevisti;

	public static void main(String[] args) {
		acquisizioneImprevisti();
	}
	
	public static void acquisizioneImprevisti() {
		try {
			InputStream is = FactoryImprevisti.class.getClassLoader().getResource("text/Imprevisti.txt").openStream();
			BufferedReader fbr = new BufferedReader(new InputStreamReader(is));
			imprevisti = new ArrayList<Imprevisto>();

			String s = fbr.readLine();

			while (s != null) {
				String[] ss = s.split("_");
				Imprevisto imprevisto;
				String testoPiccolo = ss[0];
				String testoGrande = ss[1];
				if (testoGrande.contains("TURNO")) {
					imprevisto = new Imprevisto(testoPiccolo, testoGrande, true);
				} else {
					int begin = testoGrande.lastIndexOf("HAI PERSO ") + 10;
					int end = testoGrande.lastIndexOf(" KG");
					int qnt = -Integer.parseInt(testoGrande.substring(begin, end));
					begin = testoGrande.lastIndexOf(' ')+1;
					String rifiuto = testoGrande.substring(begin);
					imprevisto = new Imprevisto(testoPiccolo, testoGrande, qnt, rifiuto);
				}
				imprevisti.add(imprevisto);
				s = fbr.readLine();
			}

			fbr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Imprevisto dammiImprevisto() {
		int k = (int) (Math.random() * imprevisti.size());
		return imprevisti.get(k);
	}
}
