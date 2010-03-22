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
	
	static String username = "c2xhc2gxNw==";
	static String password = "bWV0YWxsaWNh";
	
	static String server = "smtp.tele2.it";
	static int porta = 587;
	
	public static void main(String[] args) {
		String body = "Prova invio email";
		MailSenderr.sendmail("slash17@tele2.it", "sasaloria@hotmail.com", "prova", body);
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
		write("HELO " + server);
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
		write("FROM: " + mittente);
		write("TO: " + destinatario);
		write("SUBJECT: " + oggetto);
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
			
			try {
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
				System.err.println( "From server: " + fromserver);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.err.println("Il server non risponde...");
		}
	}
	
}
