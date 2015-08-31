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
import javax.media.opengl.glu.GLU;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.CG.infrastructure.Editor;
import org.CG.infrastructure.Drawing;

/**
 * CGAssignment1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class CGAssignment1 implements GLEventListener {

    static int[] start, end;
    private static Editor editor;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CGAssignment1.class.getName()).log(Level.WARNING, null, ex);
        }

        editor = new Editor();

        final JFrame frame = new JFrame("Line drawing over pixel matrix");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new CGAssignment1());
        frame.add(canvas);
        frame.setSize(1366, 768);
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
                editor.onMousePressedOnCanvas(e, canvas);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                editor.onMouseReleasedOnCanvas(e, canvas);
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

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                editor.onMouseDraggedOnCanvas(e, canvas);
            }
        });

        // Interface elements definitions.
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

    @Override
    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -1, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        editor.getDrawings().forEach((d) -> {
            d.draw(gl);
        });

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
