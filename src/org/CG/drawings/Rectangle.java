package org.CG.drawings;

import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;

/**
 *
 * @author ldavid
 */
public class Rectangle extends Drawing {

    int[] end;

    @Override
    public Drawing translate(int[] point) {
        int[] t = {end[0] -start[0], end[1] - start[1]};
        
        start = point;
        end = new int[] {point[0] +t[0], point[1]+t[0]};

        return this;
    }

    @Override
    public void updateLastCoordinateInputted(int[] point) {
        end = point;
    }

    @Override
    public void draw(GL gl) {
        if (end == null) {
            return;
        }

        gl.glColor3ub(color[0], color[1], color[2]);
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2i(start[0], start[1]);
        gl.glVertex2i(start[0], end[1]);
        gl.glVertex2i(end[0], end[1]);
        gl.glVertex2i(end[0], start[1]);

        gl.glEnd();
    }

}
