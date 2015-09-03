package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;
import org.CG.infrastructure.Point;

/**
 * Circumference drawing with Bresenham's algorithm
 * 
 * @author ldavid
 */
public class Circumference extends Drawing {

    private int radius;

    /**
     * Adjusts the center of the circumference.
     * 
     * @param point new center of the circumference
     * @return this
     */
    @Override
    public Drawing translate(Point point) {
        super.translate(point);
        updateLastCoordinate(point);
        return this;
    }

    /**
     * Adjusts the radius of the circumference.
     * 
     * @param point any point of the circumference (edge)
     * @return this
     */
    @Override
    public Drawing updateLastCoordinate(Point point) {
        radius = (int) start.euclidianDistance(point);
        return this;
    }

    /**
     * Draws the circumference on screen. Uses the Bresenham's Middle-Point Algorithm.
     * @param gl JOGL object
     */
    @Override
    public void draw(GL gl) {
        gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
        gl.glBegin(GL.GL_POINTS);

        int d = 1 - radius, x = 0, y = radius;

        drawCirclePoints(gl, x, y);

        while (x < y) {
            if (d <= 0) {
                d += 2 * x + 3;
                x++;
            } else {
                d += 2 * x - 2 * y + 5;
                x++;
                y--;
            }

            drawCirclePoints(gl, x, y);
        }

        gl.glEnd();
    }

    protected void drawCirclePoints(GL gl, int x, int y) {
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
