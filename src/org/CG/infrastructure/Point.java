package org.CG.infrastructure;

/**
 * Represents an immutable two-dimensional pair of integers
 *
 * @author Diorge-Mephy
 */
public class Point {

    /**
     * The origin (0, 0) point
     */
    public final static Point ORIGIN = new Point(0, 0);

    private final int x;

    private final int y;

    /**
     * Instantiates a new point in the given coordinates
     *
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The x-coordinate of the point
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * The y-coordinate of the point
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Creates a new point by translating this point This point remains
     * unaltered
     *
     * @param dx The movement performed on x-coordinate
     * @param dy The movement performed on y-coordinate
     * @return A new point achieved by translating this point by given values
     */
    public Point move(int dx, int dy) {
        return new Point(x + dx, y + dy);
    }

    /**
     * Creates a new point by changing the x and y-coordinates This is
     * effectively translating the octant
     *
     * @return A new point achieved by translating this point in octant
     */
    public Point invert() {
        return new Point(y, x);
    }

    /**
     * Creates a new point by changing the signal of the y-coordinate
     *
     * @return A new point achieved by mirroring the point upon the X-axis
     */
    public Point mirrorOnHorizontalAxis() {
        return new Point(x, -y);
    }

    /**
     * Creates a new point by changing the signal of the x-coordinate
     *
     * @return A new point achieved by mirroring the point upon the Y-axis
     */
    public Point mirrorOnVerticalAxis() {
        return new Point(-x, y);
    }

    /**
     * Finds the 8 octant values relative to this point (this one included)
     * Equivalent to calling all permutations of {@link #invert() invert},
     * {@link #mirrorOnHorizontalAxis() mirrorOnHorizontalAxis} and
     * {@link #mirrorOnVerticalAxis() mirrorOnVerticalAxis} methods. The points
     * are in octant order, starting at the first octant.
     *
     * @return An array of length 8 with the octant variations of this point
     */
    public Point[] allOctants() {
        return new Point[]{
            this,
            invert(),
            invert().mirrorOnVerticalAxis(),
            mirrorOnVerticalAxis(),
            mirrorOnHorizontalAxis().mirrorOnVerticalAxis(),
            invert().mirrorOnHorizontalAxis().mirrorOnVerticalAxis(),
            invert().mirrorOnHorizontalAxis(),
            mirrorOnHorizontalAxis()
        };
    }

    /**
     * Calculates the Euclidian distance between two points
     *
     * @param distanceTo point to calculate distance
     * @return the distance between this point and the given point
     */
    public final double euclidianDistance(Point distanceTo) {
        double dx = this.getX() - distanceTo.getX();
        double dy = this.getY() - distanceTo.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        return hash;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
