package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public class Circunference extends Drawing {

    int radius;

    @Override
    public void updateLastCoordinateInputted(int[] point) {
        radius = (int) Math.sqrt(Math.pow(start[0] - point[0], 2) + Math.pow(start[1] - point[1], 2));
    }

    @Override
    public void draw(GL gl) {
        gl.glColor3ub(color[0], color[1], color[2]);
        gl.glBegin(GL.GL_LINE_LOOP);

        for (int i = 0; i < 360; i++) {
            float degInRad = 0.0174532925f * i;
            
            gl.glVertex2f(start[0] + (float) Math.cos(degInRad) * radius, start[1] + (float) Math.sin(degInRad) * radius);
        }

        gl.glEnd();
    }

}
