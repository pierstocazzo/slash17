package logica;

import java.io.InputStream;
import java.util.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

/**
 * 
 * @authors Diana e Paola (Grid&Cloud)
 * 
 * 
 */
public class GestioneCaselle {

	String id;
	String x;
	String y;
	String text;
	String down;
	String up;
	String laterale;
	String laterale1;
	String laterale2;
	String laterale3;
	String laterale4;
	String laterale5;
	String laterale6;
	protected static ArrayList<Casella> caselle;

	public GestioneCaselle() {
		caselle = new ArrayList<Casella>();
		this.CaricamentoFile();
	}

	public void CaricamentoFile() {
		DocumentBuilderFactory factory;
		DocumentBuilder parser;
		Document document;
		try {
			factory = DocumentBuilderFactory.newInstance();
			parser = factory.newDocumentBuilder();
			
			InputStream is = getClass().getClassLoader().getResource("text/caselle.xml").openStream();
			document = parser.parse(is);
			handleDocument(document);
		} catch (Exception ex) {
			System.out.println("Errore." + ex);
			ex.printStackTrace();
		}
	}

	public void handleDocument(Document document) {
		/* Tutti i nodi contenuti in document che si chiamano "NodoCella" */

		NodeList cas = document.getElementsByTagName("NodoCella");
		Element casellona = (Element) cas.item(0);

		id = casellona.getElementsByTagName("id").item(0).getTextContent();
		x = casellona.getElementsByTagName("XCOR").item(0).getTextContent();
		y = casellona.getElementsByTagName("YCOR").item(0).getTextContent();
		text = casellona.getElementsByTagName("text").item(0).getTextContent();
		down = casellona.getElementsByTagName("down").item(0).getTextContent();
		up = casellona.getElementsByTagName("up").item(0).getTextContent();
		laterale = casellona.getElementsByTagName("laterale").item(0).getTextContent();
		laterale1 = casellona.getElementsByTagName("laterale1").item(0).getTextContent();
		laterale2 = casellona.getElementsByTagName("laterale2").item(0).getTextContent();
		laterale3 = casellona.getElementsByTagName("laterale3").item(0).getTextContent();
		laterale4 = casellona.getElementsByTagName("laterale4").item(0).getTextContent();
		laterale5 = casellona.getElementsByTagName("laterale5").item(0).getTextContent();
		laterale6 = casellona.getElementsByTagName("laterale6").item(0).getTextContent();

		CasellaStart casella_start = new CasellaStart(id, text, x, y, down, up, laterale, laterale1, laterale2, laterale3, laterale4, laterale5, laterale6);

		GestioneCaselle.caselle.add(casella_start);

		for (int i = 0; i < cas.getLength(); i++) {
			Element casella = (Element) cas.item(i);

			id = casella.getElementsByTagName("id").item(0).getTextContent();
			x = casella.getElementsByTagName("XCOR").item(0).getTextContent();
			y = casella.getElementsByTagName("YCOR").item(0).getTextContent();
			text = casella.getElementsByTagName("text").item(0).getTextContent();
			down = casella.getElementsByTagName("down").item(0).getTextContent();
			up = casella.getElementsByTagName("up").item(0).getTextContent();
			laterale = casella.getElementsByTagName("laterale").item(0).getTextContent();

			Casella nuova_casella = new Casella(id, text, x, y, down, up, laterale);

			GestioneCaselle.caselle.add(nuova_casella);
		}
		// this.caselle.remove(1);

		casella_start.setLaterali();
	}

	public static ArrayList<Casella> getCaselle() {
		return caselle;
	}

	public void collega() {
		for (int i = 1; i < caselle.size(); i++) {
			String down = caselle.get(i).down;

			String up = caselle.get(i).up;
			String lat = caselle.get(i).laterale;
			caselle.get(i).setProssimaCasellaSensoAntiOrario(findCasella(down));

			caselle.get(i).setProssimaCasellaSensoOrario(findCasella(up));
			Casella c;
			if (lat.isEmpty())
				c = null;
			else
				c = findCasella(lat);
			caselle.get(i).setCasellaLaterale(c); // null se non è un bivio
		}
	}

	public static Casella findCasella(String d) {
		for (int i = 0; i < caselle.size(); i++) {
			if (caselle.get(i).id.equalsIgnoreCase(d)) {
				return caselle.get(i);
			}
		}
		return null;
	}
	//
	// public static void main(String[] args) {
	//
	// GestioneCaselle gc = new GestioneCaselle();
	// gc.collega();
	// Casella c = gc.findCasella("-1");
	// for (int i = 0; i < gc.caselle.size(); i++) {
	//
	// System.out.println(gc.caselle.get(i).toString());
	// }
	// System.out.println(c);
	// System.out.println(c.getX());
	// System.out.println(c.getY());
	// System.out.println("--------------------------------------------------");
	//
	//
	//
	// }
}
