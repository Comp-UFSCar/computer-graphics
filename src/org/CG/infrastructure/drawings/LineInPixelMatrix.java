package org.CG.infrastructure.drawings;

import javax.media.opengl.GL;
import org.CG.infrastructure.Pair;

/**
 *
 * @author ldavid
 */
public class LineInPixelMatrix implements Drawing {

    public void draw(GL gl, int[] parameters) {
        int[] start = {parameters[0], parameters[1]};
        int[] end = {parameters[2], parameters[3]};

        // Start[x] must be smaller than end[x]. If it isn't, swap points.
        if (start[0] > end[0]) {
            int[] t = start;
            start = end;
            end = t;
        }

        int dx = end[0] - start[0];
        int dy = end[1] - start[1];

        int octect = findOctect(dx, dy);

        start = translate(start[0], start[1], octect);
        end = translate(end[0], end[1], octect);

        int x = (int) start[0];
        int y = (int) start[1];
        dx = (int) (end[0] - start[0]);
        dy = (int) (end[1] - start[1]);

        int incE = 2 * (dy - dx);
        int incNE = 2 * dy;

        int d = 2 * dy - dx;

        gl.glBegin(GL.GL_POINTS);
        int[] point = restore(x, y, octect);
        gl.glVertex2i(point[0], point[1]);

        while (x < end[0]) {
            if (d <= 0) {
                d += incNE;
            } else {
                d += incE;
                y++;
            }
            x++;

            point = restore(x, y, octect);
            gl.glVertex2i(point[0], point[1]);
        }

        gl.glEnd();
    }

    protected int findOctect(int dx, int dy) {
        double d = Math.toDegrees(Math.atan(((double) dy) / dx));
        d = (d >= 0) ? d : (180 - d);

        return (int) (d / 45 - .01) + 1;
    }

    protected int[] translate(int x, int y, int octect) {
        if (octect == 1) {
            return new int[]{x, y};
        }
        if (octect == 2) {
            return new int[]{y, x};
        }
        if (octect == 3) {
            return new int[]{-y, x};
        }
        if (octect == 4) {
            return new int[]{-x, y};
        }
        if (octect == 5) {
            return new int[]{-x, -y};
        }
        if (octect == 6) {
            return new int[]{-y, -x};
        }
        if (octect == 7) {
            return new int[]{y, -x};
        }
        if (octect == 8) {
            return new int[]{x, -y};
        }

        throw new RuntimeException("Unknown octect " + octect);
    }

    protected int[] restore(int x, int y, int octect) {
        if (octect == 1) {
            return new int[]{x, y};
        }
        if (octect == 2) {
            return new int[]{y, x};
        }
        if (octect == 3) {
            return new int[]{y, -x};
        }
        if (octect == 4) {
            return new int[]{-x, y};
        }
        if (octect == 5) {
            return new int[]{-x, -y};
        }
        if (octect == 6) {
            return new int[]{-y, -x};
        }
        if (octect == 7) {
            return new int[]{-y, x};
        }
        if (octect == 8) {
            return new int[]{x, -y};
        }

        throw new RuntimeException("Unknown octect " + octect);
    }
}
