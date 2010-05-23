package test;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class TTFontDemo extends JLabel {

	   /** Construct a TTFontDemo -- Create a Font from TTF.
	    */
	   public TTFontDemo(String fontFileName, String text)
	   throws IOException, FontFormatException {
	      super(text, JLabel.CENTER);

	      setBackground(Color.white);

	      // First, see if we can load the font file.
	      InputStream is = this.getClass().getResourceAsStream(fontFileName);
	      if (is == null) {
	         throw new IOException("Cannot open " + fontFileName);
	      }

	      // createFont makes a 1-point font, bit hard to read :-)
	      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, is);

	      // So scale it to 24 pt.
	      Font ttfReal = ttfBase.deriveFont(Font.PLAIN, 24);

	      setFont(ttfReal);
	   }

	   /** Simple main program for TTFontDemo */
	   public static void main(String[] args) throws Exception {

	      String DEFAULT_MESSAGE =
	         "What hath man wrought? Or at least rendered?";
	      String DEFAULT_FONTFILE =
	         "adjutant.ttf";
	      String message = args.length == 1 ? args[0] : DEFAULT_MESSAGE;
	      JFrame f = new JFrame("TrueType Font Demo");

	      TTFontDemo ttfd = new TTFontDemo(DEFAULT_FONTFILE, message);
	      f.getContentPane().add(ttfd);

	      f.setBounds(100, 100, 700, 250);
	      f.setVisible(true);
	      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   }
	}
