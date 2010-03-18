package mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {


	public static void sendMail( String dest, String mitt, String oggetto, String testoEmail )
	{
		// Creazione di una mail session
		Properties props = new Properties();
		props.put("mail.smtp.host", "ml.mat.unical.it");
		props.put("mail.smtp.port", "25");
		Session session = Session.getDefaultInstance(props);
		
		System.out.println("Connessione effettuata con " + session.getProperty("mail.smtp.host") + 
				" sulla porta " + session.getProperty("mail.smtp.port"));
		
		// Creazione del messaggio da inviare
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(oggetto);
			System.out.println("oggetto ok");
		} catch (MessagingException e) {
			System.err.println("errore soggetto");
			return;
		}
		try {
			message.setText(testoEmail);
			System.out.println("testo ok");
		} catch (MessagingException e) {
			System.err.println("errore setText");
			return;
		}

		// Aggiunta degli indirizzi del mittente e del destinatario
		InternetAddress fromAddress;
		InternetAddress toAddress;
		try {
			fromAddress = new InternetAddress(mitt);
			message.setFrom(fromAddress);
			System.out.println("indirizzo mittente ok");
		} catch (Exception e) {
			System.err.println("errore indirizzo mittente");
			return;
		}
		try {
			toAddress = new InternetAddress(dest);
			message.setRecipient(Message.RecipientType.TO, toAddress);
			System.out.println("indirizzo destinatario ok");
		} catch (Exception e) {
			System.err.println("errore indirizzo destinatario");
			return;
		}

		// Invio del messaggio
		try {
			System.out.println("provo ad inviare la mail..");
			Transport.send(message);
			System.err.println("mail inviata con successo");
		} catch (MessagingException e) {
			System.err.println("errore invio email");
			return;
		}
	}
	
	public static void main(String[] args) {
		MailSender.sendMail("deadlyomen17@yahoo.com", "sasaloria@hotmail.com", "ciao", "ciao");
	}
}
