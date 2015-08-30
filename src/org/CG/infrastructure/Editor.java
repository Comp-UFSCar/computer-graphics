package org.CG.infrastructure;

import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GLCanvas;
import org.CG.infrastructure.drawings.Drawing;
import org.CG.infrastructure.drawings.Line;
import org.CG.infrastructure.drawings.Pencil;

/**
 *
 * @author ldavid
 */
public class Editor {

    LinkedList<Class<? extends Drawing>> drawMode;

    LinkedList<Drawing> drawings;
    LinkedList<Drawing> redos;

    Class<? extends Drawing> currentDrawMode = Line.class;

    public Editor() {
        drawMode = new LinkedList<>();
        drawMode.add(Line.class);
        drawMode.add(Pencil.class);

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
        
        if (currentDrawMode.equals(Line.class)) {
            d = new Line(point, color);
        } else if (currentDrawMode.equals(Pencil.class)) {
            d = new Pencil(point, color);
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

    public LinkedList<Class<? extends Drawing>> getDrawModes() {
        return drawMode;
    }

    public void setDrawMode(Class<? extends Drawing> currentDrawMode) {
        this.currentDrawMode = currentDrawMode;
    }
}
