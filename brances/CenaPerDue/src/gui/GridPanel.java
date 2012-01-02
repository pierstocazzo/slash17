package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import env.Env;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel[][] labelMatrix;
	public static ImageIcon tileIcon, tableIcon, mealIcon, suitIcon, 
				ciccioIcon, flowerIcon, doorIcon, grassIcon, flowerTakenIcon, suitUpIcon;
	public static int iconWidth = 60, iconHeigth = 120;

	Env env;

	public GridPanel (Env env){
		this.env = env;
		init();
		update();
	}

	void init() {
		setLayout(new FlowLayout());

		JPanel flowPanel = new JPanel();
		add(flowPanel);
		flowPanel.setLayout(new GridLayout(env.rooms, env.posForRoom));

		tileIcon = new ImageIcon(new ImageIcon("img/tile.png")
		.getImage().getScaledInstance(iconWidth, iconHeigth, 100));
		mealIcon = new ImageIcon(new ImageIcon("img/meal.png")
		.getImage().getScaledInstance(iconWidth, iconHeigth, 100));
		flowerIcon = new ImageIcon(new ImageIcon("img/flowers.png")
		.getImage().getScaledInstance(iconWidth, iconHeigth, 100));
		suitIcon = new ImageIcon(new ImageIcon("img/suit.png")
		.getImage().getScaledInstance(iconWidth, iconHeigth, 100));
		tableIcon = new ImageIcon(new ImageIcon("img/table.png")
		.getImage().getScaledInstance(iconWidth, iconHeigth, 100));
		grassIcon = new ImageIcon(new ImageIcon("img/grass.png")
		.getImage().getScaledInstance(iconWidth, iconHeigth, 100));
		ciccioIcon = new ImageIcon(new ImageIcon("img/ciccio.png")
		.getImage().getScaledInstance(iconWidth, iconHeigth, 100));
		flowerTakenIcon = new ImageIcon(new ImageIcon("img/flowers_taken.png")
		.getImage().getScaledInstance(iconWidth, iconHeigth, 100));
		suitUpIcon = new ImageIcon(new ImageIcon("img/suit_up.png")
		.getImage().getScaledInstance(iconWidth, iconHeigth, 100));

		labelMatrix = new JLabel[env.rooms][env.posForRoom];

		for(int i=0; i<env.rooms; i++)
			for(int j=0; j<env.posForRoom; j++){
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.fill = GridBagConstraints.BOTH;
				constraints.gridx = i;
				constraints.gridy = j;
				final JLabel label = new JLabel();
				label.setPreferredSize(new Dimension(iconWidth,iconHeigth));
				labelMatrix[i][j] = label;
				flowPanel.add(label, constraints);
			}
	}

	public void update() {
		for(int i=0; i<env.rooms; i++)
			for(int j=0; j<env.posForRoom; j++){
				ImageIcon icon = null;

				// disegna i vari elementi
				switch (env.get(i, j)) {
				case Env.MEAL:
					icon = mealIcon;
					break;
				case Env.FLOWERS:
					icon = flowerIcon;
					break;
				case Env.GRASS:
					icon = grassIcon;
					break;
				case Env.TABLE:
					icon = tableIcon;
					break;
				case Env.SUIT:
					icon = suitIcon;
					break;
				case Env.TILE:
					icon = tileIcon;
					break;
				default:
					icon = null;	
				}

				// disegna ciccio, eventualmente con i fiori e/o con l'abito
				if (env.player_i() == i && env.player_j() == j) {
					if (env.isFlower_taken())
						icon = flowerTakenIcon;
					else if (env.isSuit_up())
						icon = suitUpIcon;
					else
						icon = ciccioIcon;
				}

				labelMatrix[i][j].setIcon(icon);
			}
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
