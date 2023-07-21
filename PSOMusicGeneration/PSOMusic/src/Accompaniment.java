import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used to generate 16 chords (tonic, subdominant and dominant) in a given key using PSO.
 */
public class Accompaniment {
    private int numberOfParticles; // number of particles
    private Key key; // the key of the melody
    private ArrayList<Point3D> chords; // generated chords
    private Point3D globalBest; // global best point in the search space
    private int bestFitness; // global best fitness in the search space
    private int numberOfIterations; // number of iterations

    // Constructor
    Accompaniment(Key key, int numOfParticles) {
        this.numberOfParticles = numOfParticles;
        this.key = key;
        chords = new ArrayList<>();
    }

    /**
     * running PSO algorithm
     */
    ArrayList<Point3D> runPSO() {
        int iterations = 0;
        for (int i = 0; i < 16; i++) {
            globalBest = new Point3D(0, 0, 0);
            bestFitness = 0;
            iterations += numberOfIterations;
            numberOfIterations = 0;
            chords.add(generate());
        }

        // printing some stats
        System.out.println("Generation of chords took " + iterations + " iterations");
        return chords;
    }

    /**
     * func to generate chords
     */
    private Point3D generate() {
        Random random = new Random();
        ArrayList<AccompanimentParticle> particles = new ArrayList<>();

        // initialization
        for (int i = 0; i < numberOfParticles; i++) {
            AccompanimentParticle particle = new AccompanimentParticle(key.getTonic());
            particle.setMyBest(particle.getVelocity());
            particles.add(particle);
        }

        // while best fitness is not 3 and number of iterations not exceed 500
        while (bestFitness != 3 && numberOfIterations < 500) {
            numberOfIterations++;

            calculateFitness(particles); // calculating fitness of this particle
            findGlobalBest(particles); // finding global best particle

            // updating position and velocity
            for (AccompanimentParticle p : particles) {
                Point3D vel = p.getVelocity();
                // updating velocity for every coordinate
                int velX = (int) Math.round(vel.x + 2 * random.nextDouble() * (p.getMyBest().x - p.getPosition().x)
                        + 2 * random.nextDouble() * (globalBest.x - p.getPosition().x));
                int velY = (int) Math.round(vel.y + 2 * random.nextDouble() * (p.getMyBest().y - p.getPosition().y)
                        + 2 * random.nextDouble() * (globalBest.y - p.getPosition().y));
                int velZ = (int) Math.round(vel.z + 2 * random.nextDouble() * (p.getMyBest().z - p.getPosition().z)
                        + 2 * random.nextDouble() * (globalBest.z - p.getPosition().z));

                p.setVelocity(new Point3D(velX, velY, velZ));

                // %15 + getTonic() is used to keep position in the range
                int posX = (velX + p.getPosition().x) % 15 + key.getTonic();
                int posY = (velY + p.getPosition().y) % 15 + key.getTonic();
                int posZ = (velZ + p.getPosition().z) % 15 + key.getTonic();
                p.setPosition(new Point3D(posX, posY, posZ));
            }
        }

        return new Point3D(globalBest.x, globalBest.y, globalBest.z);
    }

    /**
     * func to calculate fitness of accompaniment
     */
    private void calculateFitness(ArrayList<AccompanimentParticle> list) {
        int index = chords.size();

        // initialization
        for (AccompanimentParticle p : list) {
            int fit = 0;
            Point3D pos = p.getPosition();

            // check if first note is equal to tonic
            if (pos.x == key.getTonic())
                fit++;
            if ((pos.x == key.getDominant() || pos.x == key.getSubdominant()) && index != 16)
                fit++;

            //check whether the other notes are good and in the key
            // in midi table notes in major scale have its own ratios, for example if we take C - E - G chord,
            // C is 60, E is 64 and F is 67;
            // C maps to x, E maps to y and G maps to z
            // difference is visible if y coordinate which is E equals to x coordinate which is C than it is a major scale,
            // because 64 == 60 + 4

            // the same idea but for minor scale

            if (key.scale == Scale.MAJOR) {
                if (pos.y == pos.x + 4)
                    fit++;
                if (pos.z == pos.y + 3)
                    fit++;

            } else {

                if (pos.y == pos.x + 3)
                    fit++;
                if (pos.z == pos.y + 4)
                    fit++;
            }

            // if two chords repeat more than twice, then fitness = 0
            if (index > 1) {
                if (pos.x == chords.get(index - 1).x &&
                        pos.x == chords.get(index - 2).x)
                    fit = 0;
            }
            //check whether the first and the last chords are tonic chords
            if ((index == 15 || index == 0) && pos.x != key.getTonic())
                fit = 0;

            // update fitness
            p.setFitness(fit);
            if (fit > p.getBestFitness()) {
                p.setMyBest(p.getPosition());
                p.setBestFitness(fit);
            }

        }
    }

    /**
     * Function which finds global best particle
     */
    private void findGlobalBest(ArrayList<AccompanimentParticle> list) {
        int max = 0;
        Point3D best = new Point3D(0, 0, 0);
        for (AccompanimentParticle p : list) {
            if (p.getFitness() > max) {
                max = p.getFitness();
                best = p.getPosition();
            }
        }
        this.globalBest = new Point3D(best.x, best.y, best.z);
        bestFitness = max;
    }
}