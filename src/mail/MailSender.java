package mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class MailSender {


	public static void sendMail( String dest, String mitt, String oggetto, String testoEmail )
	{
		// Creazione di una mail session
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.tele2.it");
		props.put("mail.smtp.port", "587");
		Session session = Session.getDefaultInstance(props);

		// Creazione del messaggio da inviare
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(oggetto);
		} catch (MessagingException e) {
			System.err.println("errore soggetto");
			return;
		}
		try {
			message.setText(testoEmail);
		} catch (MessagingException e) {
			System.err.println("errore setText");
			return;
		}

		// Aggiunta degli indirizzi del mittente e del destinatario
		InternetAddress fromAddress;
		try {
			fromAddress = new InternetAddress(mitt);
			InternetAddress toAddress = new InternetAddress(dest);
			message.setFrom(fromAddress);
			message.setRecipient(Message.RecipientType.TO, toAddress);
		} catch (Exception e) {
			System.err.println("errore indirizzi");
			return;
		}

		// Invio del messaggio
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			System.err.println("mail non inviata");
			return;
		}
	}
	
	public static void main(String[] args) {
		MailSender.sendMail("abc@a.it", "sasaloria@hotmail.com", "ciao", "ciao");
	}
}
