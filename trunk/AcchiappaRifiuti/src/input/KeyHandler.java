/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import gui.GestoreTurni;
import gui.PannelloPricipale;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Sal
 */
public class KeyHandler extends KeyAdapter {

	PannelloPricipale p;

	public KeyHandler(PannelloPricipale p) {
		this.p = p;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (!p.isInputAttivo() || !GestoreTurni.instance().check(e.getKeyCode()))
			return;

		GestoreTurni.instance().setTastoPremuto(e.getKeyCode());
	}
}
