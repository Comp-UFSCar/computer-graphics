package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;
import org.CG.infrastructure.Point;

/**
 *
 * @author ldavid
 */
public class Circumference extends Drawing {

    int radius;

    @Override
    public Drawing translate(Point point) {
        super.translate(point);
        updateLastCoordinate(point);

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(Point point) {
        radius = (int) start.euclidianDistance(point);

        return this;
    }

    @Override
    public void draw(GL gl) {
        gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
        gl.glBegin(GL.GL_POINTS);

        int d = 1 - radius, x = 0, y = radius;

        circlepoints(gl, x, y);

        while (x < y) {
            if (d <= 0) {
                d += 2 * x + 3;
                x++;
            } else {
                d += 2 * x - 2 * y + 5;
                x++;
                y--;
            }

            circlepoints(gl, x, y);
        }

        gl.glEnd();
    }

    protected void circlepoints(GL gl, int x, int y) {
        gl.glVertex2i(start.getX() + x, start.getY() + y);
        gl.glVertex2i(start.getX() - x, start.getY() + y);
        gl.glVertex2i(start.getX() - x, start.getY() - y);
        gl.glVertex2i(start.getX() + x, start.getY() - y);
        gl.glVertex2i(start.getX() + y, start.getY() + x);
        gl.glVertex2i(start.getX() - y, start.getY() + x);
        gl.glVertex2i(start.getX() - y, start.getY() - x);
        gl.glVertex2i(start.getX() + y, start.getY() - x);
    }

}
