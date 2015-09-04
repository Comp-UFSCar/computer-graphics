package org.CG.infrastructure.editor;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GLCanvas;
import org.CG.drawings.Line;
import org.CG.infrastructure.ColorByte;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.DrawingsLoader;
import org.CG.infrastructure.Point;

/**
 *
 * @author ldavid
 */
public class Editor {
    
    private final List<Class<? extends Drawing>> availableDrawings;
    
    private final LinkedList<Drawing> drawings;
    private final LinkedList<Drawing> redos;
    
    private Class<? extends Drawing> currentDrawing = Line.class;
    private Mode mode;
    
    private final Random rand;
    
    public Editor() {
        availableDrawings = DrawingsLoader.getDrawingsClasses();
        
        drawings = new LinkedList<>();
        redos = new LinkedList<>();
        
        mode = Mode.DRAWING;
        rand = new Random();
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
        if (!drawings.isEmpty() && !drawings.getLast().isFinished()) {
            Drawing l = drawings.getLast();
            l.isFinished(true);
        }
    }
    
    public void onMousePressedOnCanvas(MouseEvent e, GLCanvas canvas) {
        redos.clear();
        
        Point point = new Point(e.getX(), canvas.getHeight() - e.getY());
        
        if (e.isControlDown()) {
            mode = Mode.MOVING;
            return;
        }
        
        mode = Mode.DRAWING;
        
        if (!drawings.isEmpty() && !drawings.getLast().isFinished()) {
            drawings.getLast().setNextCoordinate(point);
            return;
        }
        
        ColorByte color = ColorByte.random(rand).adjustBrightness(80);
        
        try {
            drawings.add(currentDrawing.newInstance()
                    .setColor(color)
                    .setStart(point));
            
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onMouseDraggedOnCanvas(MouseEvent e, GLCanvas canvas) {
        Point point = new Point(e.getX(), canvas.getHeight() - e.getY());
        
        if (!e.isControlDown() && mode == Mode.MOVING) {
            mode = Mode.IDLE;
            
        } else if (mode == Mode.MOVING) {
            drawings
                    .getLast()
                    .translate(point);
            
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
