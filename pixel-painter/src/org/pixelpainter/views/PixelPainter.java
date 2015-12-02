package org.pixelpainter.views;

import com.sun.opengl.util.Animator;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import org.pixelpainter.infrastructure.representations.Color;
import org.pixelpainter.drawing.Drawing;
import org.pixelpainter.infrastructure.Environment;
import org.pixelpainter.infrastructure.representations.Vector;

/**
 * Matrix Paint runner.
 *
 */
public class PixelPainter implements GLEventListener {

    final static String TITLE = "Pixelpaint";
    final static String ICON = "resources/icon.png";

    private static Environment env;

    /**
     * Opens a new frame for using the CG-Assignment-1 implemented features.
     *
     * @param args if two integer are given, they will be used as width and height.
     */
    public static void main(String[] args) {
        env = Environment.getEnvironment();

        initGraphicComponents();
    }

    private static void initGraphicComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PixelPainter.class.getName()).log(Level.WARNING, null, ex);
        }

        int width = 1366;
        int height = 768;

        final JFrame frame = new JFrame(TITLE);
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new PixelPainter());
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
                    env.getEditor().onMousePressedOnCanvas(e, canvas);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    env.getEditor().finishLastDrawing();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    env.getEditor().onMouseReleasedOnCanvas(e, canvas);
                }
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    env.getEditor().onMouseDraggedOnCanvas(e, canvas);
                }
            }
        });

        canvas.addMouseWheelListener(new MouseAdapter() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    env.getEditor().undo();
                } else {
                    env.getEditor().redo();
                }
            }
        });

        frame.setJMenuBar(createMenuBar());
        frame.setLocationRelativeTo(null);

        ImageIcon img = new ImageIcon(ICON);
        frame.setIconImage(img.getImage());

        frame.setVisible(true);
        animator.start();
    }

    private static JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu menu;
        JMenuItem item;

        // Drawings.
        menu = new JMenu("Draw mode");
        for (Class<? extends Drawing> d : env.getEditor().getAvailableDrawings()) {
            String name = d.getSimpleName();
            item = new JMenuItem(name);
            item.addActionListener((ActionEvent e) -> env.getEditor().setCurrentDrawing(d));
            menu.add(item);
        }
        bar.add(menu);

        // Edit.
        menu = new JMenu("Edit");
        item = new JMenuItem("Undo");
        item.addActionListener((ActionEvent e) -> env.getEditor().undo());
        menu.add(item);

        item = new JMenuItem("Redo");
        item.addActionListener((ActionEvent e) -> env.getEditor().redo());
        menu.add(item);

        bar.add(menu);

        // Color.
        menu = new JMenu("Color");
        item = new JMenuItem("Random");
        item.addActionListener((ActionEvent e) -> {
            env.getEditor().useRandomColor();
        });
        menu.add(item);
        item = new JMenuItem("Pick");
        item.addActionListener((ActionEvent e) -> {
            java.awt.Color c = JColorChooser.showDialog(null, "Choose the color", java.awt.Color.yellow);
            if (c != null) {
                env.getEditor().setSelectedColor(new Color(
                        (byte) c.getRed(), (byte) c.getGreen(),
                        (byte) c.getBlue(), (byte) c.getAlpha()
                ));
            }
        });
        menu.add(item);
        bar.add(menu);

        // Lighting.
        menu = new JMenu("Lighting");

        menu.add(new JLabel("Intensity"));
        JSlider s = new JSlider();
        s.setValue((int) (100 * env.getLight().getIntensity()));
        s.addChangeListener((ChangeEvent e) -> env.getLight().setIntensity(((JSlider) e.getSource()).getValue() / 100f));
        menu.add(s);
        menu.addSeparator();

        menu.add(new JLabel("Ambient Intensity"));
        s = new JSlider();
        s.setValue((int) (100 * env.getLight().getAmbientIntensity()));
        s.addChangeListener((ChangeEvent e) -> env.getLight().setAmbientIntensity(((JSlider) e.getSource()).getValue() / 100f));
        menu.add(s);
        menu.addSeparator();

        menu.add(new JLabel("Ambient Reflection"));
        s = new JSlider();
        s.setValue((int) (100 * env.getLight().getAmbientReflection()));
        s.addChangeListener((ChangeEvent e) -> env.getLight().setAmbientReflection(((JSlider) e.getSource()).getValue() / 100f));
        menu.add(s);
        menu.addSeparator();

        menu.add(new JLabel("Diffuse Reflection"));
        s = new JSlider();
        s.setValue((int) (100 * env.getLight().getDiffuseReflection()));
        s.addChangeListener((ChangeEvent e) -> env.getLight().setDiffuseReflection(((JSlider) e.getSource()).getValue() / 100f));
        menu.add(s);
        menu.addSeparator();

        menu.add(new JLabel("Light direction"));

        Vector direction = env.getLight().getDirection();
        DecimalFormat df = new DecimalFormat("####.##");

        JFormattedTextField t = new JFormattedTextField(df);
        t.setValue(direction.getX());
        t.addPropertyChangeListener("value", (PropertyChangeEvent evt) -> {
            Vector d = env.getLight().getDirection();
            env.getLight().setDirection(d.add(((Number) evt.getNewValue()).floatValue() - d.getX(), 0, 0));
        });
        menu.add(new JLabel("x"));
        menu.add(t);

        t = new JFormattedTextField(df);
        t.setValue(direction.getY());
        t.addPropertyChangeListener("value", (PropertyChangeEvent evt) -> {
            Vector d = env.getLight().getDirection();
            env.getLight().setDirection(d.add(0, ((Number) evt.getNewValue()).floatValue() - d.getY(), 0));
        });
        menu.add(new JLabel("y"));
        menu.add(t);

        t = new JFormattedTextField(df);
        t.setValue(direction.getZ());
        t.addPropertyChangeListener("value", (PropertyChangeEvent evt) -> {
            Vector d = env.getLight().getDirection();
            env.getLight().setDirection(d.add(0, 0, ((Number) evt.getNewValue()).floatValue() - d.getZ()));
        });
        menu.add(new JLabel("z"));
        menu.add(t);

        bar.add(menu);
        return bar;
    }

    /**
     * Initializes the GL options
     *
     * @param drawable OpenGL API @drawable object.
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

        env.getEditor().getDrawings().forEach(d -> {
            d.draw(gl);
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
}
