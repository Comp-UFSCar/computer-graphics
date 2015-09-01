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

    abstract public Drawing updateLastCoordinate(int[] point);

    public Drawing setNextCoordinate(int[] point) {
        if (finished) {
            throw new RuntimeException("Cannot set next coordinate if drawing is already finished.");
        }
        
        return this;
    }

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

    public Drawing setFinished(boolean f) {
        finished = f;
        return this;
    }

    public boolean getFinished() {
        return finished;
    }
}
