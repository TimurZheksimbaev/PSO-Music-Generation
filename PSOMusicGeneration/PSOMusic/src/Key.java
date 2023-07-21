import java.util.ArrayList;
import java.util.Random;

/**
 * Class which represents the musical key.
 * It generates the key based on the tonic and the scale and
 * stores all the possible allowed values.
 */

enum Scale {
    MAJOR, MINOR
}

// enum for major and minor
public class Key {

    public ArrayList<Integer> notes; // values from 48 to 96
    public Scale scale;
    public int tonic;

    // constructor
    Key() {
        if (new Random().nextInt(2) == 0)
            this.scale = Scale.MAJOR;
        else
            this.scale = Scale.MINOR;
        this.notes = new ArrayList<>();
        findTonic(new Random().nextInt(13) + 60);
    }

    /**
     * This function is used to find the first tonic within the allowed range to
     * build the key values out of it.
     */
    private void findTonic(int tonic) {
        if (tonic < 48 || tonic > 96) {
            System.out.println("Only values between 48 and 96 for tonic are allowed");
            return ;
        }

        int i = 1;
        while (tonic - 12 * i >= 48) {
            i++;
        }
        this.tonic = tonic - 12 * (i - 1);
        i = 1;
        while (tonic + 12 * i <= 96) {
            i++;
        }
        int high = tonic + 12 * (i - 1); // the biggest possible value for a key
        notes.add(this.tonic); // add tonic to the notes
        findKeyNotes(high); // and find other notes
    }

    /**
     * This function uses tonic and high to generate all the
     * possible values for a key.
     */
    private void findKeyNotes(int high) {
        int semitone1, semitone2;
        if (this.scale == Scale.MAJOR) { // if major
            semitone1 = 2;
            semitone2 = 6;
        } else {
            semitone1 = 1;
            semitone2 = 4;
        }

        int note = tonic;
        while (true) {
            for (int i = 0; i < 7; i++) {
                if (i == semitone1 || i == semitone2) {
                    note++;
                    if (note <= high)
                        notes.add(note);
                    else
                        return;
                } else {
                    note += 2;
                    if (note <= high)
                        notes.add(note);
                    else
                        return;
                }
            }
        }
    }

    // getters
    int getTonic() {
        return tonic;
    }

    int getSubdominant() {
        return notes.get(3);
    }

    int getDominant() {
        return notes.get(4);
    }

    /**
     * Function which checks if the note is in the key or not
     */
    boolean isNote(int note) {
        for (int n : notes) {
            if (n == note)
                return true;
        }
        return false;
    }
}