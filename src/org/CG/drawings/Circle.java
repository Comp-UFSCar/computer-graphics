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

    private int radius;

    /**
     * Adjusts the center of the circle
     *
     * @param point new center of the circle
     * @return this
     */
    @Override
    public Drawing translate(Point point) {
        super.translate(point);
        updateLastCoordinate(point);
        return this;
    }

    /**
     * Adjusts the radius of the circle
     *
     * @param point one of the points at the edge of the circle
     * @return this
     */
    @Override
    public Drawing updateLastCoordinate(Point point) {
        radius = (int) start.euclidianDistance(point);
        return this;
    }

    /**
     * Draws the circle on screen. Uses a variant of Bresenham's Algorithm with
     * GL.GL_LINE_LOOP for filling.
     *
     * @param gl JOGL object
     */
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
            drawCirclePoints(gl, x, y);

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

    private void drawCirclePoints(GL gl, int x, int y) {
        int sx = start.getX();
        int sy = start.getY();
        gl.glVertex2i(sx + x, sy + y);
        gl.glVertex2i(sx - x, sy + y);
        gl.glVertex2i(sx - x, sy - y);
        gl.glVertex2i(sx + x, sy - y);
        gl.glVertex2i(sx + y, sy + x);
        gl.glVertex2i(sx - y, sy + x);
        gl.glVertex2i(sx - y, sy - x);
        gl.glVertex2i(sx + y, sy - x);
    }
}
