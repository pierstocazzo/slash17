package test;

import java.io.*;

class Frasi {
	public static void main(String args[]) throws Exception {
		// nome del file in cui scrivere
		String fileName = "frasi.txt";
		// file il cui scrivere
		File outFile = new File(fileName);
		// reader per leggere da standard input
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		// writer per scrivere sul file
		PrintWriter out = new PrintWriter(outFile);
		// array di 5 stringhe, ci metteremo le 5 frasi da stampare
		String frasi[] = new String[5];
		// una frase 
		String frase;
		
		// ciclo principale, ci aspettiamo 5 frasi
		for( int i = 0; i < 5; i++ ) {
			// diciamo all'utente di inserire la frase
			System.out.println("Inserisci la " + (i+1) + "° frase:");
			// leggiamo quanto ha scritto e salviamolo nella variabile frase
			frase = in.readLine();
			// finchè la frase scritta dall'utente è formata da meno di 30 caratteri
			// diciamogli di riscriverne una corretta e rileggiamo
			while( frase.length() < 30 ) {
				System.err.println("Inserisci una frase di almeno 30 caratteri");
				frase = in.readLine();
			}
			// quando la frase sarà maggiore di 30 caratteri, salviamola nel 
			// nostro array di frasi
			frasi[i] = frase;
			
			out.println(frase);
		}
		
		// dopo questo ciclo l'array frasi sarà composto da 5 frasi di lunghezza 
		// almeno 30 caratteri, e saranno nell'ordine in cui sono state scritte
		
		// chiudiamo gli stream di input e output
		in.close();
		out.close();
		
		// scorriamo il nostro array di frasi e per ognuna stampiamone le informazioni
		for( int i = 0; i < 5; i++ ) {
			// otteniamo la frase in questa posizione
			frase = frasi[i];
			// dividiamo la frase nelle sue parole e contiamole
			// si "splitta" (divide) la frase in base al carattere " " (spazio)
			// "ciao come va?" diventa quindi un array di 3 stringhe: {"ciao", "come", "va?"}
			String[] parole = frase.split(" ");
			// il campo "length" di un array è il numero di elementi che questo contiene
			// nel nostro caso sarà quindi il numero di parole contenute nella frase
			int nParole = parole.length;
			// stampiamo le informazioni sulla frase
			System.out.println( "La " + (i+1) + "° frase è: '" + frase + "' " +
					"- numero di parole: " + nParole );
		}
	}
}
