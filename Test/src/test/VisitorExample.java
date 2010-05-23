package test;

import java.util.Vector;
public class VisitorExample {
   public static void main (String[] arg) {

      // Prepare a heterogeneous collection
      Vector mainVector = new Vector();
      mainVector.add( new VisitableString( "A string" ) );
     
      Vector secondVector = new Vector();
      secondVector.add( new VisitableString( "Another string" ) );
      mainVector.add( secondVector );

      mainVector.add( new Double( 4 ) );

      // Visit the collection
      Visitor browser = new WatcherVisitor();
      browser.visit( mainVector );
   }
} 