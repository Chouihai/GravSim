//Haitam Chouiekh
/**
 * Immutable location in two dimensional space.
 */
public class Point {
    private final double x;
    private final double y;

    /**
     * Create a point at the given (x,y) coordinates
     * @param xCoord x coordinate
     * @param yCoord y coordinate
     */
    public Point(double xCoord, double yCoord) {
        this.x = xCoord;
        this.y = yCoord;
    }

    // Getters for x and y (see assignment)
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    /**
     * Round this point to the closest AWT point (using integer coordinates).
     * @return rounded point
     */
    public java.awt.Point asAWT() {
        return new java.awt.Point(Math.round((float)x()), Math.round((float)y()));
    }

    /**
     * Compute the distance (never negative) between two points.
     * @param other another point, must not be null
     * @return distance between points.
     */
    public double distance(Point other) {
        Vector v = new Vector(this,other);
        return v.magnitude();
    }

    /**
     * Return string of the form (x,y)
     * @see java.lang.Object#toString()
     * @return string form of point
     */
    @Override //implementation
    public String toString() {
        return "("+this.x()+","+this.y()+")";
    }
}
