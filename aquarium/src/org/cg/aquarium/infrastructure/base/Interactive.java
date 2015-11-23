package org.cg.infrastructure.base;

import org.cg.infrastructure.representations.Vector;

/**
 * Implementations of this interface will move onto the canvas.
 *
 * @author ldavid
 */
public interface Interactive {

    /**
     * Move object to a given point in space.
     *
     * @param v the point to where the object should be moved.
     */
    public void moveTo(Vector v);

    public void move(Vector v);
}
