package org.CG.drawings;

import java.util.LinkedList;
import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;

/**
 *
 * @author ldavid
 */
public class Polygon extends Drawing {

    LinkedList<Line> edges;

    public Polygon() {
        super();

        finished = false;
        edges = new LinkedList<>();
    }
    
    @Override
    public Drawing setStart(int[] start) {
        edges.add(Line().setStart(start);
        return super.setStart(start);
    }
    
    @Override
    public Drawing translate(int[] point) {
        return this;
    }
    
    @Override
    public void updateLastCoordinateInputted(int[] point) {
        edges.getLast().updateLastCoordinateInputted(point);
    }

    @Override
    public void draw(GL gl) {
        edges.stream().forEach((edge) -> {
            edge.draw(gl);
        });
    }
}
(((
