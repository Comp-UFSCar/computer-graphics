package org.CG.drawings;

import org.CG.infrastructure.Drawing;
import java.util.LinkedList;
import java.util.List;
import javax.media.opengl.GL;
import org.CG.infrastructure.Point;

/**
 *
 * @author ldavid
 */
public class Pencil extends Drawing {

    private final List<Point> points;

    public Pencil() {
        super();

        points = new LinkedList<>();
    }

    @Override
    public Drawing translate(Point point) {
        /*point[0] -= start[0];
        point[1] -= start[1];
        
        start[0] += point[0];
        start[1] += point[1];
        
        points.stream().forEach((p) -> {
        p[0] += point[0];
        p[1] += point[1];
        });
        
        return this;*/
        point = point.move(-start.getX(), -start.getY());
        start = start.move(point.getX(), point.getY());

        for (int i = 0; i < points.size(); i++) {
            points.set(i, points.get(i).move(point.getX(), point.getY()));
        }

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(Point point) {
        points.add(point);
        return this;
    }

    @Override
    public void draw(GL gl) {
        gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
        gl.glBegin(GL.GL_POINTS);

        points.stream().forEach((point) -> {
            for (int i = point.getX() - 1; i <= point.getX() + 1; i++) {
                for (int j = point.getY() - 1; j <= point.getY() + 1; j++) {
                    gl.glVertex2i(i, j);
                }
            }
        });

        gl.glEnd();
    }
}
