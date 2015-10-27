package org.CG.infrastructure.structures;

/**
 *
 * @author ldavid
 */
public class Vector {

    double x, y, z;

    public Vector() {
        this(0, 0, 0);
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector mul(double lambda) {
        return new Vector(lambda * x, lambda * y, lambda * z);
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    public double[] asArray() {
        return new double[]{x, y, z};
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
