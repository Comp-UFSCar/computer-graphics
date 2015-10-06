package org.CG;

import com.sun.opengl.util.Animator;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.CG.editor.Editor;
import org.CG.infrastructure.Drawing;

/**
 * JOGL Frame for using the CG-Assignment-1 implemented features.
 */
public class CGAssignment1 implements GLEventListener {

    private final static String TITLE = "Matrix Paint";

    private static Editor editor;

    /**
     * Opens a new frame for using the CG-Assignment-1 implemented features.
     *
     * @param args if two integer are given, they will be used as width and
     * height.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CGAssignment1.class.getName()).log(Level.WARNING, null, ex);
        }

        editor = new Editor();
        
        int width = 1366;
        int height = 768;
        
        if(args.length == 2) {
            try {
                width = Integer.parseInt(args[0]);
                height = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                Logger.getLogger(CGAssignment1.class.getName()).log(Level.WARNING, null, ex);
            }
        }

        final JFrame frame = new JFrame(TITLE);
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new CGAssignment1());
        frame.add(canvas);
        frame.setSize(width, height);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    animator.stop();
                    System.exit(0);
                }).start();
            }
        });

        canvas.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    editor.onMousePressedOnCanvas(e, canvas);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    editor.finishLastDrawing();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    editor.onMouseReleasedOnCanvas(e, canvas);
                }
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    editor.onMouseDraggedOnCanvas(e, canvas);
                }
            }
        });

        canvas.addMouseWheelListener(new MouseAdapter() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    editor.undo();
                } else {
                    editor.redo();
                }
            }
        });

        // Interface elements definitions.
        JMenuBar mb = createMenuBar();

        // Center frame
        frame.setJMenuBar(mb);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    private static JButton createSimpleButton(String text) {
        JButton b = new JButton(text);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        return b;
    }

    private static JMenuBar createMenuBar() {
        JButton b;
        JMenuBar mb = new JMenuBar();
        JMenu m = new JMenu("Draw mode");

        for (Class<? extends Drawing> d : editor.getAvailableDrawings()) {
            String name = d.getSimpleName();
            b = createSimpleButton(name);

            b.addActionListener((ActionEvent e) -> {
                editor.setCurrentDrawing(d);
            });
            m.add(b);
        }

        mb.add(m);

        b = createSimpleButton("Undo");
        b.addActionListener((ActionEvent e) -> {
            editor.undo();
        });
        mb.add("Undo", b);

        b = createSimpleButton("Redo");
        b.addActionListener((ActionEvent e) -> {
            editor.redo();
        });
        mb.add("Redo", b);

        return mb;
    }

    /**
     * Initializes the GL options
     *
     * @param drawable
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
    }

    /**
     * Adjusts the frame according to window size
     *
     * @param drawable GL drawable object
     * @param x x-position of the frame inside the viewport
     * @param y y-position of the frame inside the viewport
     * @param width width of the viewport
     * @param height height of the viewport
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        height = Math.max(height, 1);

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -1, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * Draws the current drawings on screen
     *
     * @param drawable JOGL drawing object
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        editor.getDrawings().forEach(d -> {
            d.draw(gl);
        });

        gl.glFlush();
    }

    /**
     * Currently not used.
     *
     * @param drawable
     * @param modeChanged
     * @param deviceChanged
     */
    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
