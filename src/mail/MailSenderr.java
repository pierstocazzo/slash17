package mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MailSenderr {
	
	static Socket s;
	static PrintWriter out;
	static BufferedReader in;
	static String fromserver;
	static String toserver;
	
	static String username = "c2FzYWxvcmlh";
	static String password = "ZmlhdHB1bnRvMS4z";
	
	static String server = "ml.mat.unical.it";
	static int porta = 25;
	
	public static void main(String[] args) {
		String body = "Salve prof. \n" +
				"Non riesco ad inviare mail tramite ml.mat.unical.it " +
				"ad indirizzi con dominio diverso da @mat.unical.it.\n" +
				"E' una precauzione del server o sto sbagliando qualcosa?\n\n" +
				"Grazie";
		MailSenderr.sendmail("sasaloria@hotmail.com", "deadlyomen17@gmail.com", "server smtp ml.mat.unical.it", body);
	}
	
	public static void sendmail( String mittente, String destinatario, String oggetto, String corpo ) {
		try {
			s = new Socket( server, porta );
			System.out.println("Connesso a " + s.getInetAddress() + " sulla porta " + s.getLocalPort() + " dalla porta " + s.getPort() );
		} catch (Exception e) {
			System.err.println("Errore connessione");
			return;
		}
		
		try {
			out = new PrintWriter( s.getOutputStream() );
			in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
			System.out.println("Buffer ottenuti");
		} catch (IOException e1) {
			System.err.println("Impossibile ottenere i buffer");
			return;
		}
		
		System.out.println("Inizio conversazione \n");
		
		read();
		write("EHLO mailserver");
		read();
		write("AUTH LOGIN");
		read();
		write(username);
		read();
		write(password);
		read();
		write("MAIL FROM: <" + mittente + ">");
		read();
		write("RCPT TO: <" + destinatario + ">");
		read();
		write("DATA");
		read();
		write("subject: " + oggetto);
		write(corpo);
		write("\r\n.\r\n");
		read();
		
		System.out.println("\n*Email spedita*");
		
		close();
	}
	
	private static void close() {
		try {
			System.out.println("\nFine conversazione");			
			out.close();
			in.close();
			System.out.println("Buffer chiusi");
			s.close();
			System.out.println("Disconnessione");
		} catch (IOException e) {
			System.err.println("Errore disconnessione");
			return;
		}
	}

	private static void write( String toserver ) {
		out.println( toserver );
		out.flush();
		System.out.println("To server: " + toserver);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
	}

	private static void read() {
		String fromserver;
		try {
			fromserver = in.readLine();
			
			String res = fromserver.split(" ")[0];
			
			if( Integer.parseInt(res) > 500 ) {
				System.err.println( "\nERROR: " + fromserver );
				close();
				System.exit(0);
			} else {
				System.err.println( "From server: " + fromserver);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.err.println("Il server non risponde...");
		}
	}
	
}
