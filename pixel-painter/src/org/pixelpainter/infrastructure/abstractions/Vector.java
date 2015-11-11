package org.CG.infrastructure.abstractions;

import org.CG.infrastructure.Camera;

/**
 * Represents a vector in the 3-dimensional space.
 *
 * @author Diorge-Mephy
 */
public class Vector {

    /**
     * The origin (0, 0) point
     */
    public final static Vector ORIGIN = new Vector(0, 0, 0);
    public final static Vector UP = new Vector(0, 1, 0);
    public final static Vector DOWN = new Vector(0, -1, 0);

    private final int x;

    private final int y;

    private final int z;

    /**
     * Instantiates a new point in the given coordinates
     *
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     */
    public Vector(int x, int y) {
        this(x, y, 0);
    }

    public Vector(int x, int y, int z) {
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
    public Vector move(int dx, int dy) {
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
    public Vector move(int dx, int dy, int dz) {
        return new Vector(x + dx, y + dy, z + dz);
    }

    /**
     * Creates a new point by translating this point. This point remains unaltered.
     *
     * @param v The movement to be performed.
     * @return A new point achieved by translating this point.
     */
    public Vector move(Vector v) {
        return move(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Reflect the vector.
     *
     * @return the new vector which represents this one reflected.
     */
    public Vector reflected() {
        return new Vector(-x, -y, -z);
    }

    /**
     * Delta difference of vector {@code v} and this.
     *
     * @param v the other vector.
     * @return the new vector that represents the difference between {@code v} and this.
     */
    public Vector delta(Vector v) {
        return move(v.reflected());
    }

    /**
     * Dot-product between two vectors.
     *
     * @param v
     * @return
     */
    public int dot(Vector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector cross(Vector v) {
        return new Vector(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    /**
     * Scale vector by a constant {@code scalar}.
     *
     * @param scalar scalar element.
     * @return component-wise multiplication by the scalar.
     */
    public Vector scale(float scalar) {
        return new Vector((int) (scalar * x), (int) (scalar * y), (int) (scalar * z));
    }

    /**
     * Length of the vector. Calculated by the square root of X² + Y² + Z².
     *
     * @return
     */
    public float length() {
        return (float) Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
    }

    /**
     * Normalizes the vector. Divides each component by the length of the vector.
     *
     * @return
     */
    public Vector normalize() {
        return scale(1 / length());
    }

    /**
     * Creates a new point by changing the x and y-coordinates This is effectively translating the octant
     *
     * @return A new point achieved by translating this point in octant
     */
    public Vector invertAxises() {
        return new Vector(y, x, z);
    }

    /**
     * Creates a new point by changing the signal of the y-coordinate
     *
     * @return A new point achieved by mirroring the point upon the X-axis
     */
    public Vector mirrorOnHorizontalAxis() {
        return new Vector(x, -y);
    }

    /**
     * Creates a new point by changing the signal of the x-coordinate
     *
     * @return A new point achieved by mirroring the point upon the Y-axis
     */
    public Vector mirrorOnVerticalAxis() {
        return new Vector(-x, y);
    }

    /**
     * Finds the 8 octant values relative to this point (this one included) Equivalent to calling all permutations of {@link #invertAxises() invertAxises},
     * {@link #mirrorOnHorizontalAxis() mirrorOnHorizontalAxis} and {@link #mirrorOnVerticalAxis() mirrorOnVerticalAxis}
     * methods. The points are in octant order, starting at the first octant.
     *
     * @return An array of length 8 with the octant variations of this point
     */
    public Vector[] allOctants() {
        return new Vector[]{
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
     * Calculates the Euclidian distance induced by the L2-norm between two points.
     *
     * @param v point to calculate distance.
     * @return the distance between this point and the given point.
     */
    public float l2Distance(Vector v) {
        return delta(v).length();
    }

    /**
     * Project Vector to the 2-dimensional space.
     *
     * This projection takes the origin and the camera as basis. It needs, obviously, a main camera to be set in the
     * scene. If it is not, the z-coordinate is simply ignored.
     *
     * @return a new vector which is the projection of this onto the plane (x, y, 0).
     */
    public Vector projectTo2d() {
        float zd = (z != 0 && Camera.getMainCamera() != null)
                ? (float) z / (z - Camera.getMainCamera().getPosition().getZ()) + 1
                : 1;

        return new Vector((int) (x / zd), (int) (y / zd));
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

        final Vector other = (Vector) obj;

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
