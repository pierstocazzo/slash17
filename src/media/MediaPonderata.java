package media;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MediaPonderata {
	
	String input;
	BufferedReader r;
	int somma;
	int cretitiTotali;
	int crediti;
	
	public static void main(String[] args) throws Exception {
		MediaPonderata m = new MediaPonderata();
		m.calcola();
	}

	private void calcola() throws Exception {
		r = new BufferedReader( new InputStreamReader( System.in ));
		
		System.out.println("Inserisci il numero di crediti");
		input = r.readLine();
		while( !input.equals("-1") ) {
			while( !input.matches("[3-9]") ) {
				System.out.println("Inserisci il numero di crediti corretto");
				input = r.readLine();
			}
			crediti = Integer.parseInt( input );
			System.out.println("Inserisci il voto");
			input = r.readLine();
			while( !input.matches("(1[8|9]|2[0-9]|30|30L)") ) {
				System.out.println("fuck");
				input = r.readLine();
			}
			if( input.equals("30L") )
				somma = somma + 31*crediti;
			else
				somma = somma + Integer.parseInt(input)*crediti;
			
			cretitiTotali = cretitiTotali + crediti;
			
			System.out.println("Inserisci il numero di crediti");
			input = r.readLine();
		}
		
		System.out.println("\nTotale crediti maturati: " + cretitiTotali);
		double media = (double) somma / cretitiTotali;
		System.out.println("Media pesata sui crediti: " + media );
		
		double votoFinale = 110 * media / 30;
		
		System.out.println("Voto finale calcolato: " + votoFinale );
	}
}
