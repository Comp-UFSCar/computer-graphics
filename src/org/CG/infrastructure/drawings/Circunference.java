package org.CG.infrastructure.drawings;

import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public class Circunference implements Drawing {

    public void draw(GL gl, float[] parameters) {
        float DEG2RAD = parameters[0];
        float radius = parameters[1];

        gl.glBegin(GL.GL_LINE_LOOP);

        for (int i = 0; i < 360; i++) {
            float degInRad = i * DEG2RAD;

            gl.glVertex2f((float) Math.cos(degInRad) * radius, (float) Math.sin(degInRad) * radius);
        }

        gl.glEnd();
    }
}
