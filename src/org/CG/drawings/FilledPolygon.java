package org.CG.drawings;

import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import javax.media.opengl.GL;
import org.CG.infrastructure.Point;
import org.CG.infrastructure.ScanLineAlgorithm;

/**
 *
 * @author ldavid
 */
public class FilledPolygon extends Polygon {

    @Override
    protected void drawShape(GL gl) {
        int yMin = 0, yMax = 0;

        List<Pair<Point, Point>> edgePoints = new LinkedList<>();
        if (finished) {
            yMin = Math.min(lines.getFirst().getStart().getY(), lines.getFirst().getEnd().getY());
            yMax = Math.min(lines.getFirst().getStart().getY(), lines.getFirst().getEnd().getY());
        }

        for (Line line : lines) {
            edgePoints.add(new Pair<>(line.getStart(), line.getEnd()));

            if (finished) {
                if (line.getStart().getY() < yMin) {
                    yMin = line.getStart().getY();
                } else if (line.getStart().getY() < yMin) {
                    yMin = line.getStart().getY();
                }

                if (line.getEnd().getY() > yMax) {
                    yMax = line.getEnd().getY();
                } else if (line.getEnd().getY() > yMax) {
                    yMax = line.getEnd().getY();
                }
            }

            line.draw(gl);
        };

        if (finished) {
            gl.glColor3ub(color.getRed(), color.getGreen(), color.getBlue());
            gl.glBegin(glDrawingType);

            new ScanLineAlgorithm(edgePoints, yMin, yMax).draw(gl);

            gl.glEnd();
        }
    }

}
