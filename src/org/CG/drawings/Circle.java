package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;

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
    public Drawing translate(int[] point) {
        super.translate(point);
        updateLastCoordinateInputted(point);
        
        return this;
    }
    
    @Override
    public void updateLastCoordinateInputted(int[] point) {
        radius = (int) Math.sqrt(Math.pow(start[0] - point[0], 2) + Math.pow(start[1] - point[1], 2));
    }

    @Override
    public void draw(GL gl) {
        gl.glColor3ub(color[0], color[1], color[2]);
        gl.glBegin(GL.GL_LINE_LOOP);

        int x = radius;
        int y = 0;
        int xChange = 1 - 2 * radius;
        int yChange = 1;
        int radiusError = 0;

        while (x >= y) {
            gl.glVertex2i(start[0] + x, start[1] + y);
            gl.glVertex2i(start[0] - x, start[1] + y);
            gl.glVertex2i(start[0] - x, start[1] - y);
            gl.glVertex2i(start[0] + x, start[1] - y);
            gl.glVertex2i(start[0] + y, start[1] + x);
            gl.glVertex2i(start[0] - y, start[1] + x);
            gl.glVertex2i(start[0] - y, start[1] - x);
            gl.glVertex2i(start[0] + y, start[1] - x);

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
