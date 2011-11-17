package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import vacuumCleaner.Square;

/**
 * Implement the mouse event to create manually an environment configuration
 *
 */
public class ClickHandler extends MouseAdapter {
	JLabel label;
	int i,j;
	
	/** 
	 * @param label label that identifies a button
	 * @param i coordinate X of the mouse
	 * @param j coordinate Y of the mouse
	 */
	ClickHandler (JLabel label, int i, int j) {
		this.label = label;
		this.i = i;
		this.j = j;
	}
	
	@Override 
	/**
	 * Choose and set the status of the square to clean/dirty/obstacle at mouse click
	 */
	public void mouseClicked (MouseEvent e) {
		super.mouseClicked(e);
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			/* assert the agent is not on this square */
			if (GridPanel.env.agent.x == i && GridPanel.env.agent.y == j)
				return;
			/* make this square of the current type */
			GridPanel.env.floor.set (i, j, GridPanel.currType);
			label.setIcon (GridPanel.currIcon);
			
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			/* show a popup menu to choose what to insert */
			JPopupMenu menu = new JPopupMenu();
			JMenuItem dirty = new JMenuItem("Dirty");
			dirty.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					GridPanel.currType = Square.Type.DIRTY;
					GridPanel.currIcon = GridPanel.dirtIcon;
				}
			});
			JMenuItem clean = new JMenuItem("Clean");
			clean.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					GridPanel.currType = Square.Type.CLEAN;
					GridPanel.currIcon = GridPanel.tileIcon;
				}
			});
			JMenuItem obstacle = new JMenuItem("Obstacle");
			obstacle.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					GridPanel.currType = Square.Type.OBSTACLE;
					GridPanel.currIcon = GridPanel.obstacleIcon;
				}
			});
			menu.add(clean);
			menu.add(dirty);
			menu.add(obstacle);
			menu.show(label, e.getX(), e.getY());
		}
	}
}