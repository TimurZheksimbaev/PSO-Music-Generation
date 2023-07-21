import java.util.ArrayList;
import java.util.Random;

/**
 * This class generates music based on the chords generated in the accompaniment.
 */
public class Melody {
    private ArrayList<Point3D> chords; // already generated chord
    private ArrayList<Integer> melody; // generated melody
    private Key key; // key of the melody
    private int globalBest; // global best point of the search
    private int bestFitness; // fitness of the global best point
    private int numberOfParticles; //number of particles in PSO
    private int numberOfIterations;

    public Melody(ArrayList<Point3D> chords, Key key, int numOfParticles) {
        this.chords = chords;
        melody = new ArrayList<>();
        this.key = key;
        this.numberOfParticles = numOfParticles;
    }

    /**
     * This function is used to run the PSO algorithm for melody
     */
    ArrayList<Integer> runPSO() {
        int iterations = 0;
        for (int i = 0; i < 32; i++) {
            globalBest = 0;
            bestFitness = 0;
            iterations += numberOfIterations;
            numberOfIterations = 0;
            melody.add(generate());
        }
        System.out.println("Generation of melody took " + iterations + " iterations");
        return melody;
    }

    /**
     * This function is used to generate the melody
     */
    private int generate() {
        Random random = new Random();
        ArrayList<MelodyParticle> particles = new ArrayList<>();
        int min = key.getTonic() + 12; // melody is above accompaniment

        // initialization of particles
        for (int i = 0; i < numberOfParticles; i++) {
            particles.add(new MelodyParticle(min));
        }

        // while best fitness value of a particle is not 2 an2number of iterations not exceeds 1000
        while (bestFitness != 2 && numberOfIterations < 1000) {
            numberOfIterations++;
            calculateFitness(particles); // calculate fitness
            findGlobalBest(particles); // find global best

            // update velocity and position for every particle
            for (MelodyParticle p : particles) {
                int vel = (int) Math.round(p.getVelocity() + 2 * random.nextDouble() * (p.getMyBest() - p.getPosition())
                        + 2 * random.nextDouble() * (globalBest - p.getPosition()));

                p.setVelocity(vel);
                int pos = (p.getPosition() + vel) % 12 + min;
                p.setPosition(pos);
            }
        }
        return globalBest;
    }

    /**
     * Calculates fitness
     * */
    private void calculateFitness(ArrayList<MelodyParticle> list) {

        for (MelodyParticle p : list) {
            int fit = 0;
            int index = melody.size();
            int note = p.getPosition();
            Point3D chord = chords.get(index / 2); // chord that plays with the note

            if (key.isNote(note)) { // if note is in the key
                if (index != 31)
                    fit++; // fitness+1
                else if (note == key.getTonic() + 12)
                    fit++;
            }

            if (index % 2 == 0) { // if the note is played with the chord
                if (note == chord.x + 12 || note == chord.y + 12 ||
                        note == chord.z + 12) { // check whether it is the same note
                    fit++; // and if it is, add fitness
                }
            } else {
                if (note > chord.z) {
                    if (index > 0 && Math.abs(note - melody.get(index - 1)) < 4) // if note is not far from the other note
                        fit++; // add to the fintess
                }
            }
            p.setFitness(fit);
            if (fit > p.getBestFitness()) {
                p.setMyBest(note);
                p.setBestFitness(fit);
            }
        }
    }

    /** finds global best particle according to their fitness values */
    private void findGlobalBest(ArrayList<MelodyParticle> list) {
        int max = 0;
        int best = 0;
        for (MelodyParticle p : list) {
            if (p.getFitness() > max) {
                max = p.getFitness();
                best = p.getPosition();
            }
        }
        this.globalBest = best;
        bestFitness = max;
    }

    public ArrayList<Integer> getMelody() {
        return melody;
    }

    public ArrayList<Point3D> getChords() {
        return chords;
    }
}