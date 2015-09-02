package br.ufscar.cg;

import br.ufscar.cg.ClickListener;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.FPSAnimator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.glu.GLU;

public class Segmento extends GLJPanel implements GLEventListener {

    private ClickListener clicks = new ClickListener();

    public static void main(String[] args) {
        Frame frame = new Frame("Primeiro Trebalho CG");
        GLCanvas canvas = new GLCanvas();

        Segmento s = new Segmento();

        canvas.addGLEventListener(s);
        canvas.addMouseListener(s.clicks);

        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new FPSAnimator(canvas, 60);
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

        final GL gl = drawable.getGL();
        //System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        //gl.setSwapInterval(1);
        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        //gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -1, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        gl.glBegin(GL.GL_LINE);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glVertex2i(10, 10);
        gl.glVertex2i(200, 200);
        gl.glEnd();

        for (int i = 0; i < clicks.size() - 1; i += 1) {
            int a[] = clicks.getClick(i);
            int b[] = clicks.getClick(i + 1);
            drawLine(gl, a[0], a[1], b[0], b[1]);
        }
        
        if(clicks.isLastClickDifferent()){
            int a[] = clicks.getClick(0);
            int b[] = clicks.getClick(clicks.size()-1);
            drawLine(gl, a[0], a[1], b[0], b[1]);
        }

    }

    void drawLine(GL gl, int x0, int y0, int x1, int y1) {

        /*
         A primeira parte ir� corrigir os sistemas de coordernadas
         entre o clique do mouse e a ViewPort.
         � obtido o tamanho da ViewPort e os pontos
         s�o refletidos em rela��o ao eixo das abcissas
         */
        // Cria um IntBuffer para 4 inteiros
        IntBuffer buffer = BufferUtil.newIntBuffer(4);
        //Obt�m as coordenadas da ViewPort (x0,y0,width,height)
        gl.glGetIntegerv(GL.GL_VIEWPORT, buffer);
        //Altura recebe o quarto elemento
        int height = buffer.get(3);

        y0 = height - y0;
        y1 = height - y1;

        int dx = 0, dy = 0, incE, incNE, d, x, y;
        // Condi��o para saber se a inclina��o da reta
        // est� entre 0 e 45 ou entre 46 e 90 graus.
        boolean dx_menor_dy = false;
        // Usado para saber se a reta � 
        // crescente ou decrescente
        int dy_signal = 1;

        dx = x1 - x0;
        dy = y1 - y0;

        // Se dy > dx, ent�o a reta est� acima de 45 graus at� 90 graus
        // Neste caso, vamos mudar as vari�veis com uma reflex�o a reta y = x
        // para usar o algoritmo entre 0 e 45 graus.
        // A reflex�o � feita trocando x_i por y_i e vice versa, onde i = 0 e 1
        if (Math.abs(dx) < Math.abs(dy)) {
            //condi��o para desenhar diferente
            dx_menor_dy = true;

            // Troca dx e dy
            int aux = dx;
            dx = dy;
            dy = aux;

            // Troca x0 e y0
            aux = x0;
            x0 = y0;
            y0 = aux;

            // Troca x1 e y1
            aux = x1;
            x1 = y1;
            y1 = aux;
        }

        // A reta sempre � desenhada da esquerda para a direita
        // Se o valor de x0 e x1 n�o est�o em ordem
        // Trocamos os pontos (x0,y0) e (x1,y1) de lugar para que
        // sejam desenhados da esquerda para a direita
        if (x1 < x0) {
            int aux = x1;
            x1 = x0;
            x0 = aux;

            aux = y1;
            y1 = y0;
            y0 = aux;

            dx = -dx;
            dy = -dy;
        }

        // Se dy � negativo, a reta � decrescente.
        // Mudamos o valor de dy para ser desenhado entre 0 e 45 graus
        // Mas ao inv�s de incrementar, y � decrementado (por dy_signal)
        if (dy < 0) {
            dy_signal = -1;
            dy = -dy;
        }

        //C�lculo das vari�veis do algoritmo
        d = 2 * dy - dx;
        incE = 2 * dy;
        incNE = 2 * (dy - dx);

        x = x0;
        y = y0;

        // O primeiro ponto � desenhado no come�o do loop
        // O loop para quando desenha o �ltimo ponto (x1,y1)
        while (x <= x1) {

            // Se o �ngulo est� entre 0 e 45 graus, desenha normalmente
            // Se est� entre 45 e 90, desenhar o ponto na reflex�o
            if (!dx_menor_dy) {
                write_pixel(gl, x, y);
            } else {
                write_pixel(gl, y, x);
            }

            x++;
            if (d <= 0) {
                d = d + incE;
            } else {
                d = d + incNE;
                y += dy_signal;
            }

        }

    }

    private void write_pixel(GL gl, int x, int y) {
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(x, y);
        gl.glEnd();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {

    }

}
