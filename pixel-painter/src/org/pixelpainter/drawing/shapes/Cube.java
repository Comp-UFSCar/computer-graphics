package org.pixelpainter.drawings.shapes;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.media.opengl.GL;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.representations.Color;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class Cube extends Square {

    List<Rectangle> faces;
    Color[] colors;

    public Cube() {
        super(GL.GL_POLYGON);
        faces = new LinkedList<>();
        colors = new Color[6];

        Random r = new Random();

        for (int i = 0; i < 6; i++) {
            colors[i] = Color.random(r);
        }
    }

    protected List<Rectangle> updateFaces() {
        faces = new LinkedList<>();

        int[] planes = new int[]{0, 1, 2, 0, 1, 2};
        int i = 0;

        for (int position : new int[]{end.getX(), end.getY(), end.getZ(), start.getX(), start.getY(), start.getZ()}) {
            Rectangle s = new Rectangle();

            s
                    .setStart(start)
                    .setColor(colors[i])
                    .updateLastCoordinate(end);

            s.setPlane(planes[i]);
            s.setPlanePosition(position);

            faces.add(s);

            i++;
        }

        return faces;
    }

    public List<Rectangle> getFaces() {
        return faces;
    }

    @Override
    public Drawing moveTo(Vector v) {
        super.moveTo(v);

        updateFaces();
        return this;
    }

    /**
     * Update last coordinate based on point, but maintaining proportion of 1.0 for sides.
     *
     * @param point coordinate to where the square should be moved.
     * @return this.
     */
    @Override
    public Drawing updateLastCoordinate(Vector point) {
        int dx = point.getX() - start.getX();
        int dy = point.getY() - start.getY();

        end = Math.abs(dx) > Math.abs(dy)
                ? start.move(dx, (int) Math.signum(dy) * Math.abs(dx), Math.abs(dx))
                : start.move((int) Math.signum(dx) * Math.abs(dy), dy, Math.abs(dy));

        updateFaces();

        return this;
    }

    @Override
    protected void drawShape(GL gl) {
        faces.stream().forEach((face) -> {
            face.draw(gl);
        });
    }
}
