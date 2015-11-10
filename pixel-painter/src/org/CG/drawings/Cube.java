package org.CG.drawings;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.abstractions.ColorByte;
import org.CG.infrastructure.abstractions.Point;

/**
 *
 * @author ldavid
 */
public class Cube extends Square {

    public Cube() {
        super(GL.GL_POLYGON);
    }

    protected List<Rectangle> getFaces() {
        List<Rectangle> faces = new LinkedList<>();

        int[] planes = new int[]{0, 1, 2, 0, 1, 2};
        double[] planePositions = new double[]{end.getX(), end.getY(), end.getZ(), start.getX(), start.getY(), start.getZ()};

        int i = 0;

        for (double position : planePositions) {
            Rectangle s = new Rectangle();
            s.setStart(start);
            s.updateLastCoordinate(end);
            s.setPlane(planes[i]);
            s.setPlanePosition(position);
            faces.add(s);

            i++;
        }

        return faces;
    }

    protected ColorByte[] getFacesColors() {
        return new ColorByte[]{
            new ColorByte(200, 0, 255),
            new ColorByte(200, 255, 0),
            new ColorByte(0, 0, 255),
            new ColorByte(200, 0, 0),
            new ColorByte(50, 100, 255),
            new ColorByte(0, 100, 155)
        };
    }

    /**
     * Update last coordinate based on point, but maintaining proportion of 1.0 for sides.
     *
     * @param point coordinate to where the square should be moved.
     * @return this.
     */
    @Override
    public Drawing updateLastCoordinate(Point point) {
        double dx = point.getX() - start.getX();
        double dy = point.getY() - start.getY();

        end = Math.abs(dx) > Math.abs(dy)
                ? start.move(dx, (int) Math.signum(dy) * Math.abs(dx), Math.abs(dx))
                : start.move((int) Math.signum(dx) * Math.abs(dy), dy, Math.abs(dy));

        return this;
    }

    @Override
    protected void drawShape(GL gl) {
        int i = 0;
        ColorByte[] colors = getFacesColors();

        for (Rectangle face : getFaces()) {
            face.setColor(colors[i]);
            face.draw(gl);

            i++;
        }
    }
}
