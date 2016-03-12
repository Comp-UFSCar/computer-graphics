package org.pixelpainter.drawing.shapes;

import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 * Pencil drawing using list of points.
 *
 * @author ldavid
 */
public class Pencil extends Drawing {

    private final List<Vector> points;

    /**
     * Instantiates a new Pencil with an empty list of points.
     */
    public Pencil() {
        super();
        points = new ArrayList<>();
    }

    /**
     * Translates every point by a certain amount.
     *
     * @param point determines dx/dy of the movement
     */
    @Override
    public void moveTo(Vector point) {
        point = point.add(-start.getX(), -start.getY());
        start = start.add(point.getX(), point.getY());

        for (int i = 0; i < points.size(); i++) {
            points.set(i, points.get(i).add(point.getX(), point.getY()));
        }
    }

    /**
     * Adds a new point to the list of points to be drawn.
     *
     * @param point new point in the collection
     */
    @Override
    public void reshape(Vector point) {
        points.add(point);
    }

    /**
     * Draws all the points on screen. Each point is represent by a 3x3 grid
     * around it, for better visualization.
     *
     * @param gl JOGL object
     */
    @Override
    public void drawShape(GL gl) {
        points.stream().forEach((point) -> {
            for (int i = (int)point.getX() - 1; i <= (int)point.getX() + 1; i++) {
                for (int j = (int)point.getY() - 1; j <= (int)point.getY() + 1; j++) {
                    gl.glVertex2i(i, j);
                }
            }
        });
    }
}
