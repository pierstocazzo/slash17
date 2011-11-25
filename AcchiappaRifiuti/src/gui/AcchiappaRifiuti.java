package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class AcchiappaRifiuti extends JApplet {
	
	private static final long serialVersionUID = 3779225454776476552L;
	static FramePrincipale rootPane;
	JButton buttonGioca;
	
	static AcchiappaRifiuti instance;
	
	public static void main(String[] args) {
		/** main usato per lanciare AcchiappaRifiuti come applicazione desktop */
		JFrame f = new JFrame("L'Acchiappa Rifiuti!");
		instance().createGUI(f);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}
	
	public static AcchiappaRifiuti instance() {
		if (instance == null)
			instance = new AcchiappaRifiuti();
		return instance;
	}
	
	public Container getFramePrincipale() {
		return rootPane;
	}
	
	/** chiamato quando l'applicazione viene lanciata come applet */
    public void init() {
    	instance = this;
    	createGUI(this);
    }
    
    private void createGUI(Container c) {
    	c.setLayout(new BorderLayout());
    	Image sfondo = new ImageIcon(getClass().getClassLoader().getResource("img/sfondoApplet.jpg")).getImage();
    	c.add(new PannelloSfondo(sfondo), BorderLayout.CENTER);

    	buttonGioca = new JButton("Gioca!");
    	buttonGioca.setOpaque(false);
    	buttonGioca.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			buttonGioca.setEnabled(false);
    			rootPane = new FramePrincipale();
    		}
    	});
    	c.add(buttonGioca, BorderLayout.SOUTH);
    	
    	Dimension d = new Dimension(sfondo.getWidth(null), sfondo.getHeight(null)+30);
    	c.setMinimumSize(d);
    	c.setMaximumSize(d);
    	c.setPreferredSize(d);
    	c.setSize(d);
    } 
    
	public JComponent getPannello() {
		return rootPane.gioco;
	}

	public void finished() {
		rootPane.dispose();
		buttonGioca.setEnabled(true);
	}
	
	public void setButtonGiocaEnabled(boolean b) {
		buttonGioca.setEnabled(b);
	}
}
