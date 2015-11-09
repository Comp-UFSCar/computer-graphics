package org.CG.infrastructure.abstractions;

import org.CG.editor.Camera;

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

    private final int z;

    /**
     * Instantiates a new point in the given coordinates
     *
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     */
    public Point(int x, int y) {
        this(x, y, 0);
    }

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
     * The z-coordinate of the point
     *
     * @return the z-coordinate
     */
    public int getZ() {
        return z;
    }

    /**
     * Creates a new point by translating this point and ignoring the z coordinate. This point remains unaltered.
     *
     * @param dx The movement performed on x-coordinate
     * @param dy The movement performed on y-coordinate
     * @return A new point achieved by translating this point by given values
     */
    public Point move(int dx, int dy) {
        return move(dx, dy, 0);
    }

    /**
     * Creates a new point by translating this point. This point remains unaltered.
     *
     * @param dx The movement performed on x-coordinate
     * @param dy The movement performed on y-coordinate
     * @param dz The movement performed on z-coordinate
     * @return A new point achieved by translating this point by given values
     */
    public Point move(int dx, int dy, int dz) {
        return new Point(x + dx, y + dy, z + dz);
    }

    /**
     * Creates a new point by translating this point. This point remains unaltered.
     *
     * @param dp The movement to be performed
     * @return A new point achieved by translating this point
     */
    public Point move(Point dp) {
        return move(dp.getX(), dp.getY(), dp.getZ());
    }

    /**
     * Creates a new point by changing the x and y-coordinates This is effectively translating the octant
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
     * Finds the 8 octant values relative to this point (this one included) Equivalent to calling all permutations of {@link #invert() invert},
     * {@link #mirrorOnHorizontalAxis() mirrorOnHorizontalAxis} and {@link #mirrorOnVerticalAxis() mirrorOnVerticalAxis}
     * methods. The points are in octant order, starting at the first octant.
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
        double dz = this.getZ() - distanceTo.getZ();

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Point projectTo2d() {
        if (z == 0) {
            return new Point(x, y);
        }

        Point cameraPosition = Camera.getMainCamera().getPosition();
        
        float fz = ((float)z - cameraPosition.getZ())/z;

        return new Point(
            (int)((x - cameraPosition.getX()) * fz) + cameraPosition.getX(),
            (int)((y - cameraPosition.getY()) * fz) + cameraPosition.getX()
        );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        hash = 59 * hash + this.z;
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Point other = (Point) obj;

        return this.x == other.x
                && this.y == other.y
                && this.z == other.z;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
