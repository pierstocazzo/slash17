package test;

import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 4395459176419007992L;

	JPanel mainPanel;
	
	public MainFrame() {
		super( "Test Swing" );
		
		setSize( 800, 600 );
		
		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				int answer = JOptionPane.showConfirmDialog(
					e.getWindow(), "Sei sicuro di voler chiudere?",
					"Chiusura Applicazione",
					JOptionPane.YES_NO_CANCEL_OPTION );
				
				if( answer == JOptionPane.YES_OPTION ) {
					dispose();
				}
			};
		} );
		
		setVisible( true );
		
		mainPanel = new JPanel();
		setContentPane( mainPanel );
		
		JLabel text1 = new JLabel( "Allineato a sinistra", SwingConstants.LEFT );
		mainPanel.add( text1 );
		
		JLabel text2 = new JLabel( "Allineato a destra", SwingConstants.RIGHT );
		mainPanel.add( text2 );
		
		JLabel text3 = new JLabel( "Centrato" );
		mainPanel.add( text3 );
		
		JTextField campoDiTesto = new JTextField( "Immetti qui una stringa...", 30 );
		mainPanel.add( campoDiTesto );
		
		JTextArea areaDiTesto = new JTextArea( "Questa é un'area di testo di\n5 righe e 30 colonne" , 5, 30 );
		mainPanel.add( areaDiTesto );
		
		campoDiTesto.setEditable(true);

		areaDiTesto.setLineWrap(true);

		areaDiTesto.setWrapStyleWord(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
