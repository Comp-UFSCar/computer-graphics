package org.CG.infrastructure;

import java.util.Iterator;
import java.util.List;
import javafx.util.Pair;
import javax.media.opengl.GL;
import org.CG.infrastructure.structures.ActiveEdgeTable;
import org.CG.infrastructure.structures.EdgeNode;
import org.CG.infrastructure.structures.EdgeTable;

/**
 * Performs the Scan-Line Algorithm to fill a polygon.
 * 
 * @author ldavid
 */
public class ScanLineAlgorithm {

    protected ActiveEdgeTable aet;
    protected EdgeTable et;
    protected int yMin, yMax;

    /**
     * Instatiates a new solver for the Scanline Algorithm.
     * @param edgePoints list of the edges of the polygon.
     */
    public ScanLineAlgorithm(List<Pair<Point, Point>> edgePoints) {
        if (yMin > yMax) {
            throw new IllegalArgumentException(
                "yMin (" + yMin + ") cannot be greater than yMax (" + yMax + ").");
        }

        if (edgePoints == null || edgePoints.isEmpty()) {
            throw new IllegalArgumentException(
                "List of edge points must contain at least one element.");
        }

        et = new EdgeTable().addEdges(edgePoints);
        aet = new ActiveEdgeTable();

        Pair<Point, Point> first = edgePoints.get(0);

        // Find the minimum and maximum Y of all set.
        yMin = Math.min(first.getKey().getY(), first.getValue().getY());
        yMax = Math.max(first.getKey().getY(), first.getValue().getY());

        edgePoints.stream().forEach((p) -> {
            int localYMin = Math.min(p.getKey().getY(), p.getValue().getY());
            int localYMax = Math.max(p.getKey().getY(), p.getValue().getY());

            if (localYMin < yMin) {
                yMin = localYMin;
            }

            if (localYMax > yMax) {
                yMax = localYMax;
            }
        });
    }

    /**
     * Fill the polygon represented by @edgePoints.
     *
     * @param gl OpenGL drawing API object.
     * @return self.
     */
    public ScanLineAlgorithm draw(GL gl) {
        for (int y = yMin; y <= yMax; y++) {
            aet.removeEdgesWithMaximumYOf(y);
            aet.addEdges(et.getEdgesAtLine(y));

            Iterator<EdgeNode> i = aet.getEdges().iterator();
            while (i.hasNext()) {
                Rational left = i.next().getCurrentX().ceil();
                Rational right = i.next().getCurrentX();

                for (Rational x = left; right.gt(x); x = x.add(new Rational(1))) {
                    gl.glVertex2i(x.getInteger(), y);
                }
            }

            aet.nextScanLine();
        }

        return this;
    }

}
