package br.ufscar.cgm;

import br.ufscar.cgm.*;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.FPSAnimator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.nio.IntBuffer;



/**
 * Classe onde os métodos da OpenGL são implementados por meio da biblioteca JOGL e onde se encontram 
 * os algoritmos para desenhar o segmento. <br><br>
 *
 * Autores:<br>
 * Breno da Silveira Souza RA: 551481<br>
 * Camilo Moreira RA: 359645<br>
 * João Paulo Soares RA: 408034
 * 
 * @author João Paulo
 * @author Breno Silveira
 * @author Camilo Moreira
 */
public class Segmento implements GLEventListener {

    private ClickListener clicks = new ClickListener();
    
    /**
     * Método que inicia a execução.
     * 
     * @param args 
     */

    public static void main(String[] args) {
        
        Racional r = new Racional((int)(Math.random()*100)%10, (int)(Math.random()*100)%10, (int)(Math.random()*100)%10+1);
        Racional rr = new Racional((int)(Math.random()*100)%10, (int)(Math.random()*100)%10, (int)(Math.random()*100)%10+1);
        System.out.println("Soma");
        r.soma(rr);
        System.out.println("Subtração");
        r.subtrai(rr);
        System.out.println("Muliplicação");
        r.multiplica(rr);
        System.out.println("Divisão");
        r.divide(rr);
        
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
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {

        final GL gl = drawable.getGL();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) {

            height = 1;
        }
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -1, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    /**
     * Nesse método, os valores de x e y na posição do clique são recuperados, 
     * e o método drawLine é chamado em um loop passando como parâmetros os valores
     * de x e y de dois em dois pontos.
     * @param drawable 
     */
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

        for (int i = 0; i < clicks.getNumeroCliques() - 1; i += 1) {
            int a[] = clicks.getClick(i);
            int b[] = clicks.getClick(i + 1);
            drawLine(gl, a[0], a[1], b[0], b[1]);
        }
        
        if(clicks.isLastClickDifferent()){
            int a[] = clicks.getClick(0);
            int b[] = clicks.getClick(clicks.getNumeroCliques()-1);
            drawLine(gl, a[0], a[1], b[0], b[1]);
        }

    }
    
    /**
    * Método drawLine realiza a conversão matricial do segmento de reta, além
    * de ajustar os pontos para que o algoritmo funcione em um ângulo que não
    * pertença ao primeiro octante.<br><br>
    * 
    * A primeira parte irá corrigir o sistema de coordernadas entre o clique do 
    * mouse e a ViewPort. É obtido o tamanho da ViewPort e os pontos
    * são refletidos em relação ao eixo das abcissas (meio da tela).
    * As coordenadas do eixo y foram ajustadas atribuindo aos valores de y0 e y1 o valor da altura
    * da viewport subtraido pelo valor do y obtido a partir do clique. Isso se deve ao fato de que 
    * a posicao (0,0) para o mouse se localiza no canto superior esquerdo, enquanto que na viewport se
    * localiza no canto inferior esquerdo.<br><br>
    * 
    * Uma variavel lógica, dx_menor_dy, foi criada para servir como condição sobre a inclinação
    * da reta estiver entre 0 e 45, ou 46 e 90 graus. Se a reta estiver entre 45 e 90, o dx será 
    * menor que o dy e os pontos precisam ser refletidos na reta y = x<br><br>
    * 
    * A próxima parte do algoritmo verifica se x0 é menor que x1. Isso se 
    * deve ao fato de que a reta sempre eh desenhada da esquerda para a direita. Se o valor de x0 e x1 não estão
    * em ordem,  trocamos os pontos (x0,y0) e (x1,y1) de lugar para que a reta seja desenhada da esquerda para a
    * direita. <br><br>
    * 
    * Após isso, é verificado o sinal de dy. Se dy eh negativo, a reta é decrescente. Esse teste
    * serve para guiar na execução do algoritmo da reta. Se dy for negativo, ao invés de incrementar durante a
    * execução, deve decrementar, para que a reta seja desenhada corretamente.
    * 
    * @param gl objeto GL que sera desenhado
    * @param x0 valor do x do primeiro clique
    * @param y0 valor do y do primeiro clique
    * @param x1 valor do x do segundo clique
    * @param y1 valor do y do segundo clique
    */
    public void drawLine(GL gl, int x0, int y0, int x1, int y1) {

        
        IntBuffer buffer = BufferUtil.newIntBuffer(4);
        
        gl.glGetIntegerv(GL.GL_VIEWPORT, buffer);
    
        int height = buffer.get(3);

        y0 = height - y0;
        y1 = height - y1;

        int dx = 0, dy = 0, incE, incNE, d, x, y;
        // Condição para saber se a inclinação da reta
        // está entre 0 e 45 ou entre 46 e 90 graus.
        boolean dx_menor_dy = false;
        // Usado para saber se a reta é 
        // crescente ou decrescente
        int dy_signal = 1;

        dx = x1 - x0;
        dy = y1 - y0;

        // Se dy > dx, então a reta está acima de 45 graus até 90 graus
        // Neste caso, vamos mudar as variáveis com uma reflexão a reta y = x
        // para usar o algoritmo entre 0 e 45 graus.
        // A reflexão é feita trocando x_i por y_i e vice versa, onde i = 0 e 1
        if (Math.abs(dx) < Math.abs(dy)) {
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

        // A reta sempre é desenhada da esquerda para a direita
        // Se o valor de x0 e x1 nao estao em ordem
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

        // Se dy é negativo, a reta é decrescente.
        // Mudamos o valor de dy para ser desenhado entre 0 e 45 graus
        // Mas ao invés de incrementar, y é decrementado (por dy_signal)
        if (dy < 0) {
            dy_signal = -1;
            dy = -dy;
        }

        //Cálculo das variáveis do algoritmo
        d = 2 * dy - dx;
        incE = 2 * dy;
        incNE = 2 * (dy - dx);

        x = x0;
        y = y0;

        // O primeiro ponto é desenhado no começo do loop
        // O loop para quando desenha o último ponto (x1,y1)
        while (x <= x1) {

            // Se o angulo esta entre 0 e 45 graus, desenha normalmente
            // Se esta entre 45 e 90, desenhar o ponto na reflexao
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

    /**
     * Método que desenha um ponto na coordenada (x,y) na tela
     * @param gl objeto a ser desenhado
     * @param x coordenada x do ponto a ser desenhado
     * @param y coordenada y do ponto a ser desenhado
     */
    public void write_pixel(GL gl, int x, int y) {
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(x, y);
        gl.glEnd();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {

    }
}

