package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;
import org.CG.infrastructure.abstractions.Point;

/**
 * Drawing of a line on the screen. Uses the Bresenham's mid-point algorithm.
 *
 * @author ldavid
 */
public class Line extends Drawing {

    private Point end;
    private Point translated_start;
    private Point translated_end;
    private int incE;
    private int incNE;
    private int dx;
    private int dy;
    private int octant;

    /**
     * {@inheritDoc }
     */
    @Override
    public Drawing setStart(Point start) {
        super.setStart(start);
        return updateLastCoordinate(start);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Drawing translate(Point point) {
        Point t = new Point(point.getX() - start.getX(), point.getY() - start.getY());
        start = point;

        return updateLastCoordinate(end.move(t));
    }

    /**
     * Refresh LineInPixel fields based on the last coordinated inputted by the
     * user. This refresh override re-calculates the midpoint line algorithm.
     *
     * @param last the last coordinate
     * @return this
     */
    @Override
    public Drawing updateLastCoordinate(Point last) {
        end = last;
        dx = end.getX() - start.getX();
        dy = end.getY() - start.getY();

        octant = findOctant(dx, dy);

        translated_start = translateToFirstOctant(start);
        translated_end = translateToFirstOctant(end);

        if (translated_start.getX() > translated_end.getX()) {
            Point tmp = translated_start;
            translated_start = translated_end;
            translated_end = tmp;
        }

        dx = translated_end.getX() - translated_start.getX();
        dy = translated_end.getY() - translated_start.getY();

        incE = 2 * (dy - dx);
        incNE = 2 * dy;

        return this;
    }

    /**
     * Draw the line on screen, using the Bresenham's middle point algorithm.
     *
     * @param gl JOGL object to use in the drawing
     */
    @Override
    protected void drawShape(GL gl) {
        // Set line color.
        int x = translated_start.getX();
        int y = translated_start.getY();
        int d = 2 * dy - dx;

        Point point = restoreToOriginalOctant(translated_start);
        gl.glVertex2i(point.getX(), point.getY());

        while (x < translated_end.getX()) {
            if (d <= 0) {
                d += incNE;
            } else {
                d += incE;
                y++;
            }
            x++;

            point = restoreToOriginalOctant(new Point(x, y));
            gl.glVertex2i(point.getX(), point.getY());
        }
    }

    /**
     * Find in which octant the line is, based on its slope.
     *
     * @param dx The difference between ending-point-x and starting-point-x.
     * @param dy The difference between ending-point-y and starting-point-y.
     * @return the octant, in range [0, 7]
     */
    protected static int findOctant(int dx, int dy) {
        double d = Math.atan(((double) dy) / dx);
        d = (d >= 0) ? d : 2 * Math.PI + d;

        return (int) (4 * d / Math.PI);
    }

    /**
     * Based on its original {@link #octant octant}, find the representative
     * coordinate of the point in the first octant.
     *
     * @param pt point to be translated
     * @return a point that represents the translation of (x,y) to the first
     * octant.
     */
    protected Point translateToFirstOctant(Point pt) {
        return pt.allOctants()[octant];
    }

    /**
     * Restore a point from the first octant to its original octant.
     *
     * @param pt point to be restored
     * @return a point that represents the restored point to its original
     * octant.
     */
    protected Point restoreToOriginalOctant(Point pt) {
        int oct = octant;
        // handles edge case for the secondary diagonal
        if (oct == 2) {
            oct = 6;
        } else if (oct == 6) {
            oct = 2;
        }
        return pt.allOctants()[oct];
    }

    public Point getEnd() {
        return end;
    }
}
