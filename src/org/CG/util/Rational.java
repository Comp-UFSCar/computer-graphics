package org.CG.util;

/**
 * Represents an immutable Rational number (fraction) of form
 * Integer+(Numerator/Denominator), where Numerator is strictly less than
 * Denominator, and Denominator not equal zero.
 *
 * @author Diorge-Mephy
 */
public final class Rational {

    private final int integer;

    private final int numerator;

    private final int denominator;

    /**
     * Instantiates a rational, making sure Numerator is less than Denominator
     * (the leftover is used as Integer).
     *
     * @param num numerator of the fraction
     * @param den denominator of the fraction
     */
    public Rational(int num, int den) {
        if (den == 0) {
            throw new IllegalArgumentException("den");
        }
        this.integer = num / den;
        this.numerator = num % den;
        this.denominator = den;
    }

    /**
     * Instantiates a rational, making sure Numerator is less than Denominator
     * (the leftover is added to integer and used as Integer).
     *
     * @param integer integral value of the rational
     * @param num numerator of the fraction
     * @param den denominator of the fraction
     */
    public Rational(int integer, int num, int den) {
        this(num + integer * den, den);
    }

    /**
     * Instantiates a rational with only an integral part and no fraction
     * (equivalent to using Numerator zero and Denominator 1).
     *
     * @param integer integral value of the rational
     */
    public Rational(int integer) {
        this.integer = integer;
        this.numerator = 0;
        this.denominator = 1;
    }

    /**
     * Gets the integral part of the rational number
     *
     * @return integral part (Integer)
     */
    public int getInteger() {
        return integer;
    }

    /**
     * Gets the numerator of the fraction
     *
     * @return Numerator
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Gets the denominator of the fraction
     *
     * @return Denominator
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Creates a new equivalent Rational with a positive Denominator.
     *
     * @return a Rational with positive Denominator
     */
    public Rational adjustSign() {
        if (denominator < 0) {
            return new Rational(integer, -numerator, -denominator);
        }
        return this;
    }

    /**
     * Creates a new Rational from the sum of two Rational numbers.
     *
     * @param other the number to be added to this
     * @return this + other
     */
    public Rational add(Rational other) {
        Rational lv = this.adjustSign();
        Rational rv = other.adjustSign();
        int lcm = MathHelper.leastCommonMultiple(lv.denominator, rv.denominator);
        return new Rational(
                lv.integer + rv.integer,
                (lcm / lv.denominator) * lv.numerator
                + (lcm / rv.denominator) * rv.numerator,
                lcm);
    }

}
