package org.CG.drawings;

import java.util.LinkedList;
import java.util.List;
import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.abstractions.Point;

/**
 * Rectangle drawing using two points.
 *
 * @author ldavid
 */
public class Rectangle extends Drawing {

    protected Point end;
    protected byte plane;
    protected double planePosition;

    public Rectangle() {
        this(GL.GL_POLYGON);
    }

    public Rectangle(int drawingMethod) {
        super(drawingMethod);

        plane = 2;
        planePosition = 0;
    }

    @Override
    public Drawing translate(Point point) {
        Point t = new Point(end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ());

        start = point;
        end = point.move(t.getX(), t.getY(), t.getZ());

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(Point point) {
        end = point;
        return this;
    }

    @Override
    protected void drawShape(GL gl) {
        List<Point> points = new LinkedList<>();

        switch (plane) {
            case 0:
                points.add(new Point(planePosition, start.getY(), start.getZ()));
                points.add(new Point(planePosition, start.getY(), end.getZ()));
                points.add(new Point(planePosition, end.getY(), end.getZ()));
                points.add(new Point(planePosition, end.getY(), start.getZ()));
                break;

            case 1:
                points.add(new Point(start.getX(), planePosition, start.getZ()));
                points.add(new Point(end.getX(), planePosition, start.getZ()));
                points.add(new Point(end.getX(), planePosition, end.getZ()));
                points.add(new Point(start.getX(), planePosition, end.getZ()));
                break;

            case 2:
                points.add(new Point(start.getX(), start.getY(), planePosition));
                points.add(new Point(start.getX(), end.getY(), planePosition));
                points.add(new Point(end.getX(), end.getY(), planePosition));
                points.add(new Point(end.getX(), start.getY(), planePosition));
                break;
        }

        points.stream().map(p -> p.projectTo2d()).forEach(p -> {
            gl.glVertex2d(p.getX(), p.getY());
        });
    }

    @Override
    public Drawing setStart(Point start) {
        super.setStart(start);
        end = start;

        return this;
    }

    public Drawing setPlane(int plane) {
        if (plane < 0 || plane > 2) {
            throw new IllegalArgumentException("Invalid plane " + plane);
        }

        this.plane = (byte) plane;

        return this;
    }

    public byte getPlane() {
        return plane;
    }

    public Drawing setPlanePosition(double planePosition) {
        this.planePosition = planePosition;

        return this;
    }
}
