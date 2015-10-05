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

    protected Point lastPoint;
    protected final LinkedList<Line> edges;

    public Polygon() {
        super();

        finished = false;
        edges = new LinkedList<>();
    }

    @Override
    public Drawing setStart(Point start) {
        super.setStart(start);
        lastPoint = start;

        return this;
    }

    @Override
    public Drawing translate(Point point) {
        Point lastTranslation = point;
        
        for (Line edge : edges) {
            edge.translate(lastTranslation);
            lastTranslation = edge.getEnd();
        }
        
        lastPoint = lastTranslation;

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

        edges.add((Line) new Line()
                .setColor(color)
                .setStart(lastPoint)
                .updateLastCoordinate(point));

        lastPoint = point;
        return this;
    }

    @Override
    public Drawing isFinished(boolean finish) {
        // Draws the last vector, linking the last coordinate with the first one.
        if (finish) {
            setNextCoordinate(start);
        }

        return super.isFinished(finish);
    }

    @Override
    protected void drawShape(GL gl) {
        edges.stream().forEach((edge) -> {
            edge.draw(gl);
        });
    }
}
