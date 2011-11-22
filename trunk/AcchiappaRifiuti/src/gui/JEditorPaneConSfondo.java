package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JEditorPane;
import javax.swing.UIManager;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class JEditorPaneConSfondo extends JEditorPane {
	private static final long serialVersionUID = 7844831466551818402L;
	
	private Image sfondoImg;

    public JEditorPaneConSfondo(Image sfondoImg) {
    	super(new HTMLEditorKit().getContentType(), null);
    	
    	Font font = UIManager.getFont("Label.font");
        String bodyRule = "body { font-family: " + font.getFamily() + "; " +
                "font-size: " + font.getSize() + "pt; }";
        ((HTMLDocument) this.getDocument()).getStyleSheet().addRule(bodyRule);
    	
    	this.sfondoImg = sfondoImg;
    	setOpaque(false);
	}

	protected void paintComponent(Graphics g) {
        g.drawImage(sfondoImg, 0, 0, this);
        super.paintComponent(g);
    }

}
