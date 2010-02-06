package test;

import java.awt.Checkbox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

public class Test extends JFrame {
	private static final long serialVersionUID = 1L;
	boolean dontAsk = false;
	
	Test() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JOptionPane exitDialog = new JOptionPane( "Sei sicuro di voler chiudere l'applicazione?",
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION );
		
		final Checkbox check = new Checkbox( "Never show this dialog again", false );
		check.addItemListener( new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dontAsk = check.getState();
			}
		});
		exitDialog.add( check );
		check.setVisible(true);
		
		add( exitDialog );
		
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Test();
	}
	
}
