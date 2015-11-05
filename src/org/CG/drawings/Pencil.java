package org.CG.drawings;

import java.util.ArrayList;
import org.CG.infrastructure.Drawing;
import java.util.List;
import javax.media.opengl.GL;
import org.CG.infrastructure.Point;

/**
 * Pencil drawing using list of points.
 *
 * @author ldavid
 */
public class Pencil extends Drawing {

    private final List<Point> points;

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
     * @return this
     */
    @Override
    public Drawing translate(Point point) {
        point = point.move(-start.getX(), -start.getY());
        start = start.move(point.getX(), point.getY());

        for (int i = 0; i < points.size(); i++) {
            points.set(i, points.get(i).move(point.getX(), point.getY()));
        }

        return this;
    }

    /**
     * Adds a new point to the list of points to be drawn.
     *
     * @param point new point in the collection
     * @return this
     */
    @Override
    public Drawing updateLastCoordinate(Point point) {
        points.add(point);
        return this;
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
            for (int i = point.getX() - 1; i <= point.getX() + 1; i++) {
                for (int j = point.getY() - 1; j <= point.getY() + 1; j++) {
                    gl.glVertex2i(i, j);
                }
            }
        });
    }
}
