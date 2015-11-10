package org.CG.infrastructure.abstractions;

import org.CG.editor.Camera;

/**
 * Represents a vector in the 3-dimensional space.
 *
 * @author Diorge-Mephy
 */
public class Vector3 {

    /**
     * The origin (0, 0) point
     */
    public final static Vector3 ORIGIN = new Vector3(0, 0);

    private final int x;

    private final int y;

    private final int z;

    /**
     * Instantiates a new point in the given coordinates
     *
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     */
    public Vector3(int x, int y) {
        this(x, y, 0);
    }

    public Vector3(int x, int y, int z) {
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
    public Vector3 move(int dx, int dy) {
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
    public Vector3 move(int dx, int dy, int dz) {
        return new Vector3(x + dx, y + dy, z + dz);
    }

    /**
     * Creates a new point by translating this point. This point remains unaltered.
     *
     * @param v The movement to be performed.
     * @return A new point achieved by translating this point.
     */
    public Vector3 move(Vector3 v) {
        return move(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Reflect the vector.
     *
     * @return the new vector which represents this one reflected.
     */
    public Vector3 reflected() {
        return new Vector3(-x, -y, -z);
    }
    
    /**
     * Delta difference of vector {@code v} and this.
     *
     * @param v the other vector.
     * @return the new vector that represents the difference between {@code v} and this.
     */
    public Vector3 delta(Vector3 v) {
        return move(v.reflected());
    }
    
    /**
     * Creates a new point by changing the x and y-coordinates This is effectively translating the octant
     *
     * @return A new point achieved by translating this point in octant
     */
    public Vector3 invertAxises() {
        return new Vector3(y, x, z);
    }

    /**
     * Creates a new point by changing the signal of the y-coordinate
     *
     * @return A new point achieved by mirroring the point upon the X-axis
     */
    public Vector3 mirrorOnHorizontalAxis() {
        return new Vector3(x, -y);
    }

    /**
     * Creates a new point by changing the signal of the x-coordinate
     *
     * @return A new point achieved by mirroring the point upon the Y-axis
     */
    public Vector3 mirrorOnVerticalAxis() {
        return new Vector3(-x, y);
    }

    /**
     * Finds the 8 octant values relative to this point (this one included) Equivalent to calling all permutations of {@link #invertAxises() invertAxises},
     * {@link #mirrorOnHorizontalAxis() mirrorOnHorizontalAxis} and {@link #mirrorOnVerticalAxis() mirrorOnVerticalAxis}
     * methods. The points are in octant order, starting at the first octant.
     *
     * @return An array of length 8 with the octant variations of this point
     */
    public Vector3[] allOctants() {
        return new Vector3[]{
            this,
            invertAxises(),
            invertAxises().mirrorOnVerticalAxis(),
            mirrorOnVerticalAxis(),
            mirrorOnHorizontalAxis().mirrorOnVerticalAxis(),
            invertAxises().mirrorOnHorizontalAxis().mirrorOnVerticalAxis(),
            invertAxises().mirrorOnHorizontalAxis(),
            mirrorOnHorizontalAxis()
        };
    }

    /**
     * Calculates the Euclidian distance between two points
     *
     * @param distanceTo point to calculate distance
     * @return the distance between this point and the given point
     */
    public final int euclidianDistance(Vector3 distanceTo) {
        int dx = this.getX() - distanceTo.getX();
        int dy = this.getY() - distanceTo.getY();
        int dz = this.getZ() - distanceTo.getZ();

        return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Vector3 projectTo2d() {
        if (z == 0) {
            return new Vector3(x, y);
        }

        float zd = (float) z / (z - Camera.getMainCamera().getPosition().getZ()) + 1;

        return new Vector3((int) (x / zd), (int) (y / zd));
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
        return (int) hash;
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

        final Vector3 other = (Vector3) obj;

        return this.x == other.x
                && this.y == other.y
                && this.z == other.z;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
