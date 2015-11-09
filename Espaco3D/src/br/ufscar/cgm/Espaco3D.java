package br.ufscar.cgm;

import br.ufscar.cgm.geometria.Aresta3D;
import br.ufscar.cgm.geometria.Face;
import br.ufscar.cgm.preenchimento.ET;
import br.ufscar.cgm.preenchimento.No;
import br.ufscar.cgm.utils.Drawer;
import br.ufscar.cgm.utils.Racional;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    
    ArrayList<Face> faces;
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
        faces = new ArrayList<Face>();
        ArrayList<Face> novasFaces = new ArrayList<Face>();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        
        novasFaces = Drawer.drawCube(gl, 2, 0, 0, 0);
        faces.addAll(novasFaces);        
        
        gl.glColor3f(1.0f, 0f, 0f);
        //glut.glutWireCube(1.0f);

        // Flush all drawing operations to the graphics card
        preencheEspaco3D(gl);
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    
    }
    
    public void preencheEspaco3D(GL gl)
    {
        //Nada para preencher
        if(faces == null || faces.size() == 0)
            return;
        
        No novoNo;
        for(Face f: faces){
            tabelaET = new ET();
            for (int i = 0; i < f.arestas.size(); i++) {
                Aresta3D s = f.arestas.get(i);

                //se a linha não esta desenhada na horizontal
                if(s.inicio.z != s.fim.z)
                {
                    novoNo = new No(s);
                    tabelaET.adicionaNo(novoNo, Math.min(s.inicio.z, s.fim.z));
                }
            }
            preencheFace(gl);
        }
        
        
    }
    
    public void preencheFace(GL gl){
        No AET = null;
        
        // TODO implementar min e max de Faces
        int nivel = -Drawer.precision*10;
        int nivel_max = Drawer.precision*10;
        
        //Inicializa AET
        while(tabelaET.isNivelVazio(nivel) && nivel < nivel_max)
                nivel++;
        //ET está vazia
        if(nivel == nivel_max)
            return;
        
        boolean AET_esta_vazia = false;
        No p1, p2;
        while(!AET_esta_vazia && nivel < nivel_max){
            //AET recebe os nós que ymin = nivel
            if(AET == null)
                AET = tabelaET.getNivel(nivel);
            else
                AET.setUltimoProximo(tabelaET.getNivel(nivel));
            
            if(AET!=null){
                //System.out.println(nivel + "\n" + AET.toFullString());
            }
            
            //Remove os nós que ymax = nivel
            //Remove os pontos de ymax no começo da AET
            while(AET != null && AET.getZmax() == nivel){
                AET = AET.getProximo();
            }
            if(AET == null){
                AET_esta_vazia = true;
                continue;
            }
            //Remove os pontos de ymax no meio da AET
            p1 = AET;
            p2 = AET.getProximo();
            while(p2 != null){
                if(p2.getZmax() == nivel){
                    p1.setProximo(p2.getProximo());
                    p2 = p1.getProximo();
                }
                else
                {
                    p1 = p1.getProximo();
                    p2 = p1.getProximo();
                }
            }
            
            //ordena AET
            AET = No.ordena(AET);
            
            //preenche figura
            p1 = AET;
            int x1, x2, y1, y2;
            while(p1 != null){
                //Caso especial
                x1 = p1.getXdoMin().arredondaParaCima();
                y1 = p1.getYdoMin().arredondaParaCima();
                x2 = p1.getProximo().getXdoMin().arredondaParaBaixo();
                y2 = p1.getProximo().getYdoMin().arredondaParaBaixo();
                Drawer.original_size = true;
                if(x1 > x2 && y1 > y2)
                    Drawer.drawLine3D(gl, x1, y1, nivel, x1, y1, nivel);
                else
                    Drawer.drawLine3D(gl, x1, y1, nivel, x2, y2, nivel);
                Drawer.original_size = false;
                
                p1 = p1.getProximo().getProximo();
            }
            
            //Atualiza o nível
            nivel++;
            
            //Atualiza o valor dos Nós
            p1 = AET;
            while(p1 != null){
                p1.setXdoMin(p1.getXdoMin().soma(p1.getDxDz()));
                p1.setYdoMin(p1.getYdoMin().soma(p1.getDyDz()));
                p1 = p1.getProximo();
            }
                
        }
    }

}

