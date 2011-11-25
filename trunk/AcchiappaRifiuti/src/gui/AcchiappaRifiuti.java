package gui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class AcchiappaRifiuti extends JApplet {
	
	private static final long serialVersionUID = 3779225454776476552L;
	static PannelloPricipale gioco;
	static Container rootPane;
	
	static AcchiappaRifiuti instance;
	
	public static void main(String[] args) {
		/** main usato per lanciare AcchiappaRifiuti come applicazione desktop */
		instance().gioca();
	}
	
	public static AcchiappaRifiuti instance() {
		if (instance == null)
			instance = new AcchiappaRifiuti();
		return instance;
	}
	
	public void gioca() {
		gioco = new PannelloPricipale();
		JFrame framePrincipale = new JFrame("L'Acchiappa Rifiuti!");
		framePrincipale.add(gioco);
		framePrincipale.setResizable(false);
			
		framePrincipale.pack();
		framePrincipale.setVisible(true);
		framePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		rootPane = framePrincipale;
	}
	
	public Container getFramePrincipale() {
		return rootPane;
	}
	
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) { 
            System.err.println("createGUI didn't complete successfully");
            e.printStackTrace();
        }
    }
    
    /**
     * Create the GUI. For thread safety, this method should
     * be invoked from the event-dispatching thread.
     */
    private void createGUI() {
        //Create and set up the content pane.
        gioco = new PannelloPricipale();
        setContentPane(gioco);  
        repaint();
		Dimension d = new Dimension(1150, 690);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setPreferredSize(d);
		this.setSize(d);
		
		rootPane = getRootPane();
    } 
    
    public void restart() {
    	instance.createGUI();
    }

	public JComponent getPannello() {
		return gioco;
	}

	public void finished() {
		int r = JOptionPane.showConfirmDialog(rootPane, "Vuoi fare un'altra partita?");
		if (r == JOptionPane.YES_OPTION) {
			System.out.println("restart");
			init();
		} else {
			// si dovrebbe chiudere...
		}
	}
}
