package mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MailSenderr {
	
	Socket s;
	PrintWriter out;
	BufferedReader in;
	String fromserver;
	String toserver;
	
	String server = "smtp.mail.yahoo.it";
	
	public static void main(String[] args) {
		new MailSenderr().sendmail("slash17@tele2.it", "slash17@tele2.it", "prova", "prova");
	}
	
	public void sendmail( String mittente, String destinatario, String oggetto, String corpo ) {
		try {
			s = new Socket( server, 25 );
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
		write("HELO mailserver");
		read();
		write("MAIL FROM: <slash17@tele2.it>");
		read();
		write("RCPT TO: <sasaloria@hotmail.com>");
		read();
		write("DATA");
		read();
		write(corpo);
		write("\r\n.\r\n");
		
		System.out.println("Email spedita");
		
		close();
	}
	
	private void close() {
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

	private void write( String toserver ) {
		out.println( toserver );
		out.flush();
		System.out.println("To server: " + toserver);
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
	}

	private void read() {
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
