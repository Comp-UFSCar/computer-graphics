package org.CG.drawings;

import java.util.LinkedList;
import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.abstractions.Vector3;

/**
 * Polygon drawing using list of lines.
 *
 * @author ldavid
 */
public class Polygon extends Drawing {

    protected Vector3 lastPoint;
    protected final LinkedList<Line> lines;

    public Polygon() {
        super();

        finished = false;
        lines = new LinkedList<>();
    }

    @Override
    public Drawing setStart(Vector3 start) {
        super.setStart(start);
        lastPoint = start;

        return setNextCoordinate(start);
    }

    @Override
    public Drawing moveTo(Vector3 point) {
        Vector3 lastTranslation = point;
        
        for (Line edge : lines) {
            edge.moveTo(lastTranslation);
            lastTranslation = edge.getEnd();
        }
        
        lastPoint = lastTranslation;

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(Vector3 point) {
        lastPoint = point;
        lines.getLast().updateLastCoordinate(point);

        return this;
    }

    @Override
    public Drawing setNextCoordinate(Vector3 point) {
        super.setNextCoordinate(point);

        lines.add((Line) new Line()
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
        lines.stream().forEach((edge) -> {
            edge.draw(gl);
        });
    }
}
