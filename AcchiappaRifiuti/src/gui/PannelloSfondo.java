package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

class PannelloSfondo extends JPanel {
	private static final long serialVersionUID = -912931945527929467L;
	private Image sfondoImg;

    public PannelloSfondo(Image sfondoImg) {
        this.sfondoImg = sfondoImg;
        Dimension d = new Dimension(sfondoImg.getWidth(null), sfondoImg.getHeight(null));
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
    }

    public PannelloSfondo(Image sfondoImg, Dimension d) {
    	this.sfondoImg = sfondoImg;
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
	}

	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sfondoImg, 0, 0, this);
    }
}