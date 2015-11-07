package br.ufscar.cgm;

import br.ufscar.cgm.geometria.Aresta3D;
import br.ufscar.cgm.geometria.Face;
import br.ufscar.cgm.preenchimento.ET;
import br.ufscar.cgm.preenchimento.No;
import br.ufscar.cgm.utils.Drawer;
import br.ufscar.cgm.utils.Racional;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;


/**
 * Espaco3D.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Espaco3D implements GLEventListener {
    
    ArrayList<Face> faces = new ArrayList<Face>();
    ET tabelaET;
    No AET;

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Espaco3D());
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
        glu.gluPerspective(45.0f, h, 2.0, 20.0);
        glu.gluLookAt(5.0, 6.0, 7.0,
                0.0, 0.0, 0.0,
                0.0, 1.0, 0.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        GLUT glut = new GLUT();
        ArrayList<Face> novasFaces = new ArrayList<Face>();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        
        //Drawer.drawLine2D(gl, -1, -1, 0, 0);
        //Drawer.drawCube(gl, 1, 0, 0, 0);
        novasFaces = Drawer.drawCube(gl, 2, 0, 0, 0);
        faces.addAll(novasFaces);
        //Drawer.drawCube(gl, 3, 0, 0, 0);
        
        gl.glColor3f(1.0f, 0f, 0f);
        //glut.glutWireCube(1.0f);

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    
    }
    
    public void inicializaET()
    {
        //Nada para preencher
        if(faces == null || faces.size() == 0)
            return;
        
        tabelaET = new ET(50*Drawer.precision);
             
        No novoNo;
        for (int i = 0; i < faces.get(0).arestas.size(); i++) {
            Aresta3D s = faces.get(0).arestas.get(i);
            
            //se a linha não esta desenhada na horizontal
            if(s.inicio.z != s.fim.z)
            {
                novoNo = new No(s);
                tabelaET.adicionaNo(novoNo, Math.min(s.inicio.z, s.fim.z)*Drawer.precision);
            }
        }
        
        tabelaET.exibe();
    }
}

