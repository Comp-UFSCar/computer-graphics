package org.CG.drawings;

import javax.media.opengl.GL;

/**
 * Circle drawing with Bresenham's Algorithm.
 *
 * @author ldavid
 */
public class Circle extends Circumference {

    public Circle() {
        glDrawingType = GL.GL_LINE_LOOP;
    }
}
