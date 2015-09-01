package org.CG.drawings;

import java.util.LinkedList;
import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;

/**
 *
 * @author ldavid
 */
public class Polygon extends Drawing {

    int[] lastPoint;
    LinkedList<Drawing> edges;

    public Polygon() {
        super();

        finished = false;
        edges = new LinkedList<>();
    }

    @Override
    public Drawing setStart(int[] start) {
        super.setStart(start);
        lastPoint = start;

        return setNextCoordinate(start);
    }

    @Override
    public Drawing translate(int[] point) {
        return this;
    }

    @Override
    public Drawing updateLastCoordinate(int[] point) {
        lastPoint = new int[]{point[0], point[1]};
        edges.getLast().updateLastCoordinate(point);

        return this;
    }

    @Override
    public Drawing setNextCoordinate(int[] point) {
        super.setNextCoordinate(point);

        edges.add(new Line()
            .setColor(color)
            .setStart(lastPoint)
            .updateLastCoordinate(point));

        lastPoint = new int[]{point[0], point[1]};
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
    public void draw(GL gl) {
        edges.stream().forEach((edge) -> {
            edge.draw(gl);
        });
    }
}
