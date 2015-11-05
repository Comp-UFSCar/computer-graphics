package org.CG.infrastructure.structures;

import org.CG.infrastructure.Rational;

/**
 * Node for the Edge Table and Alive Edge Table data structures.
 *
 * @author Diorge-Mephy
 */
public class EdgeNode {

    private final int maximumY;

    private Rational currentX;

    private final Rational delta;

    /**
     * Instantiates a new node.
     *
     * @param maximumY the maximum y-coordinate of the edge.
     * @param xCoordinateOfMinimumY the x-coordinate of the point with the
     * minimum y-coordinate of the edge.
     * @param delta the width of the edge divided by its height.
     */
    public EdgeNode(int maximumY, Rational xCoordinateOfMinimumY, Rational delta) {
        this.maximumY = maximumY;
        this.currentX = xCoordinateOfMinimumY;
        this.delta = delta;
    }

    /**
     * Gets the maximum y-coordinate of the edge.
     *
     * @return the maximum y-coordinate.
     */
    public int getMaximumY() {
        return maximumY;
    }

    /**
     * Gets the current x-coordinate of the scan line.
     *
     * @return the current x-coordinate.
     */
    public Rational getCurrentX() {
        return currentX;
    }

    /**
     * Gets the delta of the edge.
     *
     * @return the delta of the edge.
     */
    public Rational getDelta() {
        return delta;
    }

    /**
     * Moves the current x-coordinate to the next scan line.
     */
    public void goToNextScanLine() {
        this.currentX = this.currentX.add(this.getDelta());
    }

}
