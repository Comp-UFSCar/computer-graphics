package org.CG.infrastructure;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.media.opengl.GLCanvas;
import org.CG.infrastructure.drawings.Drawing;
import org.CG.infrastructure.drawings.LineInPixelMatrix;

/**
 *
 * @author ldavid
 */
public class Editor {

    LinkedList<Drawing> drawings;
    LinkedList<Drawing> redos;

    public Editor() {
        drawings = new LinkedList<>();
        redos = new LinkedList<>();
    }

    public void undo() {
        if (drawings.size() > 0) {
            redos.add(drawings.removeLast());
        }
    }

    public void redo() {
        if (redos.size() > 0) {
            drawings.add(redos.removeLast());
        }
    }

    public void onMousePressedOnCanvas(MouseEvent e, GLCanvas canvas) {
        int[] start = new int[]{e.getX(), canvas.getHeight() - e.getY()};

        // The mouse was pressed, instantiates a LineInPixelMatrix with the starting point and random colors.
        // Finally, adds it to the list of drawings that will be given to GL.
        drawings.add(new LineInPixelMatrix(
            new int[]{start[0], start[1], start[0], start[1],
                (int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256)}));
    }

    public void onMouseDraggedOnCanvas(MouseEvent e, GLCanvas canvas) {
        updateLineEndPoint(e, canvas);
    }

    public void onMouseReleasedOnCanvas(MouseEvent e, GLCanvas canvas) {
        updateLineEndPoint(e, canvas);
    }

    protected void updateLineEndPoint(MouseEvent e, GLCanvas canvas) {
        int[] end = new int[]{e.getX(), canvas.getHeight() - e.getY()};

        Drawing d = drawings.getLast();
        d.getParameters()[2] = end[0];
        d.getParameters()[3] = end[1];
        d.refresh();
    }

    public LinkedList<Drawing> getDrawings() {
        return drawings;
    }
}
