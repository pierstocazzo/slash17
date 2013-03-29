package gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.omg.CORBA.Environment;

import env.Env;

public class MyMouseAdapter extends MouseAdapter {
	
	int i, j;
	GridPanel gui;
	
	public MyMouseAdapter(GridPanel gui, int i, int j) {
		super();
		this.gui = gui;
		this.i = i;
		this.j = j;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if (!gui.isPause())
			return;
		if (gui.env.matrix[i][j] == Env.TILE) {
			if (gui.getSelectedCellI() == null || gui.getSelectedCellJ() == null) 
				return;
			gui.env.matrix[i][j] = gui.env.matrix[gui.getSelectedCellI()][gui.getSelectedCellJ()];
			gui.env.matrix[gui.getSelectedCellI()][gui.getSelectedCellJ()] = Env.TILE;
			gui.labelMatrix[gui.getSelectedCellI()][gui.getSelectedCellJ()].setBorder(null);
			gui.setSelectedCellI(null);
			gui.setSelectedCellJ(null);
			gui.update();
		} else if (isMovable(gui.env.matrix[i][j])) {
			if (gui.getSelectedCellI() != null && gui.getSelectedCellJ() != null) 
				gui.labelMatrix[gui.getSelectedCellI()][gui.getSelectedCellJ()].setBorder(null);
			gui.labelMatrix[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
			gui.setSelectedCellI(i);
			gui.setSelectedCellJ(j);
		}
	}

	private boolean isMovable(char c) {
		if (c == Env.DOOR || c == Env.GRASS || c == Env.RENATA || 
				c == Env.TABLE || c == Env.MEAL || c == Env.TILE)
			return false;
		return true;
	}
}
