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

    protected LinkedList<Line> lines;
    protected Vector end;

    public Polygon() {
        super();

        finished = false;
        lines = new LinkedList<>();
    }

    @Override
    public Drawing setStart(Vector start) {
        super.setStart(start);
        end = start;

        return setNextCoordinate(start);
    }

    @Override
    public void moveTo(Vector point) {
        Vector lastTranslation = point;

        for (Line edge : lines) {
            edge.moveTo(lastTranslation);
            lastTranslation = edge.getEnd();
        }

        end = lastTranslation;
    }

    @Override
    public void reshape(Vector point) {
        end = point;
        lines.getLast().reshape(point);
    }

    @Override
    public Drawing setNextCoordinate(Vector point) {
        super.setNextCoordinate(point);

        lines.add(new Line());
        lines.getLast()
                .setColor(color)
                .setStart(end)
                .reshape(point);

        end = point;
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
