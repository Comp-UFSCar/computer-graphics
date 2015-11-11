package org.CG.infrastructure.algorithms.scanline;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Active Edge Table (AET) for the Scan-Line Algorithm.
 *
 * @author ldavid
 */
public class ActiveEdgeTable {

    List<EdgeNode> edges = new LinkedList<>();

    /**
     * Add a list of @EdgeNode to the AET.
     * 
     * If the current line scanned is @y, should insert only nodes with 
     * yMinimum is equal to @y.
     * 
     * ActiveEdgeTable ignores empty or null lists.
     *
     * @param edges list containing @EdgeNodes that will be merged
     * into the current scanned line.
     * @return this.
     */
    public boolean addEdges(List<EdgeNode> edges) {
        if (edges != null && !edges.isEmpty()) {
            this.edges.addAll(edges);
            sortEdgesByTheirXCoordinate();
            
            return true;
        }
        
        return false;
    }

    /**
     * Signal AET that all nodes should have their x incremented by their
     * respective delta.
     * 
     * At the end of this operation, the list of nodes will also be sorted.
     *
     * @return this
     */
    public ActiveEdgeTable nextScanLine() {
        edges.stream().forEach((e) -> {
            e.goToNextScanLine();
        });

        return sortEdgesByTheirXCoordinate();
    }

    /**
     * Remove all @EdgeNode that have their maximumY equal to @y.
     *
     * @param y coordinate used for comparison during the removal process.
     * @return true, if any element was removed. False, otherwise.
     */
    public boolean removeEdgesWithMaximumYOf(int y) {
        boolean anyRemoved = false;
        
        Iterator<EdgeNode> i = edges.iterator();

        while (i.hasNext()) {
            if (i.next().getMaximumY() == y) {
                i.remove();
                anyRemoved = true;
            }
        }
        
        return anyRemoved;
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
