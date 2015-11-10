package org.CG.infrastructure;

import org.CG.infrastructure.abstractions.Vector3;
import org.CG.infrastructure.abstractions.ColorFloat;
import org.CG.infrastructure.abstractions.ColorByte;
import javax.media.opengl.GL;

/**
 * Represents an object/shape that can be drawn on screen.
 *
 * @author ldavid
 */
public abstract class Drawing implements Interactive {

    protected Vector3 start;
    protected ColorByte color;
    protected boolean finished;
    protected final int glDrawingType;

    /**
     * Instantiates a finished drawing using GL_POINTS as the drawing method.
     */
    public Drawing() {
        this(GL.GL_POINTS);
    }

    /**
     * Instantiates a finished drawing with a given drawing method.
     *
     * @param drawingMethod the drawing method used
     */
    public Drawing(int drawingMethod) {
        // All drawings, except by polygons, are initiated with finished
        // as true, as they don't allow second clicks.
        finished = true;
        this.glDrawingType = drawingMethod;
    }

    /**
     * Updates the last coordinate in the shape.
     *
     * @param point the new last coordinate.
     * @return this
     */
    abstract public Drawing updateLastCoordinate(Vector3 point);

    /**
     * Sets the next coordinate of the shape.
     *
     * @param point the new coordinate.
     * @return this
     */
    public Drawing setNextCoordinate(Vector3 point) {
        if (finished) {
            throw new RuntimeException(
                    "Cannot set next coordinate if drawing is already finished.");
        }

        return this;
    }

    /**
     * Draws the shape on screen.
     *
     * @param gl JOGL object used to draw
     */
    public void draw(GL gl) {
        gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
        gl.glBegin(glDrawingType);

        drawShape(gl);

        gl.glEnd();
    }

    /**
     * Implementation of the draw method. The methods glColor(), glBegin() and glEnd() are already called.
     *
     * @param gl JOGL object used to draw
     */
    abstract protected void drawShape(GL gl);

    /**
     * Move the shape to a specific point in space.
     *
     * @param v point to which the shape should move.
     * @return this
     */
    @Override
    public Drawing moveTo(Vector3 v) {
        setStart(v);
        return this;
    }

    /**
     * Move the shape according to a translation {@code v}. .
     *
     *
     * @param v vector describing the translation that the shape should do.
     * @return this
     */
    @Override
    public Drawing move(Vector3 v) {
        return moveTo(start.move(v));
    }

    /**
     * Sets the starting point of the shape.
     *
     * @param start new starting point
     * @return this
     */
    public Drawing setStart(Vector3 start) {
        this.start = start;
        return this;
    }

    public Vector3 getStart() {
        return start;
    }

    /**
     * Sets the color of the shape.
     *
     * @param color color of the shape
     * @return this
     */
    public Drawing setColor(ColorByte color) {
        this.color = color;
        return this;
    }

    /**
     * Sets the color of the shape
     *
     * @param color color of the shape
     * @return this
     */
    public Drawing setColor(ColorFloat color) {
        setColor(color.toColorByte());
        return this;
    }

    /**
     * Sets the drawing as finished or not.
     *
     * @param finished states if drawing should be finished or not.
     * @return this
     */
    public Drawing isFinished(boolean finished) {
        this.finished = finished;
        return this;
    }

    /**
     * Checks if the drawing is finished or not.
     *
     * @return true if the drawing is finished, false otherwise.
     */
    public boolean isFinished() {
        return finished;
    }
}
