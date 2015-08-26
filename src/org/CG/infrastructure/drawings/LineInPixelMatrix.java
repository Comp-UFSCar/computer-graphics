package org.CG.infrastructure.drawings;

import javax.media.opengl.GL;

public class LineInPixelMatrix extends Drawing {

    public LineInPixelMatrix(int[] parameters) {
        super(parameters);
    }

    @Override
    public void draw(GL gl) {
        int[] start = {parameters[0], parameters[1]};
        int[] end = {parameters[2], parameters[3]};

        int dx = end[0] - start[0];
        int dy = end[1] - start[1];

        int octant = findOctect(dx, dy);

        start = translate(start, octant);
        end = translate(end, octant);

        // Start[x] must be smaller than end[x]. If it isn't, swap points.
        if (start[0] > end[0]) {
            int[] t = start;
            start = end;
            end = t;
        }

        // Calculate E and NE constants for the Midpoint Line algorithm.
        int x = (int) start[0];
        int y = (int) start[1];
        dx = (int) (end[0] - start[0]);
        dy = (int) (end[1] - start[1]);

        int incE = 2 * (dy - dx);
        int incNE = 2 * dy;

        int d = 2 * dy - dx;

        // Set line color. Default is white.
        gl.glColor3ub((byte) parameters[4], (byte) parameters[5], (byte) parameters[6]);
        gl.glBegin(GL.GL_POINTS);

        int[] point = restore(x, y, octant);
        gl.glVertex2i(point[0], point[1]);

        while (x < end[0]) {
            if (d <= 0) {
                d += incNE;
            } else {
                d += incE;
                y++;
            }
            x++;

            point = restore(x, y, octant);
            gl.glVertex2i(point[0], point[1]);
        }

        gl.glEnd();
    }

    /**
     * Find in which octant the line is, based on its slope.
     * 
     * @param dx The difference between ending-point-x and starting-point-x.
     * @param dy The difference between ending-point-y and starting-point-y.
     * @return x, x e [0,8], where x represents the octant (anticlockwise order).
     */
    protected int findOctect(int dx, int dy) {
        double d = Math.atan(((double) dy) / dx);
        d = (d >= 0) ? d : 2 * Math.PI + d;

        return (int) (4 * d / Math.PI);
    }

    protected int[] translate(int[] point, int octant) {
        return translate(point[0], point[1], octant);
    }

    /**
     * Based on its original @octant, find the representative coordinate of the point (x,y) in the 1st octant.
     *
     * @param x The 1st coordinate of the point.
     * @param y The 2nd coordinate of the point.
     * @param octant The octant from which that point originally belongs.
     * @return a pair (x1, y1) that represents the translation of (x,y) to the 1st octant.
     */
    protected int[] translate(int x, int y, int octant) {
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

    protected int[] restore(int[] point, int octant) {
        return restore(point[0], point[1], octant);
    }

    /**
     * Restore a point from the 1st octant to its original octant.
     * 
     * @param x The 1st coordinate of the point in the 1st octant.
     * @param y The 2nd coordinate of the point in the 1st octant.
     * @param octant The octant to which the point should be restored.
     * @return a pair (x1, y1) that represents the restored point (x, y) to its original octant.
     */
    protected int[] restore(int x, int y, int octant) {
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
