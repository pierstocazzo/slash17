package interfaces;

import vacuumCleaner.AbstractAgent.VisibilityType;

public class ItemCsv {

	String nomeIstanza;
	VisibilityType visibilita;
	int punteggio;
	int num_step;
	String descrizione;
	
	public ItemCsv() {
		nomeIstanza = null;
		visibilita = null;
		punteggio = 0;
		num_step = 0;
		descrizione = null;
	}
	
	public ItemCsv(String n, VisibilityType visibilita, int punteggio, int num_step, String descrizione) {
		this.nomeIstanza = n;
		this.visibilita = visibilita;
		this.punteggio = punteggio;
		this.num_step = num_step;
		this.descrizione = descrizione;
	}
	
	@Override
	public String toString() {
		return nomeIstanza + "," + visibilita + "," + punteggio + "," + num_step + "," 
						+ descrizione;
	}
	
}

