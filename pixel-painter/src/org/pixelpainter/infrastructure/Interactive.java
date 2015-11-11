package org.pixelpainter.infrastructure;

import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public interface Interactive {

    public Drawing moveTo(Vector v);

    public Drawing move(Vector v);
}
