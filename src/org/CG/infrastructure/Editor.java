package org.CG.infrastructure;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.media.opengl.GLCanvas;
import org.CG.infrastructure.drawings.Circle;
import org.CG.infrastructure.drawings.Drawing;
import org.CG.infrastructure.drawings.Line;
import org.CG.infrastructure.drawings.Pencil;

/**
 *
 * @author ldavid
 */
public class Editor {

    LinkedList<Class<? extends Drawing>> availableDrawings;

    LinkedList<Drawing> drawings;
    LinkedList<Drawing> redos;

    Class<? extends Drawing> currentDrawingMode = Line.class;

    public Editor() {
        availableDrawings = new LinkedList<>();
        availableDrawings.add(Line.class);
        availableDrawings.add(Pencil.class);
        availableDrawings.add(Circle.class);

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
        int[] point = new int[]{e.getX(), canvas.getHeight() - e.getY()};
        byte[] color = new byte[]{(byte) (Math.random() * 256), (byte) (Math.random() * 256), (byte) (Math.random() * 256)};

        Drawing d = null;
        
        if (currentDrawingMode.equals(Line.class)) {
            d = new Line(point, color);
        } else if (currentDrawingMode.equals(Pencil.class)) {
            d = new Pencil(point, color);
        } else if (currentDrawingMode.equals(Circle.class)) {
            d = new Circle(point, color);
        }

        drawings.add(d);
    }

    public void onMouseDraggedOnCanvas(MouseEvent e, GLCanvas canvas) {
        drawings
            .getLast()
            .updateLastCoordinateInputted(new int[]{e.getX(), canvas.getHeight() - e.getY()});
    }

    public void onMouseReleasedOnCanvas(MouseEvent e, GLCanvas canvas) {

    }

    public LinkedList<Drawing> getDrawings() {
        return drawings;
    }

    public LinkedList<Class<? extends Drawing>> getAvailableDrawings() {
        return availableDrawings;
    }

    public void setCurrentDrawingMode(Class<? extends Drawing> currentDrawingMode) {
        this.currentDrawingMode = currentDrawingMode;
    }
}
