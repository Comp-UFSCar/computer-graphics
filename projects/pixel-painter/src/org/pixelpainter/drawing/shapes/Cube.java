package org.pixelpainter.drawing.shapes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import javax.media.opengl.GL;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.Environment;
import org.pixelpainter.infrastructure.algorithms.scanline.ScanLineAlgorithm;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 * The Cube shape.
 *
 * @author ldavid
 */
public class Cube extends Square {

    List<Rectangle> faces;

    public Cube() {
        super(GL.GL_POLYGON);

        faces = new LinkedList<>();
    }

    @Override
    public Drawing update() {
        faces = new LinkedList<>();

        int[] planes = new int[]{0, 1, 2, 0, 1, 2};

        int i = 0;

        for (float position : new float[]{end.getX(), end.getY(), end.getZ(), start.getX(), start.getY(), start.getZ()}) {
            Rectangle s = new Rectangle();

            s
                    .setStart(start)
                    .reshape(end);

            s
                    .setPlane(planes[i])
                    .setPlanePosition((int) position)
                    .setColor(color.applyTone(Environment.getEnvironment().getLight().calculateLightingIntensity(s.getNormal())));

            faces.add(s);
            i++;
        }

        return this;
    }

    public List<Rectangle> getFaces() {
        return faces;
    }

    @Override
    public void moveTo(Vector v) {
        super.moveTo(v);

        update();
    }

    /**
     * Update last coordinate based on point, but maintaining proportion of 1.0 for sides.
     *
     * @param point coordinate to where the square should be moved.
     */
    @Override
    public void reshape(Vector point) {
        int dx = (int) point.getX() - (int) start.getX();
        int dy = (int) point.getY() - (int) start.getY();

        end = Math.abs(dx) > Math.abs(dy)
                ? start.add(dx, (int) Math.signum(dy) * Math.abs(dx), Math.abs(dx))
                : start.add((int) Math.signum(dx) * Math.abs(dy), dy, Math.abs(dy));

        update();
    }

    @Override
    public void draw(GL gl) {
        drawShape(gl);
    }

    @Override
    protected void drawShape(GL gl) {

        faces.stream().forEach((face) -> {
            // Set color to face color (this is precomputed in the update() method).
            gl.glColor3f(face.getColor().getRed(), face.getColor().getGreen(), face.getColor().getBlue());

            face.draw(gl);

            List<Pair<Vector, Vector>> edges = new LinkedList<>();
            Iterator<Vector> vertices = face.getVertices().iterator();

            Vector first, current;
            first = current = vertices.next().projectTo2d().truncate();

            while (vertices.hasNext()) {
                Vector next = vertices.next();

                edges.add(new Pair(current.projectTo2d().truncate(), next.projectTo2d().truncate()));
                current = next;
            }

            edges.add(new Pair(current.projectTo2d().truncate(), first.projectTo2d().truncate()));

            if (finished) {
                gl.glBegin(GL.GL_POINTS);

                new ScanLineAlgorithm(edges).draw(gl);

                gl.glEnd();
            }
        });
    }

}
