package test;

/* Classe che implementa l'interfaccia Visitable e che rappresenta stringhe
 * visitabili */

public class VisitableString implements Visitable {
	
	private String value;
	
	// CREATORE:
	public VisitableString(String string){
		value = string;
	}
	
	// Restituisce al chiamante il valore della stringa:
	public String getString(){
		return value;
	}
	
	/* IMPLEMENTAZIONE DEL METODO accept() DELL'INTERFACCIA CHE PERMETTE DI
	 * VISITARE L'OGGETTO IN QUESTIONE: come parametro di input riceve il
	 * riferimento ad un generico oggetto visitor */
	public void accept(Visitor visitor){
		/* Invia al visitor ricevuto nel parametro il messaggio di visitare
		 * l'oggetto Visitable corrente */
		visitor.visit(this);
	}

}