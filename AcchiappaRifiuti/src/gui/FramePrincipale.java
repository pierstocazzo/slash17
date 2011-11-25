package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class FramePrincipale extends JFrame {

	private static final long serialVersionUID = 762154615227655088L;

	public FramePrincipale() {
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosed(WindowEvent e) {
//				AcchiappaRifiuti.instance().setButtonGiocaEnabled(true);
//				super.windowClosed(e);
//			}
			@Override
			public void windowClosing(WindowEvent e) {
				AcchiappaRifiuti.instance().setButtonGiocaEnabled(true);
				super.windowClosing(e);
			}
		});
		
		PannelloPricipale gioco = new PannelloPricipale();
		add(gioco);
		
		pack();
		setVisible(true);
	}
}
	
