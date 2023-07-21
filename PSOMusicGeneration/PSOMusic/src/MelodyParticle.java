import java.util.Random;

/**
 * Class which represents the Melody particle for PSO.
 */
public class MelodyParticle {
    private int position; // position of a particle
    private int velocity; // velocity of a particle
    private int fitness; // it's current fitness
    private int myBest; // best location of the particle
    private int bestFitness; // best fitness that particle ever had

    // constructor
    public MelodyParticle(int min) {
        this.position = new Random().nextInt(12) + min; //set random position within the range
        this.myBest = position;
        this.velocity = 0;
        this.fitness = 0;
        this.bestFitness = 0;
    }

    // getters and setters
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getMyBest() {
        return myBest;
    }

    public void setMyBest(int myBest) {
        this.myBest = myBest;
    }
    public int getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(int bestFitness) {
        this.bestFitness = bestFitness;
    }
}