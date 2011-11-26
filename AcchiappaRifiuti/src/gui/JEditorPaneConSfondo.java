package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.UIManager;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class JEditorPaneConSfondo extends JEditorPane {
	private static final long serialVersionUID = 7844831466551818402L;
	
	private Image sfondoImg;
	private int height;
	
    public JEditorPaneConSfondo(Image sfondoImg) {
    	super(new HTMLEditorKit().getContentType(), null);
    	setBorder(BorderFactory.createEmptyBorder());
    	Font font = UIManager.getFont("Label.font");
        String bodyRule = "body { font-family: " + font.getFamily() + "; " +
                "font-size: " + font.getSize() + "pt; }";
        ((HTMLDocument) this.getDocument()).getStyleSheet().addRule(bodyRule);
    	
    	this.sfondoImg = sfondoImg;
    	
    	this.height = sfondoImg.getHeight(null);
    	
    	setOpaque(false);
	}
    
    public void setDimension(int width, int height) {
    	this.height = height;
    }

	protected void paintComponent(Graphics g) {
        g.drawImage(sfondoImg, 0, 0, this);
        Image i = new ImageIcon(getClass().getClassLoader().getResource("img/empty.png")).getImage();
        g.drawImage(i, 0, height, Color.white, null);
        super.paintComponent(g);
    }

	public void setNumeroGiocatori(int numeroGiocatori) {
		this.height = numeroGiocatori*115+50;
	}

}
