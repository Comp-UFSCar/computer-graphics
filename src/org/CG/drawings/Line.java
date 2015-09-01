package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import javax.media.opengl.GL;

public class Line extends Drawing {

    int[] end, translated_start;
    private int incE, incNE;
    private int dx, dy;
    private int octant;

    @Override
    public Drawing setStart(int[] start) {
        super.setStart(start);
        updateLastCoordinateInputted(start);
        return this;
    }

    @Override
    public Drawing translate(int[] point) {
        int[] t = {end[0] -start[0], end[1] - start[1]};
        
        start = point;
        end = new int[] {point[0] +t[0], point[1]+t[0]};

        updateLastCoordinateInputted(end);
        return this;
    }

    /**
     * Refresh LineInPixel fields based on the last coordinated inputted by the user.
     *
     * This refresh override re-calculates the midpoint line algorithm.
     *
     * @param last the last coordinate.
     */
    @Override
    public void updateLastCoordinateInputted(int[] last) {
        end = new int[]{last[0], last[1]};

        dx = end[0] - start[0];
        dy = end[1] - start[1];

        findOctect(dx, dy);

        // Find the line representation on the first octant.
        translated_start = translateToFirstOctant(start);
        end = translateToFirstOctant(end);

        // Start[x] must be smaller than end[x]. If it isn't, swap points.
        if (translated_start[0] > end[0]) {
            int[] t = translated_start;
            translated_start = end;
            end = t;
        }

        // Calculate E and NE constants for the Midpoint Line algorithm.
        dx = end[0] - translated_start[0];
        dy = end[1] - translated_start[1];

        incE = 2 * (dy - dx);
        incNE = 2 * dy;
    }

    @Override
    public void draw(GL gl) {
        // Set line color.
        gl.glColor3ub(color[0], color[1], color[2]);
        gl.glBegin(GL.GL_POINTS);

        int x = translated_start[0];
        int y = translated_start[1];
        int d = 2 * dy - dx;

        int[] point = restoreToOriginalOctant(x, y);
        gl.glVertex2i(point[0], point[1]);

        while (x < end[0]) {
            if (d <= 0) {
                d += incNE;
            } else {
                d += incE;
                y++;
            }
            x++;

            point = restoreToOriginalOctant(x, y);
            gl.glVertex2i(point[0], point[1]);
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

    protected int[] translateToFirstOctant(int[] point) {
        return translateToFirstOctant(point[0], point[1]);
    }

    /**
     * Based on its original @octant, find the representative coordinate of the point (x,y) in the 1st octant.
     *
     * @param x The 1st coordinate of the point.
     * @param y The 2nd coordinate of the point.
     * @return a pair (x1, y1) that represents the translation of (x,y) to the 1st octant.
     */
    protected int[] translateToFirstOctant(int x, int y) {
        if (octant == 0) {
            return new int[]{x, y};
        }
        if (octant == 1) {
            return new int[]{y, x};
        }
        if (octant == 2) {
            return new int[]{-y, x};
        }
        if (octant == 3) {
            return new int[]{-x, y};
        }
        if (octant == 4) {
            return new int[]{-x, -y};
        }
        if (octant == 5) {
            return new int[]{-y, -x};
        }
        if (octant == 6) {
            return new int[]{y, -x};
        }
        if (octant == 7) {
            return new int[]{x, -y};
        }

        throw new RuntimeException("Unknown octant " + octant);
    }

    protected int[] restoreToOriginalOctant(int[] point) {
        return restoreToOriginalOctant(point[0], point[1]);
    }

    /**
     * Restore a point from the 1st octant to its original octant.
     *
     * @param x The 1st coordinate of the point in the 1st octant.
     * @param y The 2nd coordinate of the point in the 1st octant.
     * @return a pair (x1, y1) that represents the restored point (x, y) to its original octant.
     */
    protected int[] restoreToOriginalOctant(int x, int y) {
        if (octant == 0) {
            return new int[]{x, y};
        }
        if (octant == 1) {
            return new int[]{y, x};
        }
        if (octant == 2) {
            return new int[]{y, -x};
        }
        if (octant == 3) {
            return new int[]{-x, y};
        }
        if (octant == 4) {
            return new int[]{-x, -y};
        }
        if (octant == 5) {
            return new int[]{-y, -x};
        }
        if (octant == 6) {
            return new int[]{-y, x};
        }
        if (octant == 7) {
            return new int[]{x, -y};
        }

        throw new RuntimeException("Unknown octant " + octant);
    }
}
