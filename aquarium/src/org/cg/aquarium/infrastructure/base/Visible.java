package org.cg.aquarium.infrastructure.base;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/**
 * Visible interface.
 * 
 * Sub classes will be displayed on the screen, they are likely to be called
 * by {@code AquariumCanvas}, which contains the GL object passed.
 *
 * @author ldavid
 */
public interface Visible {

    /**
     * Draws the object onto the canvas.
     *
     * @param gl OpenGL object which can be used to draw onto the screen.
     * @param glu GLU object which can be used to draw onto the screen.
     */
    public abstract void display(GL gl, GLU glu);
}
