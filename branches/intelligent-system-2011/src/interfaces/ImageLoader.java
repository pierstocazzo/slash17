package interfaces;

import javax.swing.ImageIcon;

class ImageLoader {
	public static ImageIcon playIcon, stopIcon, nextIcon;
	public static ImageIcon dirtIcon, obstacleIcon, tileIcon, vacuumIcon;
	public static int iconSize = 60;
	
	static {
		playIcon = new ImageIcon( new ImageIcon("img/play.png").getImage().getScaledInstance(30,30,30));
		stopIcon = new ImageIcon( new ImageIcon("img/stop.png").getImage().getScaledInstance(30,30,30));
		nextIcon = new ImageIcon( new ImageIcon("img/next.png").getImage().getScaledInstance(30,30,30));
		
		dirtIcon = new ImageIcon(new ImageIcon("img/dirt.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		obstacleIcon = new ImageIcon(new ImageIcon("img/obstacle.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		tileIcon = new ImageIcon(new ImageIcon("img/tile.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
		vacuumIcon = new ImageIcon(new ImageIcon("img/vacuum.jpeg").getImage().getScaledInstance(iconSize,iconSize,iconSize));
	}
}
