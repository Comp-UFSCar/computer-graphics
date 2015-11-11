package org.CG.drawings;

import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import javax.media.opengl.GL;
import org.CG.infrastructure.abstractions.Vector;
import org.CG.infrastructure.algorithms.scanline.ScanLineAlgorithm;

/**
 * Filled Polygon drawing using Polygon and ScanLineAlgorithm.
 *
 * @author ldavid
 */
public class FilledPolygon extends Polygon {

    @Override
    protected void drawShape(GL gl) {
        List<Pair<Vector, Vector>> edgePoints = new LinkedList<>();

        lines.stream().forEach((line) -> {
            edgePoints.add(new Pair<>(line.getStart(), line.getEnd()));
            line.draw(gl);
        });
;
        if (finished) {
            gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
            gl.glBegin(glDrawingType);

            new ScanLineAlgorithm(edgePoints).draw(gl);

            gl.glEnd();
        }
    }

}
