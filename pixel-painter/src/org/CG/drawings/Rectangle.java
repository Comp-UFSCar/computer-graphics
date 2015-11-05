package org.CG.drawings;

import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.abstractions.Point;

/**
 * Rectangle drawing using two points.
 *
 * @author ldavid
 */
public class Rectangle extends Drawing {

    protected Point end;

    public Rectangle() {
        super(GL.GL_POLYGON);
    }

    @Override
    public Drawing translate(Point point) {
        Point t = new Point(end.getX() - start.getX(), end.getY() - start.getY());

        start = point;
        end = point.move(t.getX(), t.getY());

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(Point point) {
        end = point;
        return this;
    }

    @Override
    public void drawShape(GL gl) {
        gl.glVertex2i(start.getX(), start.getY());
        gl.glVertex2i(start.getX(), end.getY());
        gl.glVertex2i(end.getX(), end.getY());
        gl.glVertex2i(end.getX(), start.getY());
    }

    @Override
    public Drawing setStart(Point start) {
        super.setStart(start);
        end = start;

        return this;
    }
}
