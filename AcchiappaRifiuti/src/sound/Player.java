package sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
 
public class Player extends Thread { 
 
    public static final String VITTORIA = "sound/vittoria.wav";
    public static final String MOVIMENTO = "sound/pedina.wav";
    public static final String LANCIADADO = "sound/dado.wav";
    public static final String RISPOSTACORRETTA = "sound/corretta_jolly.wav";
    public static final String RISPOSTAERRATA = "sound/errata_imprevisto.wav";
    public static final String IMPREVISTO = "sound/errata_imprevisto.wav";
    public static final String JOLLY = "sound/corretta_jolly.wav";
    public static final String DOMANDA = "sound/domanda.wav";
    public static final String ISOLA = "sound/isola.wav";
    
	private String filename;
 
    private Position curPosition;
 
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb 
 
    enum Position { 
        LEFT, RIGHT, NORMAL
    };
    
    public static void play(String file) {
    	new Player(file).start();
    }
    
    public Player(String file) {
    	this.filename = file;
        curPosition = Position.NORMAL;
    } 
 
    public void run() { 
        AudioInputStream audioInputStream = null;
        try { 
        	URL URL = Player.class.getClassLoader().getResource(filename);
            audioInputStream = AudioSystem.getAudioInputStream(URL);
        } catch (UnsupportedAudioFileException e1) { 
            e1.printStackTrace();
            return;
        } catch (IOException e1) { 
            e1.printStackTrace();
            return;
        } 
 
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
        try { 
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) { 
            e.printStackTrace();
            return;
        } catch (Exception e) { 
            e.printStackTrace();
            return;
        } 
 
        if (auline.isControlSupported(FloatControl.Type.PAN)) { 
            FloatControl pan = (FloatControl) auline
                    .getControl(FloatControl.Type.PAN);
            if (curPosition == Position.RIGHT) 
                pan.setValue(1.0f);
            else if (curPosition == Position.LEFT) 
                pan.setValue(-1.0f);
        } 
 
        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        try { 
            while (nBytesRead != -1) { 
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) 
                    auline.write(abData, 0, nBytesRead);
            } 
        } catch (IOException e) { 
            e.printStackTrace();
            return;
        } finally { 
            auline.drain();
            auline.close();
        } 
 
    } 
} 
 