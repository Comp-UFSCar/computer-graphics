package org.CG;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.CG.drawings.Cube;
import org.CG.editor.Editor;
import org.CG.editor.Camera;
import org.CG.infrastructure.structures.ColorByte;
import org.CG.infrastructure.base.Drawing;
import org.CG.infrastructure.structures.Vector;

/**
 * Matrix Paint main class.
 */
public class CGAssignment1 implements GLEventListener {
    
    private final static String TITLE = "Matrix Paint";
    
    private GLU glu;
    private GLUT glut;
    private static Camera camera;
    private static Editor editor;
    private static int height, width;

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
        Cube s = new Cube(2);
        s.move(new Vector(1, 10, 0));
        editor.getDrawings().add(s);
        
        width = 1366;
        height = 768;
        
        if (args.length == 2) {
            try {
                width = Integer.parseInt(args[0]);
                height = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                Logger.getLogger(CGAssignment1.class.getName()).log(Level.WARNING, null, ex);
            }
        }
        
        final JFrame frame = new JFrame(TITLE);
        GLCanvas canvas = new GLCanvas();
        
        CGAssignment1 program = new CGAssignment1();
        
        canvas.addGLEventListener(program);
        frame.add(canvas);
        frame.setSize(width, height);
        final Animator animator = new FPSAnimator(canvas, 60);
        frame.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    animator.stop();
                    System.exit(0);
                }).start();
            }
        });
        
        canvas.addKeyListener(new KeyListener() {
            
            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'a') {
                    camera.move(new Vector(-1, 0, 0));
                } else if (e.getKeyChar() == 'd') {
                    camera.move(new Vector(1, 0, 0));
                } else if (e.getKeyChar() == 'w') {
                    camera.move(new Vector(0, 0, -1));
                } else if (e.getKeyChar() == 's') {
                    camera.move(new Vector(0, 0, 1));
                } else if (e.getKeyChar() == 'q') {
                    camera.move(new Vector(0, -1, 0));
                } else if (e.getKeyChar() == 'e') {
                    camera.move(new Vector(0, 1, 0));
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
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
        frame.setJMenuBar(createMenuBar());

        // Center frame
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
        JMenu m = new JMenu("Mode");
        
        for (Class<? extends Drawing> d : editor.getAvailableDrawings()) {
            String name = d.getSimpleName();
            b = createSimpleButton(name);
            
            b.addActionListener((ActionEvent e) -> {
                editor.setCurrentDrawing(d);
            });
            m.add(b);
        }
        
        mb.add(m);

        // color picker
        m = new JMenu("Color");
        b = createSimpleButton("Random");
        b.addActionListener((ActionEvent e) -> {
            editor.useRandomColor();
        });
        m.add(b);
        b = createSimpleButton("Pick");
        b.addActionListener((ActionEvent e) -> {
            Color c = JColorChooser.showDialog(null, "Choose the color", Color.yellow);
            if (c != null) {
                editor.setSelectedColor(new ColorByte(
                    c.getRed(), c.getGreen(),
                    c.getBlue(), c.getAlpha()
                ));
            }
        });
        m.add(b);
        mb.add(m);
        
        b = createSimpleButton("<");
        b.addActionListener((ActionEvent e) -> {
            editor.undo();
        });
        mb.add("<", b);
        
        b = createSimpleButton(">");
        b.addActionListener((ActionEvent e) -> {
            editor.redo();
        });
        mb.add(">", b);
        
        return mb;
    }

    /**
     * Initializes the GL options
     *
     * @param drawable OpenGL API @drawable object.
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        glu = new GLU();
        glut = new GLUT();
        camera = new Camera(gl, glu);

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(1, 1, 1, 0);
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
        
        CGAssignment1.height = height;
        CGAssignment1.width = width;
        
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        
        glu.gluPerspective(45, (float) (width) / height, 2, 20);
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
        camera.syncGraphics();
        
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        configLightning(gl);
        
        editor.getDrawings().forEach(d -> {
            d.draw(gl, glut);
        });
        
        gl.glFlush();
    }

    /**
     * Currently not used.
     *
     * @param drawable OpenGL API drawable object.
     * @param modeChanged indicates if mode has changed.
     * @param deviceChanged indicates if device has changed.
     */
    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    
    private void configLightning(GL gl) {
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {-20, 10, -40, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {.4f, .4f, .4f, 1f};
        float[] lightColorSpecular = {.5f, .5f, .5f, 1f};

        // Set light parameters.
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_LIGHTING);

        // Set material properties.
        float[] rgba = {1f, .4f, .4f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
    }
}
