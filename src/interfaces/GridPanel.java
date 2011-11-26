package interfaces;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import vacuumCleaner.Environment;
import vacuumCleaner.Square;
import vacuumCleaner.Square.Type;

/**
 * Implement a JPanel to represent the environment and the agent
 *
 */
public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel[][] labelMatrix;
	public static ImageIcon dirtIcon, obstacleIcon, tileIcon, vacuumIcon;
	public int labelSize;
	static Environment env;
	
	static Square.Type currType;
	static ImageIcon currIcon;
	
	/**
	 * Generate a graphic representation of the environment and its elements
	 * @param env an environment configuration
	 */
	public GridPanel(Environment env){
		GridPanel.env = env;
		init();
		update();
	}
	
	/**
	 * Generate a GridLayout according with the width and length of the floor
	 * and set the images that correspond to each element of the environment
	 */
	void init() {
		
		setLayout(new FlowLayout());
		
		JPanel flowPanel = new JPanel();
		add(flowPanel);
		flowPanel.setLayout(new GridLayout(env.floor.length, env.floor.width));
		
		dirtIcon = ImageLoader.dirtIcon;
		obstacleIcon = ImageLoader.obstacleIcon;
		tileIcon = ImageLoader.tileIcon;
		vacuumIcon = ImageLoader.vacuumIcon;
		
		labelMatrix = new JLabel[env.floor.length][env.floor.width];
		
		currType = Type.DIRTY;
		currIcon = dirtIcon;
		
		for(int i=0; i<env.floor.length; i++)
			for(int j=0; j<env.floor.width; j++){
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.fill = GridBagConstraints.BOTH;
				constraints.gridx = i;
				constraints.gridy = j;
				final JLabel label = new JLabel();
				label.setPreferredSize(new Dimension(ImageLoader.iconSize,ImageLoader.iconSize));
				label.addMouseListener(new ClickHandler(label,i,j));
				labelMatrix[i][j] = label;
				flowPanel.add(label, constraints);
			}
	}
	/**
	 * Generate a graphic representation of the environment 
	 * on a thread, according with the state of each tile (square)
	 * and the position of the agent
	 */
	public void update() {
		for(int i=0; i<env.floor.length; i++)
			for(int j=0; j<env.floor.width; j++){
				if(env.floor.get(i,j) == Square.Type.DIRTY)
					labelMatrix[i][j].setIcon(dirtIcon);
				if(env.floor.get(i,j) == Square.Type.CLEAN)
					labelMatrix[i][j].setIcon(tileIcon);
				if(env.floor.get(i,j) == Square.Type.OBSTACLE)
					labelMatrix[i][j].setIcon(obstacleIcon);
				if(env.agent.x == i && env.agent.y == j)
					labelMatrix[i][j].setIcon(vacuumIcon);
			}
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
