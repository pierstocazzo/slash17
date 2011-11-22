/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 * 
 * @author PiGix
 */
public class NewJApplet extends JApplet {

	private static final long serialVersionUID = -8861578534803783461L;

	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	public void init() {
		// TODO start asynchronous download of heavy resources
		JFrame f = new JFrame("L'Acchiappa Rifiuti");
		f.add(new PannelloPricipale());
		f.pack();

		f.setVisible(true);
	}

	// TODO overwrite start(), stop() and destroy() methods

}
