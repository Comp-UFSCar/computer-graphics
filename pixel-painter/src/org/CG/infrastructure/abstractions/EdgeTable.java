package org.CG.infrastructure.abstractions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;

/**
 * Edge Table (ET) for the Scan-Line Algorithm.
 *
 * @author ldavid
 */
public class EdgeTable {

    HashMap<Integer, List<EdgeNode>> lines;

    public EdgeTable() {
        lines = new HashMap<>();
    }

    /**
     * Get all edges of the polygon that begin at @y.
     *
     *
     *
     * @param y the line being currently scanned.
     * @return the list or null, if not found.
     */
    public List<EdgeNode> getEdgesAtLine(int y) {
        return lines.getOrDefault(y, null);
    }

    /**
     * Add multiple @EdgeNodes to the table based on the edges start and ending.
     *
     * Furthermore, this operation sorts all lists in the table.
     *
     * @param edges the list of pairs (Vector3, Vector3) that describe the edges that will be inserted.
     * @return this.
     */
    public EdgeTable addEdges(List<Pair<Vector3, Vector3>> edges) {
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

    /**
     * Add @EdgeNode to the table based on a edge start and ending.
     *
     * @param start the origin of the edge.
     * @param end the end of the edge.
     * @return this.
     */
    protected EdgeTable addEdge(Vector3 start, Vector3 end) {
        // Ignore horizontal lines, as they will cause
        // a exception to be thrown at @Rational class.
        if (start.getY() != end.getY()) {
            Vector3 minimumYPoint, maximumYPoint;

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
                lines.put((int) minimumYPoint.getY(), edges);
            }

            edges.add(new EdgeNode(
                    (int) maximumYPoint.getY(),
                    new Rational((int) minimumYPoint.getX()),
                    new Rational((int) (end.getX() - start.getX()), (int) (end.getY() - start.getY()))
            ));
        }

        return this;
    }

}
