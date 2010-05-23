package test;

/* Classe concreta che implementa l'interfaccia Visitor e che realizza un oggetto
 * visitatore di oggetti di tipo Collection o di tipo VisitableString o di tipo
 * VisitableFloat */

import java.util.Collection;
import java.util.Iterator;

public class WatcherVisitor implements Visitor {
	
	/* Metodo che visita una collezione di oggetti di tipo Collection */
	public void visit(Collection collection){
		// Crea un iteratore sulla collezione ricevuta come parametro di input
		Iterator iterator = collection.iterator();
		
		while(iterator.hasNext()){	// Finchè ci sono ancora elementi da visitare
			Object o = iterator.next();	// Mette l'elemento corrente in o
			
			if(o instanceof Visitable)		// Se o è un oggetto visitabile
				/* o da Object viene castato a generico Visitable e su di esso
				 * viene invocato il metodo accept() di Visitable che in base al
				 * suo tipo effettivo passandogli l'oggetto visitor corrente come
				 * parametro */
				((Visitable)o).accept(this);
			else if(o instanceof Collection)	// Se o è un oggetto Collection
				visit((Collection) o); 	// Invoca visit() passandogli la collezione
		}
	}
		
	/* Metodo che visita un oggetto di tipo VisitableString */
	public void visit(VisitableString vString){
		System.out.println("'" + vString.getString() + "'");
	}
	
}
