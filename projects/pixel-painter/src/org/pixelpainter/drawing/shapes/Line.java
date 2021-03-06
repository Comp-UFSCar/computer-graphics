package org.pixelpainter.drawing.shapes;

import javax.media.opengl.GL;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 * Drawing of a line on the screen. Uses the Bresenham's mid-point algorithm.
 *
 * @author ldavid
 */
public class Line extends Drawing {

    private Vector end;
    private Vector translated_start;
    private Vector translated_end;
    private int incE;
    private int incNE;
    private int dx;
    private int dy;
    private int octant;

    /**
     * {@inheritDoc }
     */
    @Override
    public Drawing setStart(Vector start) {
        super.setStart(start);
        reshape(start);
        
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void moveTo(Vector v) {
        Vector d = v.delta(start);
        start = v;

        reshape(end.add(d));
    }

    /**
     * Refresh LineInPixel fields based on the last coordinated inputted by the user. This refresh override
     * re-calculates the midpoint line algorithm.
     *
     * @param last the last coordinate
     */
    @Override
    public void reshape(Vector last) {
        end = last;
        dx = (int) (end.getX() - start.getX());
        dy = (int) (end.getY() - start.getY());

        octant = findOctant(dx, dy);

        translated_start = translateToFirstOctant(start);
        translated_end = translateToFirstOctant(end);

        if (translated_start.getX() > translated_end.getX()) {
            Vector tmp = translated_start;
            translated_start = translated_end;
            translated_end = tmp;
        }

        dx = (int) (translated_end.getX() - translated_start.getX());
        dy = (int) (translated_end.getY() - translated_start.getY());

        incE = 2 * (dy - dx);
        incNE = 2 * dy;
    }

    /**
     * Draw the line on screen, using the Bresenham's middle point algorithm.
     *
     * @param gl JOGL object to use in the drawing
     */
    @Override
    protected void drawShape(GL gl) {
        // Set line color.
        int x = (int) translated_start.getX();
        int y = (int) translated_start.getY();
        int d = 2 * dy - dx;

        Vector point = restoreToOriginalOctant(translated_start);
        gl.glVertex2d(point.getX(), point.getY());

        while (x < translated_end.getX()) {
            if (d <= 0) {
                d += incNE;
            } else {
                d += incE;
                y++;
            }
            x++;

            point = restoreToOriginalOctant(new Vector(x, y));
            gl.glVertex2d(point.getX(), point.getY());
        }
    }

    /**
     * Find in which octant the line is, based on its slope.
     *
     * @param dx The difference between ending-point-x and starting-point-x.
     * @param dy The difference between ending-point-y and starting-point-y.
     * @return the octant, in range [0, 7]
     */
    protected static int findOctant(double dx, double dy) {
        double d = Math.atan(((double) dy) / dx);
        d = (d >= 0) ? d : 2 * Math.PI + d;

        return (int) (4 * d / Math.PI);
    }

    /**
     * Based on its original {@link #octant octant}, find the representative coordinate of the point in the first
     * octant.
     *
     * @param pt point to be translated
     * @return a point that represents the translation of (x,y) to the first octant.
     */
    protected Vector translateToFirstOctant(Vector pt) {
        return pt.allOctants()[octant];
    }

    /**
     * Restore a point from the first octant to its original octant.
     *
     * @param pt point to be restored
     * @return a point that represents the restored point to its original octant.
     */
    protected Vector restoreToOriginalOctant(Vector pt) {
        int oct = octant;
        // handles edge case for the secondary diagonal
        if (oct == 2) {
            oct = 6;
        } else if (oct == 6) {
            oct = 2;
        }
        return pt.allOctants()[oct];
    }

    public Vector getEnd() {
        return end;
    }
}
