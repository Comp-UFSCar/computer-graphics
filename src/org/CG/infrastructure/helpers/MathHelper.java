package org.CG.infrastructure.helpers;

/**
 * Useful math functions for computational graphics
 *
 * @author Diorge-Mephy
 */
public class MathHelper {

    /**
     * Clamps a value between a minimum and maximum values
     *
     * @param min minimum value (must be less than or equal max)
     * @param max maximum value (must be greater than or equal min)
     * @param value value to be clamped
     * @return min if value &lt; min, max if value &gt; max, value otherwise
     */
    public static float clamp(float min, float max, float value) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Clamps a value between a minimum and maximum values
     *
     * @param min minimum value (must be less than or equal max)
     * @param max maximum value (must be greater than or equal min)
     * @param value value to be clamped
     * @return min if value &lt; min, max if value &gt; max, value otherwise
     */
    public static int clamp(int min, int max, int value) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Linear interpolation of t between points v0 and v1
     *
     * @param v0 origin point
     * @param v1 destination point
     * @param t variation (must be between 0 and 1, extremes included)
     * @return The linear interpolation of t
     */
    public static float lerp(float v0, float v1, float t) {
        return (1 - t) * v0 + t * v1;
    }

    /**
     * Normalizes a value t from the spectrum [v0, v1] to [0, 1]
     *
     * @param v0 minimum value that t can hold
     * @param v1 maximum value that t can hold
     * @param t value to be normalized
     * @return Normalization of the value, between 0 and 1
     */
    public static float normalize(float v0, float v1, float t) {
        return (t - v0) / (v1 - v0);
    }

    /**
     * Calculates the greatest common divisor by using the Euclidean Algorithm.
     *
     * @param a an integer greater than zero
     * @param b an integer greater than zero
     * @return the GCD between a and b
     */
    public static int greatestCommonDivisor(int a, int b) {
        if (a <= 0) {
            throw new IllegalArgumentException("a");
        }
        if (b <= 0) {
            throw new IllegalArgumentException("b");
        }
        while (a != b) {
            if (a > b) {
                a -= b;
            } else {
                b -= a;
            }
        }
        return a;
    }

    /**
     * Calculates the least common multiple between two integers.
     *
     * @param a an integer greater than zero
     * @param b an integer greater than zero
     * @return the least common multiple between a and b
     */
    public static int leastCommonMultiple(int a, int b) {
        int dem = greatestCommonDivisor(a, b);
        int num = Math.abs(a * b);
        return num / dem;
    }

}
