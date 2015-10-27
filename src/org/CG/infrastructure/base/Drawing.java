package org.CG.infrastructure.base;

import com.sun.opengl.util.GLUT;
import org.CG.infrastructure.structures.ColorByte;
import org.CG.infrastructure.structures.ColorFloat;
import org.CG.infrastructure.structures.Point;
import javax.media.opengl.GL;

/**
 * Represents an object/shape that can be drawn on screen.
 *
 * @author ldavid
 */
public abstract class Drawing extends Interactive {

    protected Point start;
    protected ColorByte color;
    protected boolean finished;
    protected final int glDrawingType;

    /**
     * Instantiates a finished drawing using GL_POINTS as the drawing
     * method.
     */
    public Drawing() {
        this(GL.GL_POINTS);
        color = new ColorByte();
    }
    
    /**
     * Instantiates a finished drawing with a given drawing method.
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
    abstract public Drawing updateLastCoordinate(Point point);

    /**
     * Sets the next coordinate of the shape.
     *
     * @param point the new coordinate.
     * @return this
     */
    public Drawing setNextCoordinate(Point point) {
        if (finished) {
            throw new RuntimeException(
                    "Cannot set next coordinate if drawing is already finished.");
        }

        return this;
    }

    /**
     * Draws the shape on screen.
     *
     * @param gl JOGL object used to draw.
     * @param glut GLUT object used to draw.
     */
    public void draw(GL gl, GLUT glut) {
        gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
        gl.glBegin(glDrawingType);

        drawShape(gl, glut);

        gl.glEnd();
    }

    /**
     * Implementation of the draw method. The methods glColor(), glBegin() and
     * glEnd() are already called.
     *
     * @param gl JOGL object used to draw.
     * @param glut GLUT object used to draw 3D elements.
     */
    abstract protected void drawShape(GL gl, GLUT glut);

    /**
     * Translates the shape.
     *
     * @param point translation point
     * @return this
     */
    public Drawing translate(Point point) {
        setStart(point);
        return this;
    }

    /**
     * Sets the starting point of the shape.
     *
     * @param start new starting point
     * @return this
     */
    public Drawing setStart(Point start) {
        this.start = start;
        return this;
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

    public Point getStart() {
        return start;
    }
}
