package org.CG.drawings;

import org.CG.infrastructure.base.Drawing;
import org.CG.infrastructure.structures.Point;

/**
 * Square drawing using a restrictive rectangle.
 *
 * @author ldavid
 */
public class Square extends Rectangle {

    /**
     * Update last coordinate based on point, but maintaining proportion
     * of 1.0 for sides.
     *
     * @param point coordinate to where the square should be moved.
     * @return this.
     */
    @Override
    public Drawing updateLastCoordinate(Point point) {
        int dx = point.getX() - start.getX();
        int dy = point.getY() - start.getY();

        end = Math.abs(dx) > Math.abs(dy)
            ? start.move(dx, (int) Math.signum(dy) * Math.abs(dx))
            : start.move((int) Math.signum(dx) * Math.abs(dy), dy);

        return this;
    }
}
