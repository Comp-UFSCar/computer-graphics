package org.CG.drawings;

import java.util.LinkedList;
import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;

/**
 *
 * @author ldavid
 */
public class Polygon extends Drawing {

    LinkedList<int[]> points;

    public Polygon() {
        super();

        finished = false;
        points = new LinkedList<>();
    }
    
    @Override
    public Drawing setStart(int[] start) {
        points.add(start);
        return super.setStart(start);
    }
    
    @Override
    public Drawing translate(int[] point) {
        point[0] -= start[0];
        point[1] -= start[1];

        points.stream().forEach((p) -> {
            p[0] += point[0];
            p[1] += point[1];
        });

        return this;
    }
    
    @Override
    public void updateLastCoordinateInputted(int[] point) {
        points.removeLast();
        points.add(point);
    }

    @Override
    public void draw(GL gl) {
        gl.glColor3ub(color[0], color[1], color[2]);
        gl.glBegin(GL.GL_POLYGON);

        points.stream().forEach((point) -> {
            gl.glVertex2i(point[0], point[1]);
        });

        gl.glEnd();
    }
}
