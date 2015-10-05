package org.CG.infrastructure;

import java.util.Iterator;
import java.util.List;
import javafx.util.Pair;
import javax.media.opengl.GL;
import org.CG.infrastructure.structures.ActiveEdgeTable;
import org.CG.infrastructure.structures.EdgeNode;
import org.CG.infrastructure.structures.EdgeTable;

/**
 *
 * @author ldavid
 */
public class ScanLineAlgorithm {

    protected ActiveEdgeTable aet;
    protected EdgeTable et;
    protected int yMin, yMax;

    public ScanLineAlgorithm(List<Pair<Point, Point>> edgePoints) {

    }

    public ScanLineAlgorithm(List<Pair<Point, Point>> edgePoints, int yMin, int yMax) {
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

        this.yMin = yMin;
        this.yMax = yMax;
    }

    public ScanLineAlgorithm draw(GL gl) {
        for (int y = yMin; y <= yMax; y++) {
            aet.removeEdgesWithMaximumYOf(y);
            aet.addEdges(et.getEdgesAtLine(y));

            Iterator<EdgeNode> i = aet.getEdges().iterator();

            while (i.hasNext()) {
                EdgeNode current = i.next();
                EdgeNode next = i.next();

                for (Rational x = current.getCurrentX();
                    next.getCurrentX().gt(x);
                    x = x.add(new Rational(1))) {
                    
                    gl.glVertex2i(x.getInteger(), y);
                }
            }

            aet.nextScanLine();
        }

        return this;
    }

}
