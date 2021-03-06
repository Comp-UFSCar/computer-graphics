package org.pixelpainter.infrastructure.abstractions;

import org.pixelpainter.infrastructure.representations.Vector;
import junit.framework.TestCase;

/**
 * Vector tests.
 *
 * @author ldavid
 */
public class VectorTest extends TestCase {

    public VectorTest(String testName) {
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
     * Test of getX method, of class Vector.
     */
    public void testGetX() {
        System.out.println("getX");

        float expected = -10;
        Vector instance = new Vector(expected, 10, 20);

        float actual = instance.getX();
        assertEquals(expected, actual);
    }

    /**
     * Test of getY method, of class Vector.
     */
    public void testGetY() {
        System.out.println("getX");

        float expected = 10;
        Vector instance = new Vector(-10, expected, 20);

        float actual = instance.getY();
        assertEquals(expected, actual);
    }

    /**
     * Test of getZ method, of class Vector.
     */
    public void testGetZ() {
        System.out.println("getX");

        float expected = 20;
        Vector instance = new Vector(-10, 10, expected);

        float actual = instance.getZ();
        assertEquals(expected, actual);
    }

    /**
     * Test of add method, of class Vector.
     */
    public void testMove_int_int() {
        System.out.println("move");
        int dx = 10;
        int dy = -20;

        Vector expected = new Vector(10, -20, 0);
        Vector actual = Vector.ORIGIN.add(dx, dy);

        assertEquals(expected, actual);
    }

    /**
     * Test of add method, of class Vector.
     */
    public void testMove_3args() {
        System.out.println("move");
        int dx = 10;
        int dy = 20;
        int dz = -20;

        Vector expected = new Vector(10, 20, -20);
        Vector actual = Vector.ORIGIN.add(dx, dy, dz);

        assertEquals(expected, actual);
    }

    /**
     * Test of add method, of class Vector.
     */
    public void testMove_Vector3() {
        System.out.println("move");

        Vector expected = new Vector(50, 10, 10);
        Vector actual = new Vector(40, 5, 1).add(new Vector(10, 5, 9));

        assertEquals(expected, actual);
    }

    /**
     * Test of reflected method, of class Vector.
     */
    public void testReflected() {
        System.out.println("reflected");

        Vector expected = new Vector(-10, -40, 4);
        Vector actual = new Vector(10, 40, -4).reflected();

        assertEquals(expected, actual);
    }

    /**
     * Test of delta method, of class Vector.
     */
    public void testDelta() {
        System.out.println("delta");

        Vector expected = new Vector(-5, 4, 0);
        Vector actual = new Vector(5, 0, -1).delta(new Vector(10, -4, -1));

        assertEquals(expected, actual);
    }

    /**
     * Test of dot method, of class Vector.
     */
    public void testDot() {
        System.out.println("dot");

        float expected = 10;
        float actual = new Vector(10, 2, 0).dot(new Vector(0, 5, 1));

        assertEquals(expected, actual);
    }

    /**
     * Test of cross method, of class Vector.
     */
    public void testCross() {
        System.out.println("cross");

        Vector expected = new Vector(0, 0, 1);
        Vector result = new Vector(1, 0, 0).cross(new Vector(0, 1, 0));

        assertEquals(expected, result);
    }

    /**
     * Test of scale method, of class Vector.
     */
    public void testMult() {
        System.out.println("mult");

        float scalar = 2;
        Vector expected = new Vector(2, 4, 6);
        Vector result = new Vector(1, 2, 3).scale(scalar);
        assertEquals(expected, result);
    }

    /**
     * Test of norm method, of class Vector.
     */
    public void testLength() {
        System.out.println("length");

        float expected = 5;
        float actual = new Vector(3, 4, 0).norm();
        assertEquals(expected, actual, 0.0);
    }

    /**
     * Test of normalize method, of class Vector.
     */
    public void testNormalize() {
        System.out.println("normalize");

        Vector expected = new Vector(3 / 5, 4 / 5, 0);
        Vector result = new Vector(3, 4, 0).normalize();
        assertEquals(expected, result);
    }

    /**
     * Test of l2Distance method, of class Vector.
     */
    public void testL2Distance() {
        System.out.println("euclidianDistance");

        float expected = (float) Math.sqrt(9);
        float actual = new Vector(-1, 1, 5).l2Distance(new Vector(1, 2, 3));

        assertEquals(expected, actual, 0.0001f);
    }

    /**
     * Test of projectTo2d method, of class Vector.
     */
    public void testProjectTo2d() {
        System.out.println("projectTo2d");
        Vector expected = new Vector(10, 20, 0);
        Vector actual = new Vector(10, 20, 0).projectTo2d();

        assertEquals(expected, actual);
    }

    /**
     * Test of equals method, of class Vector.
     */
    public void testEquals() {
        boolean actual;

        actual = new Vector(10, -40, 30).equals(new Vector(10, -40, 30));
        assertTrue(actual);

        actual = new Vector(-10, -4, 5).equals(new Vector(10, -4, 5));
        assertFalse(actual);
    }

    /**
     * Test of toString method, of class Vector.
     */
    public void testToString() {
        System.out.println("toString");

        String expected = "(4, 1, -4)";
        String result = new Vector(4, 1, -4).toString();

        assertEquals(expected, result);
    }

}
