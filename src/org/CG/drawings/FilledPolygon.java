package org.CG.drawings;

import javax.media.opengl.GL;

/**
 *
 * @author ldavid
 */
public class FilledPolygon extends Polygon {

    @Override
    protected void drawShape(GL gl) {
        edges.stream().forEach((edge) -> {
            edge.draw(gl);
        });
    }
}
