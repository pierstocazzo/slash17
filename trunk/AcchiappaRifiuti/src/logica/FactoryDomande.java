/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FactoryDomande {

	static public ArrayList<Domanda> domande;
	static String r1, r2, r3, r4;
	static int codR;
	static String cod, risp, val;
	static StringTokenizer st;
	static boolean correct;
	static int v, t;
	static BufferedReader fbr;
	static int numDomande = 0;
	static ArrayList<Integer> domandeFatte = new ArrayList<Integer>();

	public static String acquisizioneDomande() {
		try {
			InputStream is = FactoryDomande.class.getClassLoader().getResource("text/domande.txt").openStream();
			fbr = new BufferedReader(new InputStreamReader(is));
			domande = new ArrayList<Domanda>();

			String s = fbr.readLine();

			while (s != null) {
				s = s.split("_")[1];
				domande.add(new Domanda(s));
				s = fbr.readLine();
			}

			numDomande = domande.size();

			fbr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		acquisizioneRisposte();
		return " Stuttura Domande generata";
	}

	public static String acquisizioneRisposte() {
		try {
			InputStream is = FactoryDomande.class.getClassLoader().getResourceAsStream("text/Risposte.txt");
			fbr = new BufferedReader(new InputStreamReader(is));

			for (int i = 0; i < numDomande; i++) {
				r1 = fbr.readLine();
				st = new StringTokenizer(r1, "_");
				cod = st.nextToken();
				val = st.nextToken();
				risp = st.nextToken();
				correct = val.equals("t") ? true : false;

				domande.get(i).setRispostaA(new Risposta(risp, i, correct));
				if (correct) {
					domande.get(i).setCorretta((new Risposta(risp, i, correct)));
				}

				r2 = fbr.readLine();
				st = new StringTokenizer(r2, "_");
				cod = st.nextToken();
				val = st.nextToken();
				risp = st.nextToken();
				correct = val.equals("t") ? true : false;

				domande.get(i).setRispostaB(new Risposta(risp, i, correct));
				if (correct) {
					domande.get(i).setCorretta((new Risposta(risp, i, correct)));
				}

				r3 = fbr.readLine();
				st = new StringTokenizer(r3, "_");
				cod = st.nextToken();
				val = st.nextToken();
				risp = st.nextToken();
				correct = val.equals("t") ? true : false;

				domande.get(i).setRispostaC(new Risposta(risp, i, correct));
				if (correct) {
					domande.get(i).setCorretta((new Risposta(risp, i, correct)));
				}

				r4 = fbr.readLine();
				st = new StringTokenizer(r4, "_");
				cod = st.nextToken();
				val = st.nextToken();
				risp = st.nextToken();
				correct = val.equals("t") ? true : false;

				domande.get(i).setRispostaD(new Risposta(risp, i, correct));
				if (correct) {
					domande.get(i).setCorretta((new Risposta(risp, i, correct)));
				}
				
				if (domande.get(i).getCorretta() == null) {
					System.out.println("domanda " + i);
				}
			}
			fbr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return " Stuttura Risposte generata e aggiunte alle rispettive domande";
	}

	public static Domanda dammiDomanda() {
		if (domandeFatte.size() == domande.size()) {
			domandeFatte.clear();
		}

		int k = (int) (Math.random() * domande.size());
		while (domandeFatte.contains(k)) {
			k = (int) (Math.random() * domande.size());
		}
		domandeFatte.add(k);
		return domande.get(k);
	}
}
