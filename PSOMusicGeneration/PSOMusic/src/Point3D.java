
/**
 * Each coordinate is a note in a chord.
 * For example: C major triad will be the following - x coordinate is 'C', y coordinate is 'E', z coordinate is 'G'
 * */

public class Point3D {
    public int x, y, z;
    Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}