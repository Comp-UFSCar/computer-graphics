package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public class Circumference extends Drawing {

    int radius;
    
    @Override
    public Drawing translate(int[] point) {
        super.translate(point);
        updateLastCoordinate(point);
        
        return this;
    }
    
    @Override
    public Drawing updateLastCoordinate(int[] point) {
        int dx = start[0] - point[0], dy = start[1] - point[1];
        radius = (int) Math.sqrt(dx * dx + dy * dy);
        
        return this;
    }

    @Override
    public void draw(GL gl) {
        gl.glColor3ub(color[0], color[1], color[2]);
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
        gl.glVertex2i(start[0] + x, start[1] + y);
        gl.glVertex2i(start[0] - x, start[1] + y);
        gl.glVertex2i(start[0] - x, start[1] - y);
        gl.glVertex2i(start[0] + x, start[1] - y);
        gl.glVertex2i(start[0] + y, start[1] + x);
        gl.glVertex2i(start[0] - y, start[1] + x);
        gl.glVertex2i(start[0] - y, start[1] - x);
        gl.glVertex2i(start[0] + y, start[1] - x);
    }

}
