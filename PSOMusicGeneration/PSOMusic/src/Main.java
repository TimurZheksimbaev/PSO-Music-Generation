import org.jfugue.midi.MidiFileManager;
import org.jfugue.midi.MidiParser;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// You need to install library jfugue to execute program

/**
 * Main class of the program
 */

public class Main {

    static Player player = new Player();


    public static void main(String[] args) {

        Key key = new Key();

        Accompaniment accompanimentChords = new Accompaniment(key, 50);
        ArrayList<Point3D> accompaniment = accompanimentChords.runPSO(); // generate accompaniment

        Melody melodyNotes1 = new Melody(accompaniment, key, 5);
        ArrayList<Integer> melody1 = melodyNotes1.runPSO(); // generate first melody

        Melody melodyNotes2 = new Melody(accompaniment, key, 5);
        ArrayList<Integer> melody2 = melodyNotes2.runPSO(); // generate second melody

        // generating song using accompaniment and two melodies
        createMidiFile(generateMusicString(accompaniment, melody1, melody2), 100);

    }

    /**
     * Function which generates music string from which I make a song
     */
    private static String generateMusicString(ArrayList<Point3D> chords, ArrayList<Integer> music1, ArrayList<Integer> music2) {
        StringBuilder sb = new StringBuilder();

        String instrument = "ELECTRIC_JAZZ_GUITAR";

        for (int i = 0; i < 32; i++) {

            int melodyNote1 = music1.get(i);
            int melodyNote2 = music2.get(i);

            Point3D c = chords.get(i / 2);

            /**
             * Jfugue library
             * q - quarter duration
             * i - eighth duration
             * a and number is attack level, for example here attack is 40: qa40
             * + is when we need notes to play simultaneously
             * I[GUITAR] is choice of instrument for note
             * whitespace is space between notes
             * */

            if (i % 4 == 0) {
                sb
//                .append("I[GUITAR] ") // adding instrument
                        // accompaniment chords
                        .append(c.x).append("qa40+").append(c.y).append("qa40+").append(c.z).append("qa40+")

//                .append("I[").append(instrument).append("] ") // adding instrument
//                        .append(melodyNote1).append("qa55 rx ")
                        .append(melodyNote2).append("ia55 "); // melody notes


            } else {

                sb
//                        .append("I[").append(instrument).append("] ") // adding instrument
//                        .append(melodyNote2).append("qa55 ")
                        .append(melodyNote1).append("ia55 "); // melody notes
            }
        }
        return sb.toString();
    }


    /**
     * Function to save midi file
     */
    private static void createMidiFile(String music, int tempo) {

        Pattern pattern = new Pattern(music).setVoice(0).setTempo(tempo);

        try {
            File file = new File("Presentation.mid");
            MidiFileManager.savePatternToMidi(pattern, file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
