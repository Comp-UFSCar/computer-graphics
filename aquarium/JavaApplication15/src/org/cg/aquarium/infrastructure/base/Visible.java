package org.cg.aquarium.infrastructure.base;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/**
 * Visible interface.
 *
 * Sub classes will be displayed on the screen, they are likely to be called by
 * {@code AquariumCanvas}, which contains the GL object passed.
 *
 * @author ldavid
 */
public interface Visible {

    /**
     * Draws the object onto the canvas.
     *
     * @param gl OpenGL active instance.
     * @param glu GLU active instance.
     * @param glut GLUT active instance.
     */
    public abstract void display(GL gl, GLU glu, GLUT glut);
}
