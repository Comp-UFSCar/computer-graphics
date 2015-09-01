package org.CG.infrastructure;

import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public abstract class Drawing {

    protected int[] start;
    protected byte[] color;
    protected boolean finished;

    public Drawing() {
        // All drawings, except by polygons, are initiated with finished as true, as they don't allow second clicks.
        finished = true;
    }
    
    abstract public void updateLastCoordinateInputted(int[] point);

    abstract public void draw(GL gl);
    
    public Drawing translate(int[] point) {
        return setStart(point);
    }

    public Drawing setStart(int[] start) {
        this.start = start;
        return this;
    }

    public Drawing setColor(byte[] color) {
        this.color = color;
        return this;
    }
    
    public boolean getFinished() {
        return finished;
    }
    
    public void setFinished(boolean f) {
        finished = f;
    }
}
