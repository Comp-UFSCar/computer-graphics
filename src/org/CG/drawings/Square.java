package org.CG.drawings;

import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;

/**
 *
 * @author ldavid
 */
public class Square extends Drawing {

    int width = 0, height = 0;

    @Override
    public Drawing updateLastCoordinate(int[] point) {
        int s0 = point[0] - start[0], s1 = point[1] - start[1];

        width = Math.abs(s0) >= Math.abs(s1) ? s0 : s1;
        height = s1 > 0 ? Math.abs(width) : -Math.abs(width);
        
        return this;
    }

    @Override
    public void draw(GL gl) {
        gl.glColor3ub(color[0], color[1], color[2]);
        gl.glBegin(GL.GL_POLYGON);

        gl.glVertex2i(start[0], start[1]);
        gl.glVertex2i(start[0] + width, start[1]);
        gl.glVertex2i(start[0] + width, start[1] + height);
        gl.glVertex2i(start[0], start[1] + height);

        gl.glEnd();
    }
}
