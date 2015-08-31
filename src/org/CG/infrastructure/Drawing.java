package org.CG.infrastructure;

import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public abstract class Drawing {

    protected int[] start;
    protected byte[] color;

    abstract public void updateLastCoordinateInputted(int[] point);

    abstract public void draw(GL gl);

    public Drawing setStart(int[] start) {
        this.start = start;
        return this;
    }

    public Drawing setColor(byte[] color) {
        this.color = color;
        return this;
    }
}
