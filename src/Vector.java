//Haitam Chouiekh
/**
 * An immutable class representing a two dimensional vector
 * in Cartesian space.
 */
public class Vector {
    // TODO: declare fields
    private final double dx;
    private final double dy;
    /**
     * Construct an empty vector.
     */
    public Vector() {
        dx = 0;
        dy = 0;
    }

    /**
     * Construct a vector with given delta (change of position)
     * @param deltaX change in x coordinate
     * @param deltaY change in y coordinate
     */
    public Vector(double deltaX, double deltaY) {
        this.dx = deltaX;
        this.dy = deltaY;
    }

    /**
     * Create a vector from p1 to p2.
     * @param p1
     * @param p2
     */
    public Vector(Point p1, Point p2) {
        if (p1 == null || p2 == null) throw new NullPointerException("Paremeter cannot be null");
        dx = p2.x() - p1.x();
        dy = p2.y() - p1.y();
    }
    /**
     * @return dx
     */
    public double dx() {
        return dx;
    }
    /**
     * @return dy
     */
    public double dy() {
        return dy;
    }

    /**
     * Return the new point after applying this vector to the argument.
     * @param p old position
     * @return new position
     */
    public Point move(Point p) {
        return new Point(this.dx() + p.x(), this.dy()+p.y());
    }

    /** Compute the magnitude of this vector.
     * How far does it take a point from its origin?
     * @return magnitude
     */
    public double magnitude() {
        return Math.sqrt(dx()*dx()+dy()*dy());
    }

    /**
     * Return a new vector that is the sum of this vector and the parameter.
     * @param other another vector (must not be null)
     * @return new vector that is sum of this and other vectors.
     */
    public Vector add(Vector other) {
        return new Vector(this.dx()+ other.dx(),this.dy()+ other.dy());
    }

    /**
     * Compute a new vector that scales this vector by the given amount.
     * The new vector points in the same direction (unless scale is zero)
     * but has magnitude scaled by the given amount.  If the scale
     * is negative, the new vector points in the <em>opposite</em> direction.
     * @param s scale amount
     * @return new vector that scales this vector
     */
    public Vector scale(double s) {
        return new Vector(dx()*s,dy()*s);
    }

    /**
     * Return a unit vector (one with magnitude 1.0) in the same direction
     * as this vector.  This operation is not defined on the zero vector.
     * @return new vector with unit magnitude in the same direction as this.
     */
    public Vector normalize() {
        return new Vector(dx()*(1/this.magnitude()),dy()*(1/this.magnitude()));
    }
    /**
     * @return string form of vector
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "<"+dx()+","+dy()+">";
    }
}
