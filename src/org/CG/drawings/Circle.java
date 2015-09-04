package org.CG.drawings;

import javax.media.opengl.GL;

/**
 * Circle drawing with Bresenham's Algorithm.
 *
 * Reference paper: http://web.engr.oregonstate.edu/~sllu/bcircle.pdf
 *
 * @author ldavid
 */
public class Circle extends Circumference {

    public Circle() {
        glDrawingType = GL.GL_LINE_LOOP;
    }
}
