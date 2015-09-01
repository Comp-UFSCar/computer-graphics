package org.CG.infrastructure.editor;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GLCanvas;
import org.CG.drawings.Line;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.DrawingsLoader;

/**
 *
 * @author ldavid
 */
public class Editor {

    List<Class<? extends Drawing>> availableDrawings;

    LinkedList<Drawing> drawings;
    LinkedList<Drawing> redos;

    Class<? extends Drawing> currentDrawing = Line.class;
    private Mode mode;

    public Editor() {
        availableDrawings = DrawingsLoader.getDrawingsClasses();

        drawings = new LinkedList<>();
        redos = new LinkedList<>();

        mode = Mode.DRAWING;
    }

    public void undo() {
        if (drawings.size() > 0 && redos.size() < 100) {
            redos.add(drawings.removeLast());
        }
    }

    public void redo() {
        if (redos.size() > 0) {
            drawings.add(redos.removeLast());
        }
    }

    public void finishLastDrawing() {
        if (!drawings.isEmpty() && !drawings.getLast().getFinished()) {
            Drawing l = drawings.getLast();
            l.setFinished(true);
        }
    }

    public void onMousePressedOnCanvas(MouseEvent e, GLCanvas canvas) {
        redos.clear();

        int[] point = new int[]{e.getX(), canvas.getHeight() - e.getY()};

        if (e.isControlDown()) {
            mode = Mode.MOVING;
            return;
        }

        mode = Mode.DRAWING;

        if (!drawings.isEmpty() && !drawings.getLast().getFinished()) {
            drawings.getLast().setNextCoordinate(point);
            return;
        }

        byte[] color = new byte[]{(byte) (Math.random() * 256), (byte) (Math.random() * 256), (byte) (Math.random() * 256)};

        try {
            drawings.add(currentDrawing.newInstance()
                .setColor(color)
                .setStart(point));

        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onMouseDraggedOnCanvas(MouseEvent e, GLCanvas canvas) {
        int[] point = {e.getX(), canvas.getHeight() - e.getY()};

        if (!e.isControlDown() && mode == Mode.MOVING) {
            mode = Mode.IDLE;

        } else if (mode == Mode.MOVING) {
            drawings
                .getLast()
                .setStart(point);

        } else if (mode == Mode.DRAWING) {
            drawings
                .getLast()
                .updateLastCoordinate(point);
        }
    }

    public void onMouseReleasedOnCanvas(MouseEvent e, GLCanvas canvas) {
    }

    public LinkedList<Drawing> getDrawings() {
        return drawings;
    }

    public List<Class<? extends Drawing>> getAvailableDrawings() {
        return availableDrawings;
    }

    public void setCurrentDrawing(Class<? extends Drawing> currentDrawing) {
        this.currentDrawing = currentDrawing;
    }
}
