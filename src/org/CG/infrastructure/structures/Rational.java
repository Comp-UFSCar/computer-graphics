package org.CG.infrastructure.structures;

import org.CG.infrastructure.helpers.MathHelper;

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
            throw new IllegalArgumentException("Denominator cannot be 0.");
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
     * Creates a new, reduced Rational number.
     *
     * @return reduced version of this
     */
    public Rational reduce() {
        if (numerator == 0) {
            return new Rational(integer, 0, 1);
        }
        Rational adjusted = this.adjustSign();
        int gcd = MathHelper.greatestCommonDivisor(Math.abs(adjusted.numerator), adjusted.denominator);

        return new Rational(adjusted.integer, adjusted.numerator / gcd, adjusted.denominator / gcd);
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

    /**
     * Creates a new Rational from the subtraction of two Rational numbers.
     *
     * @param other the number to be added to this
     * @return this - other
     */
    public Rational sub(Rational other) {
        return add(new Rational(-other.getInteger(), -other.getNumerator(), other.getDenominator()));
    }

    /**
     * Checks if this Rational number if greater or equal than @other.
     * 
     * @param other the number to be compared against.
     * @return true, if this is greater or equal than other. False, otherwise.
     */
    public boolean gte(Rational other) {
        Rational result = sub(other).adjustSign();
        return result.getInteger() > 0 || result.getInteger() == 0 && result.getNumerator() >= 0;
    }

    /**
     * Checks if this Rational number if greater than @other.
     * 
     * @param other the number to be compared against.
     * @return true, if this is greater than other. False, otherwise.
     */
    public boolean gt(Rational other) {
        Rational result = sub(other).adjustSign();
        return result.getInteger() > 0 || result.getInteger() == 0 && result.getNumerator() > 0;
    }

    /**
     * Create a new Rational that's the ceil of this Rational.
     * 
     * @return ceil(x)
     */
    public Rational ceil() {
        return add(new Rational(numerator == 0 ? 0 : 1)).truncate();
    }

    public Rational truncate() {
        return new Rational(reduce().integer);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        final Rational reduced = this.reduce();
        hash = 97 * hash + reduced.integer;
        hash = 97 * hash + reduced.numerator;
        hash = 97 * hash + reduced.denominator;
        return hash;
    }

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
        final Rational other = ((Rational) obj).reduce();
        final Rational reduced = this.reduce();
        if (reduced.integer != other.integer) {
            return false;
        }
        if (reduced.numerator != other.numerator) {
            return false;
        }
        return reduced.denominator == other.denominator;
    }

    @Override
    public String toString() {
        return String.format("%d + (%d / %d)", integer, numerator, denominator);
    }

}
