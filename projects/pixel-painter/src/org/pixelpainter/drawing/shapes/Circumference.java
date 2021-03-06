package org.pixelpainter.drawing.shapes;

import javax.media.opengl.GL;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 * Circumference drawing with Bresenham's algorithm.
 *
 * Reference paper: http://web.engr.oregonstate.edu/~sllu/bcircle.pdf
 *
 * @author ldavid
 */
public class Circumference extends Drawing {

    private int radius;

    /**
     * Instantiates a new Circumference.
     */
    public Circumference() {
        super();
    }

    /**
     * Instantiates a new Circumference with a different drawing method.
     *
     * @param drawingMethod the GL drawing method
     */
    protected Circumference(int drawingMethod) {
        super(drawingMethod);
    }

    /**
     * Adjusts the radius of the circumference
     *
     * @param point one of the points at the edge of the circumference
     */
    @Override
    public void reshape(Vector point) {
        radius = (int) start.l2Distance(point);
    }

    /**
     * Draws the circle on screen. Uses a variant of Bresenham's Algorithm with
     * GL.GL_LINE_LOOP for filling.
     *
     * @param gl JOGL object
     */
    @Override
    protected void drawShape(GL gl) {
        int x = radius;
        int y = 0;
        int xChange = 1 - 2 * radius;
        int yChange = 1;
        int radiusError = 0;

        while (x >= y) {
            drawCircumferencePoints(gl, x, y);

            y++;
            radiusError += yChange;
            yChange += 2;
            if (2 * radiusError + xChange > 0) {
                x--;
                radiusError += xChange;
                xChange += 2;
            }
        }
    }

    private void drawCircumferencePoints(GL gl, int x, int y) {
        double sx = start.getX();
        double sy = start.getY();
        gl.glVertex2d(sx + x, sy + y);
        gl.glVertex2d(sx - x, sy + y);
        gl.glVertex2d(sx - x, sy - y);
        gl.glVertex2d(sx + x, sy - y);
        gl.glVertex2d(sx + y, sy + x);
        gl.glVertex2d(sx - y, sy + x);
        gl.glVertex2d(sx - y, sy - x);
        gl.glVertex2d(sx + y, sy - x);
    }
}
