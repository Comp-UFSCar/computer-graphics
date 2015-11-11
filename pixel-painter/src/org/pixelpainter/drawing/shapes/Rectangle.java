package org.pixelpainter.drawing.shapes;

import java.util.LinkedList;
import java.util.List;
import javax.media.opengl.GL;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 * Rectangle drawing using two points.
 *
 * @author ldavid
 */
public class Rectangle extends Drawing {

    protected Vector end;
    protected byte plane;
    protected int planePosition;

    public Rectangle() {
        this(GL.GL_LINES);
    }

    public Rectangle(int drawingMethod) {
        super(drawingMethod);

        plane = 2;
        planePosition = 0;
    }

    @Override
    public Drawing moveTo(Vector v) {
        end = v.move(end.delta(start));
        start = v;

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(Vector point) {
        end = point;
        return this;
    }

    @Override
    protected void drawShape(GL gl) {
        getVertices().stream().map(p -> p.projectTo2d()).forEach(p -> {
            gl.glVertex2d(p.getX(), p.getY());
        });
    }

    @Override
    public Drawing setStart(Vector start) {
        super.setStart(start);
        end = start;

        return this;
    }

    public Vector getEnd() {
        return end;
    }

    public Rectangle setPlane(int plane) {
        if (plane < 0 || plane > 2) {
            throw new IllegalArgumentException("Invalid plane " + plane);
        }

        this.plane = (byte) plane;

        return this;
    }

    public byte getPlane() {
        return plane;
    }

    public Rectangle setPlanePosition(int planePosition) {
        this.planePosition = planePosition;

        return this;
    }

    public int getPlanePosition() {
        return planePosition;
    }

    /**
     * Returns vertices of the rectangle in the 3-dimensional space.
     *
     * @return list of points {@code p} (vectors {@code 0P}).
     */
    public List<Vector> getVertices() {
        List<Vector> points = new LinkedList<>();

        switch (plane) {
            case 0:
                points.add(new Vector(planePosition, start.getY(), start.getZ()));
                points.add(new Vector(planePosition, start.getY(), end.getZ()));
                points.add(new Vector(planePosition, end.getY(), end.getZ()));
                points.add(new Vector(planePosition, end.getY(), start.getZ()));
                break;

            case 1:
                points.add(new Vector(start.getX(), planePosition, start.getZ()));
                points.add(new Vector(end.getX(), planePosition, start.getZ()));
                points.add(new Vector(end.getX(), planePosition, end.getZ()));
                points.add(new Vector(start.getX(), planePosition, end.getZ()));
                break;

            case 2:
                points.add(new Vector(start.getX(), start.getY(), planePosition));
                points.add(new Vector(start.getX(), end.getY(), planePosition));
                points.add(new Vector(end.getX(), end.getY(), planePosition));
                points.add(new Vector(end.getX(), start.getY(), planePosition));
                break;
        }

        return points;
    }

    Vector getNormal() {
        Vector a, b, d = end.delta(start);

        switch (plane) {
            case 0:
                a = new Vector(0, d.getY(), 0);
                b = new Vector(0, d.getY(), d.getZ());
                break;
            case 1:
                a = new Vector(d.getX(), 0, 0);
                b = new Vector(0, 0, d.getZ());
                break;
            default:
                a = new Vector(d.getX(), 0, 0);
                b = new Vector(0, d.getY(), 0);
                break;
        }

        return a.cross(b).normalize();
    }

}
