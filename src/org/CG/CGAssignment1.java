package org.CG;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import org.CG.infrastructure.drawings.Drawing;
import org.CG.infrastructure.drawings.LineInPixelMatrix;

/**
 * CGAssignment1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class CGAssignment1 implements GLEventListener {

    static LinkedList<Drawing> drawings = new LinkedList<>();
    static int count = 0;
    static int[] start;
    static int[] end;

    protected static int randomColor() {
        return (int) (Math.random() * 256);
    }

    public static void main(String[] args) {
        final Frame frame = new Frame("Line drawing from pixel matrix");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new CGAssignment1());
        frame.add(canvas);
        frame.setSize(1366, 768);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (count == 0) {
                    // Get first coordinate. Hold on for second one.
                    start = new int[]{e.getX(), canvas.getHeight() - e.getY()};
                } else {
                    // Just got the second coordenate.
                    end = new int[]{e.getX(), canvas.getHeight() - e.getY()};

                    System.out.println("(" + start[0] + "," + start[1] + ")->(" + end[0] + "," + end[1] + ")");

                    // Draw the line in the pixel matrix using random colors.
                    drawings.add(new LineInPixelMatrix(new int[]{start[0], start[1], end[0], end[1], randomColor(), randomColor(), randomColor()}));
                }

                count = (count + 1) % 2;
            }
        });

        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
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
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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

        drawings.stream().forEach((d) -> {
            d.draw(gl);
        });

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
