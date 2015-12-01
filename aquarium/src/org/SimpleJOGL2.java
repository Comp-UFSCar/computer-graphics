package org.yourorghere;

import com.obj.Face;
import com.obj.Group;
import com.obj.Material;
import com.obj.Vertex;
import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import com.obj.WavefrontObject;


/**
 * SimpleJOGL2.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class SimpleJOGL2 implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new SimpleJOGL2());
        frame.add(canvas);
        frame.setSize(640, 480);
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
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

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
       
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        float ambient[] = {.3f, .3f, .3f, 1};
        float diffuse[] = {1, 1, 1, 1};
        float specular[] = {1, 1, 1, 1};
        float position[] = {5.0f, 5.0f, 2.0f, 0.0f};
        float lmodel_ambient[] = {0.4f, 0.4f, 0.4f, 1.0f};
        float local_view[] = {0.0f};

        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0);
        gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient, 0);
        gl.glLightModelfv(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, local_view, 0);
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);

        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        
    }

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
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        // Move the "drawing cursor" around
        gl.glTranslatef(-1.5f, 0.0f, -12.0f);
        
        
        WavefrontObject wavefrontObject= new WavefrontObject("seahorse.obj");
        Material mat = new Material("base");
        mat.setKa(new Vertex(.6f, .6f, 1));
        mat.setKd(new Vertex(.6f, .6f, 1));
        mat.setKs(new Vertex(.2f, .2f, .2f));
        mat.setShininess(.3f);
        setMaterial(mat, gl);
        
        angle += 0.5f;
        gl.glRotated(angle, 0, 1, 0);
        
        final int begin = 0;
        final int skip = 0;
        int count = 0;
        
        for (Group g : wavefrontObject.getGroups()) {
            for(Face f : g.getFaces()) {
                if (count < skip) {
                    count++;
                    continue;
                }
                drawFace(f, gl);
            }
        }
                
        gl.glFlush();
    }
    
    private float angle = 0;
    
    private void drawFace(Face f, GL gl) {
        gl.glBegin(GL.GL_TRIANGLES);
        for(Vertex v : f.getVertices()) {
            gl.glVertex3d(v.getX(), v.getY(), v.getZ());
        }
        for(Vertex v : f.getNormals()) {
            gl.glVertex3d(v.getX(), v.getY(), v.getZ());
        }
        gl.glEnd();
    }
    
    private void setMaterial(Material mat, GL gl) {
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, new float[] {mat.getKa().getX(), mat.getKa().getY(), mat.getKa().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, new float[] {mat.getKd().getX(), mat.getKd().getY(), mat.getKd().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, new float[] {mat.getKs().getX(), mat.getKs().getY(), mat.getKs().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, new float[] {mat.getShininess(), 0, 0, 0}, 0);
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

