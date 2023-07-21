import java.util.Random;

/**
 * Representation of accompaniment particle which consists of three coordinates as three notes.
 */
public class AccompanimentParticle {
     private Point3D position; // position of a particle
     private Point3D velocity; // velocity of a particle
     private Point3D myBest; // best location of the particle
     private int bestFitness; // best fitness that particle ever had
     private int fitness; // it's current fitness

    // constructor
    public AccompanimentParticle(int min) {
        Random rand = new Random();
        this.position = new Point3D(rand.nextInt(13) + min,
                rand.nextInt(13) + min, rand.nextInt(13) + min);
        this.velocity = new Point3D(1,
                1, 1); // initially velocity is (1, 1, 1)
        bestFitness = -1;
        myBest = new Point3D(min, min, min);
    }

    // setters and getters
    public void setPosition(Point3D position) {
        this.position = position;
    }

    void setVelocity(Point3D velocity) {
        this.velocity = velocity;
    }


    public void setMyBest(Point3D myBest) {
        this.myBest = myBest;
    }


    public void setBestFitness(int bestFitness) {
        this.bestFitness = bestFitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public Point3D getPosition() { return position; }

    public Point3D getVelocity() {
        return velocity;
    }

    public Point3D getMyBest() {
        return myBest;
    }

    public int getBestFitness() {
        return bestFitness;
    }

    public int getFitness() {
        return fitness;
    }
}
