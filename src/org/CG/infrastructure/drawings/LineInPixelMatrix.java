package org.CG.infrastructure.drawings;

import javax.media.opengl.GL;
import org.CG.infrastructure.Pair;

/**
 *
 * @author ldavid
 */
public class LineInPixelMatrix implements Drawing {

    public void draw(GL gl, float[] parameters) {
        float[] start = {parameters[0], parameters[1]};
        float[] end = {parameters[2], parameters[3]};

        int x = (int) start[0];
        int y = (int) start[1];
        int dx = (int) (end[0] - start[0]);
        int dy = (int) (end[1] - start[1]);

        int octect = findOctect(dx, dy);

        int incE = 2 * (dy - dx);
        int incNE = 2 * dy;

        int d = 2 * dy - dx;

        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2i(x, y);

        while (x < end[0]) {
            if (d <= 0) {
                d += incNE;
            } else {
                d += incE;
                y++;
            }
            x++;

            gl.glVertex2i(x, y);
        }

        gl.glEnd();
    }

    protected int findOctect(int dx, int dy) {
        double d = Math.toDegrees(Math.atan(((double) dy) / dx));
        return (int) (d / 8) + 1;
    }

    protected Pair translate(int x, int y, int octect) {
        if (octect == 1) {
            return new Pair(x, y);
        }
        if (octect == 2) {
            return new Pair(y, x);
        }
        if (octect == 3) {
            return new Pair(-y, x);
        }
        if (octect == 4) {
            return new Pair(-x, y);
        }
        if (octect == 5) {
            return new Pair(-x, -y);
        }
        if (octect == 6) {
            return new Pair(-y, -x);
        }
        if (octect == 7) {
            return new Pair(y, -x);
        }
        if (octect == 8) {
            return new Pair(x, -y);
        }

        throw new RuntimeException("Unknown octect " + octect);
    }
}
