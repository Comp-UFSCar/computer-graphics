package org.CG.drawings;

import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.Point;

/**
 *
 * @author ldavid
 */
public class Rectangle extends Drawing {

    private Point end;

    @Override
    public Drawing translate(Point point) {
        Point t = new Point(end.getX() - start.getX(), end.getY() - start.getY());

        start = point;
        end = point.move(t.getX(), t.getX());

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(Point point) {
        end = point;
        return this;
    }

    @Override
    public void draw(GL gl) {
        if (end == null) {
            return;
        }

        gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2i(start.getX(), start.getY());
        gl.glVertex2i(start.getX(), end.getY());
        gl.glVertex2i(end.getX(), end.getY());
        gl.glVertex2i(end.getX(), start.getY());

        gl.glEnd();
    }

}
