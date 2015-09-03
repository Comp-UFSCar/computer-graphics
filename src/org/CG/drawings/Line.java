package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;
import org.CG.infrastructure.Point;

public class Line extends Drawing {

    Point end, translated_start;
    private int incE, incNE;
    private int dx, dy;
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
     * Refresh LineInPixel fields based on the last coordinated inputted by the user.
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
        
        findOctect(dx, dy);
        
        translated_start = translateToFirstOctant(start);
        end = translateToFirstOctant(end);
        
        if(translated_start.getX() > end.getX()) {
            Point tmp = translated_start;
            translated_start = end;
            end = tmp;
        }
        
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

        Point point = restoreToOriginalOctant(translated_start);
        gl.glVertex2i(point.getX(), point.getY());

        while (x < end.getX()) {
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

        gl.glEnd();
    }

    /**
     * Find in which octant the line is, based on its slope.
     *
     * @param dx The difference between ending-point-x and starting-point-x.
     * @param dy The difference between ending-point-y and starting-point-y.
     */
    protected void findOctect(int dx, int dy) {
        double d = Math.atan(((double) dy) / dx);
        d = (d >= 0) ? d : 2 * Math.PI + d;

        octant = (int) (4 * d / Math.PI);
    }

    /**
     * Based on its original @octant, find the representative coordinate of the point (x,y) in the 1st octant.
     *
     * @param pt point to be translated
     * @return a pair (x1, y1) that represents the translation of (x,y) to the 1st octant.
     */
    protected Point translateToFirstOctant(Point pt) {
        int x = pt.getX();
        int y = pt.getY();
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

    /**
     * Restore a point from the 1st octant to its original octant.
     *
     * @param pt point to be restored
     * @return a pair (x1, y1) that represents the restored point (x, y) to its original octant.
     */
    protected Point restoreToOriginalOctant(Point pt) {
        int x = pt.getX();
        int y = pt.getY();
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
