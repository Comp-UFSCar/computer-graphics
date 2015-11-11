package org.pixelpainter.drawing.shapes;

import javax.media.opengl.GL;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 * Square drawing using a restrictive rectangle.
 *
 * @author ldavid
 */
public class Square extends Rectangle {

    public Square() {
        this(GL.GL_POLYGON);
    }

    public Square(int drawingMethod) {
        super(drawingMethod);
    }

    /**
     * Update last coordinate based on point, but maintaining proportion of 1.0 for sides.
     *
     * @param point coordinate to where the square should be moved.
     * @return this.
     */
    @Override
    public Drawing updateLastCoordinate(Vector point) {
        int dx = (int) (point.getX() - start.getX());
        int dy = (int) (point.getY() - start.getY());

        end = Math.abs(dx) > Math.abs(dy)
                ? start.move(dx, (int) Math.signum(dy) * Math.abs(dx))
                : start.move((int) Math.signum(dx) * Math.abs(dy), dy);

        return this;
    }
}
