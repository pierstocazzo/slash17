package test;

/* Interfaccia che rappresenta generici oggetti visitabili che verranno poi
 * raggruppati in collezioni visitabili */

public interface Visitable {
	/* Metodo che consente ad un visitatore concreto di essere "accettato */
	public void accept(Visitor visitor);
}