package org.CG.infrastructure.structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ldavid
 */
public class ActiveEdgeTable {

    List<EdgeNode> edges = new LinkedList<>();

    public ActiveEdgeTable addEdges(List<EdgeNode> edges) {
        if (edges != null) {
            this.edges.addAll(edges);
        }

        return sortEdgesByTheirXCoordinate();
    }

    public ActiveEdgeTable nextScanLine() {
        edges.stream().forEach((e) -> {
            e.goToNextScanLine();
        });

        return sortEdgesByTheirXCoordinate();
    }

    public void removeEdgesWithMaximumYOf(int y) {
        Iterator<EdgeNode> i = edges.iterator();

        while (i.hasNext()) {
            if (i.next().getMaximumY() == y) {
                i.remove();
            }
        }
    }

    protected ActiveEdgeTable sortEdgesByTheirXCoordinate() {
        edges.sort((EdgeNode o1, EdgeNode o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }

            if (o1.getCurrentX().gt(o2.getCurrentX())) {
                return 1;
            } else {
                return -1;
            }
        });

        return this;
    }

    public List<EdgeNode> getEdges() {
        return edges;
    }

}
