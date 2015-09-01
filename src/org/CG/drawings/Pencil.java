package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import java.util.LinkedList;
import java.util.List;
import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public class Pencil extends Drawing {

    List<int[]> points;

    public Pencil() {
        super();

        points = new LinkedList<>();
    }

    @Override
    public Drawing translate(int[] point) {
        point[0] -= start[0];
        point[1] -= start[1];
        
        start[0] += point[0];
        start[1] += point[1];

        points.stream().forEach((p) -> {
            p[0] += point[0];
            p[1] += point[1];
        });

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(int[] point) {
        points.add(point);
        return this;
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
