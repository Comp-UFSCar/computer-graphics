package org.CG.infrastructure;

import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public abstract class Drawing {

    protected Point start;
    protected ColorByte color;
    protected boolean finished;
    protected int glDrawingType = GL.GL_POINTS;

    public Drawing() {
        // All drawings, except by polygons, are initiated with finished
        // as true, as they don't allow second clicks.
        finished = true;
    }

    abstract public Drawing updateLastCoordinate(Point point);

    public Drawing setNextCoordinate(Point point) {
        if (finished) {
            throw new RuntimeException(
                "Cannot set next coordinate if drawing is already finished.");
        }

        return this;
    }

    public void draw(GL gl) {
        gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
        gl.glBegin(glDrawingType);

        drawShape(gl);

        gl.glEnd();
    }

    abstract protected void drawShape(GL gl);

    public Drawing translate(Point point) {
        return setStart(point);
    }

    public Drawing setStart(Point start) {
        this.start = start;
        return this;
    }

    public Drawing setColor(ColorByte color) {
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
