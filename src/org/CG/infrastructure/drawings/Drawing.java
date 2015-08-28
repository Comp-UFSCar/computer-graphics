package org.CG.infrastructure.drawings;

import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public abstract class Drawing {
    int[] parameters;

    public Drawing(int[] parameters) {
        setParameters(parameters);
    }
    
    public final void setParameters(int[] parameters) {
        this.parameters = parameters;
        refresh();
    }
    
    public int[] getParameters() {
        return parameters;
    }
    
    abstract public void refresh();
    
    abstract public void draw(GL gl);
}
