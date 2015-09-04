package org.CG.drawings;

import java.util.LinkedList;
import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.Point;

/**
 *
 * @author ldavid
 */
public class Polygon extends Drawing {

    private Point lastPoint;
    private final LinkedList<Drawing> edges;

    public Polygon() {
        super();

        finished = false;
        edges = new LinkedList<>();
    }

    @Override
    public Drawing setStart(Point start) {
        super.setStart(start);
        lastPoint = start;

        return setNextCoordinate(start);
    }

    @Override
    public Drawing translate(Point point) {
        super.translate(point);
        
        edges.stream().forEach((e) -> {
            e.translate(point);
        });

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(Point point) {
        lastPoint = point;
        edges.getLast().updateLastCoordinate(point);

        return this;
    }

    @Override
    public Drawing setNextCoordinate(Point point) {
        super.setNextCoordinate(point);

        edges.add(new Line()
                .setColor(color)
                .setStart(lastPoint)
                .updateLastCoordinate(point));

        lastPoint = point;
        return this;
    }

    @Override
    public Drawing setFinished(boolean finish) {
        // Draws the last vector, linking the last coordinate with the first one.
        if (finish) {
            setNextCoordinate(start);
        }

        return super.setFinished(finish);
    }

    @Override
    protected void drawShape(GL gl) {
        edges.stream().forEach((edge) -> {
            edge.draw(gl);
        });
    }
}
