package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;
import org.CG.infrastructure.Point;

/**
 * Circle drawing with Bresenham's Algorithm.
 *
 * Reference paper: http://web.engr.oregonstate.edu/~sllu/bcircle.pdf
 *
 * @author ldavid
 */
public class Circle extends Drawing {

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
        gl.glBegin(GL.GL_LINE_LOOP);

        int x = radius;
        int y = 0;
        int xChange = 1 - 2 * radius;
        int yChange = 1;
        int radiusError = 0;

        while (x >= y) {
            gl.glVertex2i(start.getX() + x, start.getY() + y);
            gl.glVertex2i(start.getX() - x, start.getY() + y);
            gl.glVertex2i(start.getX() - x, start.getY() - y);
            gl.glVertex2i(start.getX() + x, start.getY() - y);
            gl.glVertex2i(start.getX() + y, start.getY() + x);
            gl.glVertex2i(start.getX() - y, start.getY() + x);
            gl.glVertex2i(start.getX() - y, start.getY() - x);
            gl.glVertex2i(start.getX() + y, start.getY() - x);

            y++;
            radiusError += yChange;
            yChange += 2;
            if (2 * radiusError + xChange > 0) {
                x--;
                radiusError += xChange;
                xChange += 2;
            }
        }

        gl.glEnd();
    }
}
