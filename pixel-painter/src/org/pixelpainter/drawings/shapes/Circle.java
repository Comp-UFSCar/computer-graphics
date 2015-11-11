package org.pixelpainter.drawings.shapes;

import javax.media.opengl.GL;

/**
 * Circle drawing with Bresenham's Algorithm.
 *
 * @author ldavid
 */
public class Circle extends Circumference {

    public Circle() {
        super(GL.GL_LINE_LOOP);
    }
}
