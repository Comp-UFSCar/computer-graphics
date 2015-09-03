package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;
import org.CG.infrastructure.Point;

public class Line extends Drawing {

    private Point end;
    private Point translated_start;
    private int incE;
    private int incNE;
    private int dx;
    private int dy;
    private int octant;

    @Override
    public Drawing setStart(Point start) {
        super.setStart(start);
        return updateLastCoordinate(start);
    }

    @Override
    public Drawing translate(Point point) {
        Point t = new Point(end.getX() - start.getX(), end.getY() - start.getY());

        start = point;
        end = new Point(point.getX() + t.getX(), point.getY() + t.getX());

        return updateLastCoordinate(end);
    }

    /**
     * Refresh LineInPixel fields based on the last coordinated inputted by the
     * user.
     *
     * This refresh override re-calculates the midpoint line algorithm.
     *
     * @param last the last coordinate.
     * @return this
     */
    @Override
    public Drawing updateLastCoordinate(Point last) {
        end = last;

        dx = end.getX() - start.getX();
        dy = end.getY() - start.getY();

        octant = findOctant(dx, dy);

        // Find the line representation on the first octant.
        translated_start = translateToFirstOctant(start);
        end = translateToFirstOctant(end);

        // Start[x] must be smaller than end[x]. If it isn't, swap points.
        if (translated_start.getX() > end.getY()) {
            Point t = translated_start;
            translated_start = end;
            end = t;
        }

        // Calculate E and NE constants for the Midpoint Line algorithm.
        dx = end.getX() - translated_start.getX();
        dy = end.getY() - translated_start.getY();

        incE = 2 * (dy - dx);
        incNE = 2 * dy;

        return this;
    }

    @Override
    public void draw(GL gl) {
        // Set line color.
        gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
        gl.glBegin(GL.GL_POINTS);

        int x = translated_start.getX();
        int y = translated_start.getY();
        int d = 2 * dy - dx;

        Point point = restoreToOriginalOctant(x, y);
        gl.glVertex2i(point.getX(), point.getY());

        while (x < end.getX()) {
            if (d <= 0) {
                d += incNE;
            } else {
                d += incE;
                y++;
            }
            x++;

            point = restoreToOriginalOctant(x, y);
            gl.glVertex2i(point.getX(), point.getY());
        }

        gl.glEnd();
    }

    /**
     * Find in which octant the line is, based on its slope.
     *
     * @param dx The difference between ending-point-x and starting-point-x.
     * @param dy The difference between ending-point-y and starting-point-y.
     * @return the octant (0-7) of the points
     */
    protected int findOctant(int dx, int dy) {
        double d = Math.atan(((double) dy) / dx);
        d = (d >= 0) ? d : 2 * Math.PI + d;

        return (int) (4 * d / Math.PI);
    }

    protected Point translateToFirstOctant(Point point) {
        return translateToFirstOctant(point.getX(), point.getY());
    }

    /**
     * Based on its original @octant, find the representative coordinate of the
     * point (x,y) in the 1st octant.
     *
     * @param x The 1st coordinate of the point.
     * @param y The 2nd coordinate of the point.
     * @return a pair (x1, y1) that represents the translation of (x,y) to the
     * 1st octant.
     */
    protected Point translateToFirstOctant(int x, int y) {
        if (octant == 0) {
            return new Point(x, y);
        }
        if (octant == 1) {
            return new Point(y, x);
        }
        if (octant == 2) {
            return new Point(-y, x);
        }
        if (octant == 3) {
            return new Point(-x, y);
        }
        if (octant == 4) {
            return new Point(-x, -y);
        }
        if (octant == 5) {
            return new Point(-y, -x);
        }
        if (octant == 6) {
            return new Point(y, -x);
        }
        if (octant == 7) {
            return new Point(x, -y);
        }

        throw new RuntimeException("Unknown octant " + octant);
    }

    protected Point restoreToOriginalOctant(Point point) {
        return restoreToOriginalOctant(point.getX(), point.getY());
    }

    /**
     * Restore a point from the 1st octant to its original octant.
     *
     * @param x The 1st coordinate of the point in the 1st octant.
     * @param y The 2nd coordinate of the point in the 1st octant.
     * @return a pair (x1, y1) that represents the restored point (x, y) to its
     * original octant.
     */
    protected Point restoreToOriginalOctant(int x, int y) {
        if (octant == 0) {
            return new Point(x, y);
        }
        if (octant == 1) {
            return new Point(y, x);
        }
        if (octant == 2) {
            return new Point(y, -x);
        }
        if (octant == 3) {
            return new Point(-x, y);
        }
        if (octant == 4) {
            return new Point(-x, -y);
        }
        if (octant == 5) {
            return new Point(-y, -x);
        }
        if (octant == 6) {
            return new Point(-y, x);
        }
        if (octant == 7) {
            return new Point(x, -y);
        }

        throw new RuntimeException("Unknown octant " + octant);
    }
}
