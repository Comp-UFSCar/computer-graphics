package org.pixelpainter.drawings.shapes;

import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import javax.media.opengl.GL;
import org.pixelpainter.infrastructure.representations.Vector;
import org.pixelpainter.infrastructure.algorithms.scanline.ScanLineAlgorithm;

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
