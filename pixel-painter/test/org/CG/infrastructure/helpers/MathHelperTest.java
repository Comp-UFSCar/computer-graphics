package org.CG.infrastructure.helpers;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Test cases for Math Helper utility functions
 *
 * @author Diorge-Mephy
 */
public class MathHelperTest extends TestCase {

    public MathHelperTest(String testName) {
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
     * Test of clamp method for float overload, of class MathHelper Value is
     * between min and max
     */
    @Test
    public void testClamp_float_inRange() {
        float min = 0.0f;
        float max = 255.0f;
        float value = 122.0f;
        float expResult = 122.0f;
        float result = MathHelper.clamp(min, max, value);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of clamp method for float overload, of class MathHelper Value is
     * lower than min
     */
    @Test
    public void testClamp_float_lower() {
        float min = 10.0f;
        float max = 100.0f;
        float value = 0.0f;
        float expResult = 10.0f;
        float result = MathHelper.clamp(min, max, value);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of clamp method for float overload, of class MathHelper Value is
     * higher than max
     */
    @Test
    public void testClamp_float_higher() {
        float min = 0.0f;
        float max = 100.0f;
        float value = 150.0f;
        float expResult = 100.0f;
        float result = MathHelper.clamp(min, max, value);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of clamp method for int overload, of class MathHelper Value is
     * between min and max
     */
    @Test
    public void testClamp_int_inRange() {
        int min = 0;
        int max = 255;
        int value = 122;
        int expResult = 122;
        int result = MathHelper.clamp(min, max, value);
        assertEquals(expResult, result);
    }

    /**
     * Test of clamp method for int overload, of class MathHelper Value is lower
     * than min
     */
    @Test
    public void testClamp_int_lower() {
        int min = 10;
        int max = 100;
        int value = 0;
        int expResult = 10;
        int result = MathHelper.clamp(min, max, value);
        assertEquals(expResult, result);
    }

    /**
     * Test of clamp method for int overload, of class MathHelper Value is
     * higher than max
     */
    @Test
    public void testClamp_int_higher() {
        int min = 0;
        int max = 100;
        int value = 150;
        int expResult = 100;
        int result = MathHelper.clamp(min, max, value);
        assertEquals(expResult, result);
    }

    /**
     * Test of lerp method, of class MathHelper. t = v0
     */
    @Test
    public void testLerp_onOrigin() {
        float v0 = 5.0f;
        float v1 = 10.0f;
        float t = 0.0f;
        float expResult = 5.0f;
        float result = MathHelper.lerp(v0, v1, t);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of lerp method, of class MathHelper. t = v1
     */
    @Test
    public void testLerp_onDestination() {
        float v0 = 5.0f;
        float v1 = 10.0f;
        float t = 1.0f;
        float expResult = 10.0f;
        float result = MathHelper.lerp(v0, v1, t);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of lerp method, of class MathHelper. t is between v0 and v1
     */
    @Test
    public void testLerp_betweenPoints() {
        float v0 = 5.0f;
        float v1 = 10.0f;
        float t = 0.5f;
        float expResult = 7.5f;
        float result = MathHelper.lerp(v0, v1, t);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of normalize method, of class MathHelper. t = v0
     */
    @Test
    public void testNormalize_onMinimum() {
        float v0 = 5.0f;
        float v1 = 10.0f;
        float t = 5.0f;
        float expResult = 0.0f;
        float result = MathHelper.normalize(v0, v1, t);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of normalize method, of class MathHelper. t = v1
     */
    @Test
    public void testNormalize_onMaximum() {
        float v0 = 5.0f;
        float v1 = 10.0f;
        float t = 10.0f;
        float expResult = 1.0f;
        float result = MathHelper.normalize(v0, v1, t);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of normalize method, of class MathHelper. t is between v0 and v1
     */
    @Test
    public void testNormalize_betweenPoints() {
        float v0 = 5.0f;
        float v1 = 10.0f;
        float t = 7.5f;
        float expResult = 0.5f;
        float result = MathHelper.normalize(v0, v1, t);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test GCD when a is less than or equal zero.
     */
    @Test
    public void testGreatestCommonDivisor_aBelowOne() {
        int a = 0;
        int b = 5;
        try {
            int result = MathHelper.greatestCommonDivisor(a, b);
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException ex) {
        }
    }

    /**
     * Test GCD when b is less than or equal zero.
     */
    @Test
    public void testGreatestCommonDivisor_bBelowOne() {
        int a = 2;
        int b = -5;
        try {
            int result = MathHelper.greatestCommonDivisor(a, b);
            fail("Expected exception not thrown");
        } catch (IllegalArgumentException ex) {
        }
    }

    /**
     * Test the Greatest Common Divisor.
     */
    @Test
    public void testGreatestCommonDivisor() {
        int a = 8;
        int b = 12;
        int expResult = 4;
        int result = MathHelper.greatestCommonDivisor(a, b);
        assertEquals(expResult, result);
    }

    /**
     * Test the Least Common Multiple.
     */
    @Test
    public void testLeastCommonMultiple() {
        int a = 21;
        int b = 6;
        int expResult = 42;
        int result = MathHelper.leastCommonMultiple(a, b);
        assertEquals(expResult, result);
    }

}
