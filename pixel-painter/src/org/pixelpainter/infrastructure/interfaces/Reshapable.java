package org.pixelpainter.infrastructure.interfaces;

import org.pixelpainter.infrastructure.representations.Vector;

/**
 * Implementations of this interface will allow reshaping.
 * 
 * @author ldavid
 */
public interface Reshapable {

    /**
     * Reshape th object according to a parameter {@code Vector v}.
     *
     * @param v the distances and coefficients of the scaling to be performed.
     */
    public void reshape(Vector v);
}
