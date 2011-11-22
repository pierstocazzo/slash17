package sound;

// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package sound;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import javax.sound.midi.*;
//
///**
// *
// * @author PiGix
// */
//public class Main {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {

//        try {
//        // From file
//        Sequence sequence = MidiSystem.getSequence(new File("../RollOverBeethoven.mid"));
//
//        // From URL
//        sequence = MidiSystem.getSequence(new URL("RollOverBeethoven.mid"));
//
//        // Create a sequencer for the sequence
//        Sequencer sequencer = MidiSystem.getSequencer();
//        sequencer.open();
//        sequencer.setSequence(sequence);
//
//        // Start playing
//        sequencer.start();
//    } catch (MalformedURLException e) {
//    } catch (IOException e) {
//    } catch (MidiUnavailableException e) {
//    } catch (InvalidMidiDataException e) {
//    }
//        // TODO code application logic here
//    }
//
//}

import javax.sound.midi.*;

import java.io.FileInputStream;

public class Main {

	public static void main(String[] args) {

		try {

			Sequencer sequencer = MidiSystem.getSequencer();

			if (sequencer == null)

				throw new MidiUnavailableException();

			sequencer.open();

			FileInputStream is = new FileInputStream("RollOverBeethoven.mid");

			Sequence Seq = MidiSystem.getSequence(is);

			sequencer.setSequence(Seq);

			sequencer.start();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}