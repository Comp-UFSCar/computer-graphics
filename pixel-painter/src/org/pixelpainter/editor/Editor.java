package org.pixelpainter.editor;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GLCanvas;
import org.pixelpainter.infrastructure.representations.Color;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.drawing.DrawingsLoader;
import org.pixelpainter.drawing.shapes.Cube;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 * Matrix Paint Editor.
 *
 * @author ldavid
 */
public class Editor {

    private final List<Class<? extends Drawing>> availableDrawings;

    private final LinkedList<Drawing> drawings;
    private final LinkedList<Drawing> redos;

    private Class<? extends Drawing> currentDrawing = Cube.class;
    private Mode mode;

    private final Random rand;
    private Color selectedColor;

    /**
     * Instantiates a new editor, without drawings and in Drawing mode.
     */
    public Editor() {
        availableDrawings = DrawingsLoader.getDrawingsClasses();

        drawings = new LinkedList<>();
        redos = new LinkedList<>();

        mode = Mode.DRAWING;
        rand = new Random();
    }

    /**
     * Undo the last action. Must be at least one undo-able action and less than 100 undo-ed actions.
     */
    public void undo() {
        if (drawings.size() > 0 && redos.size() < 100) {
            redos.add(drawings.removeLast());
        }
    }

    /**
     * Redo the last undone action.
     */
    public void redo() {
        if (redos.size() > 0) {
            drawings.add(redos.removeLast());
        }
    }

    /**
     * Finish the current drawing. This will close polygons if applicable.
     */
    public void finishLastDrawing() {
        if (!drawings.isEmpty() && !drawings.getLast().isFinished()) {
            Drawing l = drawings.getLast();
            l.isFinished(true);
        }
    }

    /**
     * Listens to mouse presses on the canvas, potentially drawing or starting to draw new shapes.
     *
     * @param e the action performed by the mouse.
     * @param canvas the GL canvas object.
     */
    public void onMousePressedOnCanvas(MouseEvent e, GLCanvas canvas) {
        redos.clear();

        Vector point = new Vector(e.getX(), canvas.getHeight() - e.getY());

        if (e.isControlDown()) {
            mode = Mode.MOVING;
            return;
        }

        mode = Mode.DRAWING;

        if (!drawings.isEmpty() && !drawings.getLast().isFinished()) {
            drawings.getLast().setNextCoordinate(point);
            return;
        }

        Color color = this.getDrawingColor();

        try {
            drawings.add(currentDrawing.newInstance()
                    .setColor(color)
                    .setStart(point));

        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Listens to mouse drags on the canvas.
     *
     * @param e the action performed by the mouse.
     * @param canvas the GL canvas object.
     */
    public void onMouseDraggedOnCanvas(MouseEvent e, GLCanvas canvas) {
        Vector point = new Vector(e.getX(), canvas.getHeight() - e.getY());

        if (!e.isControlDown() && mode == Mode.MOVING) {
            mode = Mode.IDLE;

        } else if (mode == Mode.MOVING) {
            drawings
                    .getLast()
                    .moveTo(point);

        } else if (mode == Mode.DRAWING) {
            drawings
                    .getLast()
                    .reshape(point);
        }
    }

    /**
     * Listens to mouse release on the canvas.
     *
     * @param e the action performed by the mouse.
     * @param canvas the GL canvas object.
     */
    public void onMouseReleasedOnCanvas(MouseEvent e, GLCanvas canvas) {
    }

    /**
     * Gets the collection of all current drawings.
     *
     * @return list of all drawings.
     */
    public LinkedList<Drawing> getDrawings() {
        return drawings;
    }

    /**
     * Gets the collection of possible Drawing shapes.
     *
     * @return list of Drawings that can be drawn.
     */
    public List<Class<? extends Drawing>> getAvailableDrawings() {
        return availableDrawings;
    }

    /**
     * Sets the drawing shape for the current drawing.
     *
     * @param currentDrawing shape for current drawing.
     */
    public void setCurrentDrawing(Class<? extends Drawing> currentDrawing) {
        this.currentDrawing = currentDrawing;
    }

    /**
     * Sets the color for the next drawings.
     *
     * @param color the selected color, or null for random.
     */
    public void setSelectedColor(Color color) {
        this.selectedColor = color;
    }

    /**
     * Sets the editor to randomize a color for each drawing.
     */
    public void useRandomColor() {
        this.setSelectedColor(null);
    }

    /**
     * Gets the color for the next drawing, either selected or randomized.
     *
     * @return the color the next shape should be drawn.
     */
    protected Color getDrawingColor() {
        if (selectedColor != null) {
            return selectedColor;
        }
        return Color.random(rand);
    }

    public void update() {
        drawings.stream().forEach((e) -> e.update());
    }
}
