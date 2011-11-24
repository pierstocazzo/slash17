package logica;

public class Jolly {
	int quantita;
	String rifiuto;
	boolean lancioExtra;
	String testoPiccolo;
	String testoGrande;
	
	public String getTestoPiccolo() {
		return testoPiccolo;
	}

	public void setTestoPiccolo(String testoPiccolo) {
		this.testoPiccolo = testoPiccolo;
	}

	public String getTestoGrande() {
		return testoGrande;
	}

	public void setTestoGrande(String testoGrande) {
		this.testoGrande = testoGrande;
	}
		
	public Jolly(String testoPiccolo, String testoGrande, int quantita, String rifiuto) {
		this.quantita = quantita;
		this.rifiuto = rifiuto;
		this.testoPiccolo = testoPiccolo;
		this.testoGrande = testoGrande;
		this.lancioExtra = false;
	}

	public Jolly(String testoPiccolo, String testoGrande, boolean lancioExtra) {
		this.lancioExtra = lancioExtra;
		this.testoPiccolo = testoPiccolo;
		this.testoGrande = testoGrande;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int bonus) {
		this.quantita = bonus;
	}

	public String getRifiuto() {
		return rifiuto;
	}

	public void setRifiuto(String rifiuto) {
		this.rifiuto = rifiuto;
	}

	public boolean isLancioExtra() {
		return lancioExtra;
	}

	public void setLancioExtra(boolean lancioExtra) {
		this.lancioExtra = lancioExtra;
	}

}
