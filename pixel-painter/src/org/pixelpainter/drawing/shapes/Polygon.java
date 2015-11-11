package org.pixelpainter.drawing.shapes;

import java.util.LinkedList;
import javax.media.opengl.GL;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 * Polygon drawing using list of lines.
 *
 * @author ldavid
 */
public class Polygon extends Drawing {

    protected Vector lastPoint;
    protected final LinkedList<Line> lines;

    public Polygon() {
        super();

        finished = false;
        lines = new LinkedList<>();
    }

    @Override
    public Drawing setStart(Vector start) {
        super.setStart(start);
        lastPoint = start;

        return setNextCoordinate(start);
    }

    @Override
    public Drawing moveTo(Vector point) {
        Vector lastTranslation = point;
        
        for (Line edge : lines) {
            edge.moveTo(lastTranslation);
            lastTranslation = edge.getEnd();
        }
        
        lastPoint = lastTranslation;

        return this;
    }

    @Override
    public Drawing updateLastCoordinate(Vector point) {
        lastPoint = point;
        lines.getLast().updateLastCoordinate(point);

        return this;
    }

    @Override
    public Drawing setNextCoordinate(Vector point) {
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
