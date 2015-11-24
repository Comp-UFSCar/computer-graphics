package org.cg.aquarium.infrastructure.representations;

import java.util.Random;

/**
 * Represents a vector in the 3-dimensional space.
 *
 * @author Diorge-Mephy
 */
public class Vector {

    /**
     * The origin (0, 0) vector
     */
    public final static Vector ORIGIN = new Vector(0, 0, 0);
    public final static Vector UP = new Vector(0, 1, 0);
    public final static Vector DOWN = new Vector(0, -1, 0);
    public final static Vector LEFT = new Vector(-1, 0, 0);
    public final static Vector RIGHT = new Vector(1, 0, 0);

    private final float x;

    private final float y;

    private final float z;

    /**
     * Instantiates a new vector in the given coordinates
     *
     * @param x x-coordinate of the vector
     * @param y y-coordinate of the vector
     */
    public Vector(float x, float y) {
        this(x, y, 0);
    }

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * The x-coordinate of the vector
     *
     * @return the x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * The y-coordinate of the vector
     *
     * @return the y-coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * The z-coordinate of the vector
     *
     * @return the z-coordinate
     */
    public float getZ() {
        return z;
    }

    /**
     * Creates a new vector by translating this vector and ignoring the z
     * coordinate. This vector remains unaltered.
     *
     * @param dx The movement performed on x-coordinate
     * @param dy The movement performed on y-coordinate
     * @return A new vector achieved by translating this vector by given values
     */
    public Vector add(float dx, float dy) {
        return Vector.this.add(dx, dy, 0);
    }

    /**
     * Creates a new vector by translating this vector. This vector remains
     * unaltered.
     *
     * @param dx The movement performed on x-coordinate
     * @param dy The movement performed on y-coordinate
     * @param dz The movement performed on z-coordinate
     * @return A new vector achieved by translating this vector by given values
     */
    public Vector add(float dx, float dy, float dz) {
        return new Vector(x + dx, y + dy, z + dz);
    }

    /**
     * Creates a new vector by translating this vector. This vector remains
     * unaltered.
     *
     * @param v The movement to be performed.
     * @return A new vector achieved by translating this vector.
     */
    public Vector add(Vector v) {
        return Vector.this.add(v.getX(), v.getY(), v.getZ());
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
     * @return the new vector that represents the difference between {@code v}
     * and this.
     */
    public Vector delta(Vector v) {
        return Vector.this.add(v.reflected());
    }

    /**
     * Dot-product between two vectors.
     *
     * @param v the other vector.
     * @return float the dot-product between two vectors.
     */
    public float dot(Vector v) {
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
        return new Vector((float) (scalar * x), (float) (scalar * y), (float) (scalar * z));
    }

    /**
     * Norm of the vector.
     *
     * Calculated by the square root of X² + Y² + Z².
     *
     * @return float the norm of this vector.
     */
    public float norm() {
        return (float) Math.sqrt(dot(this));
    }

    /**
     * Normalizes the vector.
     *
     * Divides each component by the norm of the vector.
     *
     * @return the vector normalized.
     */
    public Vector normalize() {
        return scale(1 / norm());
    }

    /**
     * Creates a new vector by changing the x and y-coordinates This is
     * effectively translating the octant
     *
     * @return A new vector achieved by translating this vector in octant
     */
    public Vector invertAxises() {
        return new Vector(y, x, z);
    }

    /**
     * Creates a new vector by changing the signal of the y-coordinate.
     *
     * @return A new vector achieved by mirroring the vector upon the X-axis.
     */
    public Vector mirrorOnHorizontalAxis() {
        return new Vector(x, -y);
    }

    /**
     * Creates a new vector by changing the signal of the x-coordinate.
     *
     * @return A new vector achieved by mirroring the vector upon the Y-axis.
     */
    public Vector mirrorOnVerticalAxis() {
        return new Vector(-x, y);
    }

    /**
     * Finds the 8 octant values relative to this vector (this one included)
     * Equivalent to calling all permutations of
     * {@link #invertAxises() invertAxises}, {@link #mirrorOnHorizontalAxis() mirrorOnHorizontalAxis}
     * and {@link #mirrorOnVerticalAxis() mirrorOnVerticalAxis} methods.
     *
     * The vectors are in octant order, starting at the first octant.
     *
     * @return An array of norm 8 with the octant variations of this vector.
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
     * Calculates the Euclidian distance induced by the L2-norm between two
     * vectors.
     *
     * @param v vector to calculate distance.
     * @return the distance between this vector and the given vector.
     */
    public float l2Distance(Vector v) {
        return delta(v).norm();
    }

    /**
     * Returns a vector which contains the this vector's coordinates truncated.
     *
     * @return Vector with truncated coordinates.
     */
    public Vector truncate() {
        return new Vector((int) x, (int) y, (int) z);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        float hash = 7;
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

    public static Vector random(Random r) {
        return new Vector(
                (r.nextBoolean() ? 1 : -1) * r.nextFloat(),
                (r.nextBoolean() ? 1 : -1) * r.nextFloat(),
                (r.nextBoolean() ? 1 : -1) * r.nextFloat());
    }
}
