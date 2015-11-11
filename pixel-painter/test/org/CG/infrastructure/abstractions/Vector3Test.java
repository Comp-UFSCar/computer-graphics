package org.CG.infrastructure.abstractions;

import junit.framework.TestCase;

/**
 * Vector3 tests.
 *
 * @author ldavid
 */
public class Vector3Test extends TestCase {

    public Vector3Test(String testName) {
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
     * Test of getX method, of class Vector3.
     */
    public void testGetX() {
        System.out.println("getX");

        int expected = -10;
        Vector3 instance = new Vector3(expected, 10, 20);

        int actual = instance.getX();
        assertEquals(expected, actual);
    }

    /**
     * Test of getY method, of class Vector3.
     */
    public void testGetY() {
        System.out.println("getX");

        int expected = 10;
        Vector3 instance = new Vector3(-10, expected, 20);

        int actual = instance.getY();
        assertEquals(expected, actual);
    }

    /**
     * Test of getZ method, of class Vector3.
     */
    public void testGetZ() {
        System.out.println("getX");

        int expected = 20;
        Vector3 instance = new Vector3(-10, 10, expected);

        int actual = instance.getZ();
        assertEquals(expected, actual);
    }

    /**
     * Test of move method, of class Vector3.
     */
    public void testMove_int_int() {
        System.out.println("move");
        int dx = 10;
        int dy = -20;

        Vector3 expected = new Vector3(10, -20, 0);
        Vector3 actual = Vector3.ORIGIN.move(dx, dy);

        assertEquals(expected, actual);
    }

    /**
     * Test of move method, of class Vector3.
     */
    public void testMove_3args() {
        System.out.println("move");
        int dx = 10;
        int dy = 20;
        int dz = -20;

        Vector3 expected = new Vector3(10, 20, -20);
        Vector3 actual = Vector3.ORIGIN.move(dx, dy, dz);

        assertEquals(expected, actual);
    }

    /**
     * Test of move method, of class Vector3.
     */
    public void testMove_Vector3() {
        System.out.println("move");

        Vector3 expected = new Vector3(50, 10, 10);
        Vector3 actual = new Vector3(40, 5, 1).move(new Vector3(10, 5, 9));

        assertEquals(expected, actual);
    }

    /**
     * Test of reflected method, of class Vector3.
     */
    public void testReflected() {
        System.out.println("reflected");

        Vector3 expected = new Vector3(-10, -40, 4);
        Vector3 actual = new Vector3(10, 40, -4).reflected();

        assertEquals(expected, actual);
    }

    /**
     * Test of delta method, of class Vector3.
     */
    public void testDelta() {
        System.out.println("delta");

        Vector3 expected = new Vector3(-5, 4, 0);
        Vector3 actual = new Vector3(5, 0, -1).delta(new Vector3(10, -4, -1));

        assertEquals(expected, actual);
    }

    /**
     * Test of dot method, of class Vector3.
     */
    public void testDot() {
        System.out.println("dot");

        int expected = 10;
        int actual = new Vector3(10, 2, 0).dot(new Vector3(0, 5, 1));

        assertEquals(expected, actual);
    }

    /**
     * Test of cross method, of class Vector3.
     */
    public void testCross() {
        System.out.println("cross");

        Vector3 expected = new Vector3(0, 0, 1);
        Vector3 result = new Vector3(1, 0, 0).cross(new Vector3(0, 1, 0));

        assertEquals(expected, result);
    }

    /**
     * Test of scale method, of class Vector3.
     */
    public void testMult() {
        System.out.println("mult");

        float scalar = 2;
        Vector3 expected = new Vector3(2, 4, 6);
        Vector3 result = new Vector3(1, 2, 3).scale(scalar);
        assertEquals(expected, result);
    }

    /**
     * Test of length method, of class Vector3.
     */
    public void testLength() {
        System.out.println("length");

        float expected = 5;
        float actual = new Vector3(3, 4, 0).length();
        assertEquals(expected, actual, 0.0);
    }

    /**
     * Test of normalize method, of class Vector3.
     */
    public void testNormalize() {
        System.out.println("normalize");

        Vector3 expected = new Vector3(3 / 5, 4 / 5, 0);
        Vector3 result = new Vector3(3, 4, 0).normalize();
        assertEquals(expected, result);
    }

    /**
     * Test of l2Distance method, of class Vector3.
     */
    public void testL2Distance() {
        System.out.println("euclidianDistance");

        float expected = (float) Math.sqrt(9);
        float actual = new Vector3(-1, 1, 5).l2Distance(new Vector3(1, 2, 3));

        assertEquals(expected, actual, 0.0001f);
    }

    /**
     * Test of projectTo2d method, of class Vector3.
     */
    public void testProjectTo2d() {
        System.out.println("projectTo2d");
        Vector3 expected = new Vector3(10, 20, 0);
        Vector3 actual = new Vector3(10, 20, 0).projectTo2d();

        assertEquals(expected, actual);
    }

    /**
     * Test of equals method, of class Vector3.
     */
    public void testEquals() {
        boolean actual;

        actual = new Vector3(10, -40, 30).equals(new Vector3(10, -40, 30));
        assertTrue(actual);

        actual = new Vector3(-10, -4, 5).equals(new Vector3(10, -4, 5));
        assertFalse(actual);
    }

    /**
     * Test of toString method, of class Vector3.
     */
    public void testToString() {
        System.out.println("toString");

        String expected = "(4, 1, -4)";
        String result = new Vector3(4, 1, -4).toString();

        assertEquals(expected, result);
    }

}
