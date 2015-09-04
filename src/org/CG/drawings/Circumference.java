package org.CG.drawings;

import javax.media.opengl.GL;

/**
 * Circumference drawing with Bresenham's algorithm
 *
 * @author ldavid
 */
public class Circumference extends Circle {

    public Circumference() {
        glDrawingType = GL.GL_POINTS;
    }
}
