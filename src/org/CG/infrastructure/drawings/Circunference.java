package org.CG.infrastructure.drawings;

import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public class Circunference extends Drawing {

    public Circunference(int[] parameters) {
        super(parameters);
    }

    public void draw(GL gl) {
        int radius = parameters[0];

        gl.glBegin(GL.GL_LINE_LOOP);

        for (int i = 0; i < 360; i++) {
            double radians = Math.toRadians(i);

            gl.glVertex2f((float) Math.cos(radians) * radius, (float) Math.sin(radians) * radius);
        }

        gl.glEnd();
    }
}
