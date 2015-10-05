package org.CG.infrastructure.structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import org.CG.infrastructure.Point;
import org.CG.infrastructure.Rational;

/**
 *
 * @author ldavid
 */
public class EdgeTable {

    HashMap<Integer, List<EdgeNode>> lines;

    public EdgeTable() {
        lines = new HashMap<>();
    }

    public List<EdgeNode> getEdgesAtLine(int y) {
        return lines.getOrDefault(y, null);
    }

    public EdgeTable addEdges(List<Pair<Point, Point>> edges) {
        edges.stream().forEach((edge) -> {
            addEdge(edge.getKey(), edge.getValue());
        });

        for (List<EdgeNode> l : lines.values()) {
            l.sort((EdgeNode o1, EdgeNode o2) -> {
                if (o1.equals(o2)) {
                    return 0;
                }

                if (o1.getCurrentX().gt(o2.getCurrentX())) {
                    return 1;
                } else {
                    return -1;
                }
            });
        }

        return this;
    }

    protected EdgeTable addEdge(Point start, Point end) {
        // Ignore horizontal lines, as they will cause
        // a exception to be thrown at @Rational class.
        if (start.getY() != end.getY()) {
            Point minimumYPoint, maximumYPoint;

            if (start.getY() <= end.getY()) {
                minimumYPoint = start;
                maximumYPoint = end;
            } else {
                minimumYPoint = end;
                maximumYPoint = start;
            }

            List<EdgeNode> edges = lines.get(minimumYPoint.getY());

            if (edges == null) {
                edges = new LinkedList<>();
                lines.put(minimumYPoint.getY(), edges);
            }

            edges.add(new EdgeNode(
                maximumYPoint.getY(),
                new Rational(minimumYPoint.getX()),
                new Rational(end.getX() - start.getX(), end.getY() - start.getY())
            ));
        }

        return this;
    }

}
