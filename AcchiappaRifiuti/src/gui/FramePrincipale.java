package gui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class FramePrincipale extends JFrame {

	private static final long serialVersionUID = 762154615227655088L;

	PannelloPricipale gioco;
	
	public FramePrincipale() {
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				AcchiappaRifiuti.instance().setButtonGiocaEnabled(true);
				super.windowClosing(e);
			}
		});
		
		Dimension sizePrincipale = new Dimension(1148, 694);
		setMaximumSize(sizePrincipale);
		setMinimumSize(sizePrincipale);
		setPreferredSize(sizePrincipale); 
		
		gioco = new PannelloPricipale();
		add(gioco);
		
//		pack();
		setVisible(true);
	}
}
	
