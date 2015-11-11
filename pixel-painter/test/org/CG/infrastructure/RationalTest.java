package org.pixelpainter.infrastructure;

import org.pixelpainter.infrastructure.representations.Rational;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Diorge-Mephy
 */
public class RationalTest extends TestCase {

    public RationalTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test the constructor with one integer parameter, of class Rational.
     */
    @Test
    public void testRationalConstructor_fromInteger() {
        int value = 3;
        int expInt = 3;
        int expNum = 0;
        int expDen = 1;
        Rational result = new Rational(value);
        assertEquals(expInt, result.getInteger());
        assertEquals(expNum, result.getNumerator());
        assertEquals(expDen, result.getDenominator());
    }

    /**
     * Test the constructor with two integer parameters, of class Rational.
     */
    @Test
    public void testRationalConstructor_fromFraction_numeratorLessThanDenominator() {
        int num = 2;
        int den = 3;
        int expInt = 0;
        int expNum = 2;
        int expDen = 3;
        Rational result = new Rational(num, den);
        assertEquals(expInt, result.getInteger());
        assertEquals(expNum, result.getNumerator());
        assertEquals(expDen, result.getDenominator());
    }

    /**
     * Test the constructor with three integer parameters, of class Rational.
     */
    @Test
    public void testRationalConstructor_fromFraction_numeratorGreaterThanDenominator() {
        int integer = 2;
        int num = 7;
        int den = 2;
        int expInt = 5;
        int expNum = 1;
        int expDen = 2;
        Rational result = new Rational(integer, num, den);
        assertEquals(expInt, result.getInteger());
        assertEquals(expNum, result.getNumerator());
        assertEquals(expDen, result.getDenominator());
    }

    /**
     * Test of adjustSign method, of class Rational.
     */
    @Test
    public void testAdjustSign_positiveDenominator() {
        Rational instance = new Rational(3, 2);
        Rational expResult = new Rational(3, 2);
        Rational result = instance.adjustSign();
        assertEquals(expResult, result);
    }

    /**
     * Test of adjustSign method, of class Rational.
     */
    @Test
    public void testAdjustSign_negativeDenominator() {
        Rational instance = new Rational(3, -2);
        Rational expResult = new Rational(-3, 2);
        Rational result = instance.adjustSign();
        assertEquals(expResult, result);
    }

    /**
     * Test of reduce method, of class Rational.
     */
    @Test
    public void testReduce() {
        Rational instance = new Rational(10, 4);
        Rational expResult = new Rational(5, 2);
        Rational result = instance.reduce();
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class Rational.
     */
    @Test
    public void testAdd_sameDenominator() {
        Rational instance = new Rational(3, 4);
        Rational other = new Rational(7, 4);
        Rational expResult = new Rational(10, 4);
        Rational result = instance.add(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class Rational.
     */
    @Test
    public void testAdd_diffDenominator() {
        Rational instance = new Rational(3, 21);
        Rational other = new Rational(5, 6);
        Rational expResult = new Rational(41, 42);
        Rational result = instance.add(other);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Rational.
     */
    @Test
    public void testToString() {
        Rational instance = new Rational(5, 2);
        String expResult = "2 + (1 / 2)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    public void testSub() {
        Rational a = new Rational(4, 1, 3), b = new Rational(4, 2, 3),
            expected = new Rational(0, -1, 3);
        
        Rational actual = a.sub(b);
        
        assertEquals(expected, actual);
    }
    
    public void testGreaterOrEqualThan() {
        Rational a, b;
        boolean expected;
        
        a = new Rational(0);
        b = new Rational(3);
        expected = false;
        
        assertEquals(expected, a.gte(b));
        
        a = new Rational(0, 1, 4);
        b = new Rational(3);
        expected = false;
        
        assertEquals(expected, a.gte(b));
        
        a = new Rational(4, 3, 4);
        b = new Rational(4, 3, 5);
        expected = true;
        
        assertEquals(expected, a.gte(b));
    }
}
