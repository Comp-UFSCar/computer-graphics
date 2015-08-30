package org.CG.infrastructure.drawings;

import java.util.LinkedList;
import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public class Pencil extends Drawing {

    LinkedList<int[]> points;

    public Pencil(int[] start, byte[] color) {
        super(start, color);

        points = new LinkedList<>();
    }

    @Override
    public void updateLastCoordinateInputted(int[] point) {
        points.add(point);
    }

    @Override
    public void draw(GL gl) {
        gl.glColor3ub(color[0], color[1], color[2]);
        gl.glBegin(GL.GL_POINTS);

        points.stream().forEach((point) -> {
            for (int i = point[0] - 1; i <= point[0] + 1; i++) {
                for (int j = point[1] - 1; j <= point[1] + 1; j++) {
                    gl.glVertex2i(i, j);
                }
            }
        });

        gl.glEnd();
    }
}
