package org.CG.editor;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GLCanvas;
import org.CG.drawings.Line;
import org.CG.infrastructure.structures.ColorByte;
import org.CG.infrastructure.base.Drawing;
import org.CG.infrastructure.DrawingsLoader;
import org.CG.infrastructure.structures.Point;

/**
 * Matrix Paint Editor.
 *
 * @author ldavid
 */
public class Editor {

    private final List<Class<? extends Drawing>> availableDrawings;

    private final LinkedList<Drawing> drawings;
    private final LinkedList<Drawing> redos;

    private Class<? extends Drawing> currentDrawing = Line.class;

    private Mode mode = Mode.MOVING;
    private State state;
    private ModeChangeListener modeChangeListener;

    private final Random rand;
    private ColorByte selectedColor;

    /**
     * Instantiates a new editor, without drawings and in Drawing state.
     */
    public Editor() {
        availableDrawings = DrawingsLoader.getDrawingsClasses();

        drawings = new LinkedList<>();
        redos = new LinkedList<>();

        state = State.IDLE;
        rand = new Random();
    }

    /**
     * Undo the last action. Must be at least one undo-able action and less than
     * 100 undo-ed actions.
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
     * Listens to mouse presses on the canvas, potentially drawing or starting
     * to draw new shapes.
     *
     * @param e the action performed by the mouse.
     * @param canvas the GL canvas object.
     */
    public void onMousePressedOnCanvas(MouseEvent e, GLCanvas canvas) {

        Point point = new Point(e.getX(), canvas.getHeight() - e.getY());

        if (e.isControlDown()) {
            state = State.MOVING;
        } else if (state == State.DRAWING || state == State.IDLE) {
            redos.clear();

            state = State.DRAWING;

            if (!drawings.isEmpty() && !drawings.getLast().isFinished()) {
                drawings.getLast().setNextCoordinate(point);
                return;
            }

            ColorByte color = this.getDrawingColor();

            try {
                drawings.add(currentDrawing.newInstance()
                    .setColor(color)
                    .setStart(point));

            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Listens to mouse drags on the canvas.
     *
     * @param e the action performed by the mouse.
     * @param canvas the GL canvas object.
     */
    public void onMouseDraggedOnCanvas(MouseEvent e, GLCanvas canvas) {
        Point point = new Point(e.getX(), canvas.getHeight() - e.getY());

        if (!e.isControlDown() && state == State.MOVING) {
            state = State.IDLE;

        } else if (state == State.MOVING) {
            drawings
                .getLast()
                .translate(point);

        } else if (state == State.DRAWING) {
            drawings
                .getLast()
                .updateLastCoordinate(point);
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
    public void setSelectedColor(ColorByte color) {
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
    protected ColorByte getDrawingColor() {
        if (selectedColor != null) {
            return selectedColor;
        }
        return ColorByte.random(rand).adjustBrightness(100);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        Mode previous = this.mode;
        this.mode = mode;

        if (modeChangeListener != null) {
            modeChangeListener.notify(previous, mode);
        }
    }

    public void setModeChangeListener(ModeChangeListener listener) {
        modeChangeListener = listener;
    }

    public static interface ModeChangeListener {

        public void notify(Mode previous, Mode current);
    }
}
