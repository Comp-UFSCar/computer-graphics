package org.CG.drawings;

import java.util.LinkedList;
import java.util.List;
import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.abstractions.Point;

/**
 *
 * @author ldavid
 */
public class Cube extends Square {

    public Cube() {
        super(GL.GL_POLYGON);
    }

    protected List<List<Point>> getFaces() {
        List<List<Point>> faces = new LinkedList<>();

        List<Point> points = new LinkedList<>();
//        points.add(start);
//        points.add(new Point(end.getX(), start.getY(), start.getZ()));
//        points.add(new Point(end.getX(), end.getY(), start.getZ()));
//        points.add(new Point(start.getX(), end.getY(), start.getZ()));
//        points.add(start);
//
//        faces.add(points);

        points = new LinkedList<>();
        points.add(start);
        points.add(new Point(start.getX(), start.getY(), end.getZ()));
        points.add(new Point(start.getX(), end.getY(), end.getZ()));
        points.add(new Point(start.getX(), end.getY(), start.getZ()));
        points.add(start);

        faces.add(points);
//
//        points = new LinkedList<>();
//        points.add(start);
//        points.add(new Point(end.getX(), start.getY(), start.getZ()));
//        points.add(new Point(end.getX(), start.getY(), end.getZ()));
//        points.add(new Point(start.getX(), start.getY(), end.getZ()));
//        points.add(start);
//
//        faces.add(points);

        // ...
        return faces;
    }

    /**
     * Update last coordinate based on point, but maintaining proportion of 1.0 for sides.
     *
     * @param point coordinate to where the square should be moved.
     * @return this.
     */
    @Override
    public Drawing updateLastCoordinate(Point point) {
        int dx = point.getX() - start.getX();
        int dy = point.getY() - start.getY();

        end = Math.abs(dx) > Math.abs(dy)
                ? start.move(dx, (int) Math.signum(dy) * Math.abs(dx), Math.abs(dx))
                : start.move((int) Math.signum(dx) * Math.abs(dy), dy, Math.abs(dy));

        return this;
    }

    @Override
    public void drawShape(GL gl) {
        getFaces().stream().forEach((face) -> {
            face.stream().map((point) -> point.projectTo2d()).forEach((point) -> {
                gl.glVertex2i(point.getX(), point.getY());
//                gl.glVertex3i(point.getX(), point.getY(), point.getZ());
            });
        });
    }
}
