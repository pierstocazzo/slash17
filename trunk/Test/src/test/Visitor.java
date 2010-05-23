package test;

/* Interfaccia che rappresenta un generico oggetto visitatore che può visitare o
 * oggetti di tipo Collection, o oggetti di tipo VisitableString, o oggetti di tipo
 * nFloat */
import java.util.Collection;

public interface Visitor {
	public void visit(Collection collection);
	public void visit(VisitableString string);
}
