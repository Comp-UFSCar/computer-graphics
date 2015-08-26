package org.CG.infrastructure.drawings;

import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public abstract class Drawing {
    int[] parameters;

    public Drawing(int[] parameters) {
        this.parameters = parameters;
    }
    
    abstract public void draw(GL gl);
}
