package br.ufscar.cgm;

import br.ufscar.cgm.geometria.Aresta3D;
import br.ufscar.cgm.geometria.Face;
import br.ufscar.cgm.geometria.Ponto3D;
import br.ufscar.cgm.preenchimento.ET;
import br.ufscar.cgm.preenchimento.No;
import br.ufscar.cgm.utils.Drawer;
import br.ufscar.cgm.utils.Racional;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.BufferUtil;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * Classe principal, contando com as incializa��es e as chamadas dos m�todos auxiliares em outras classes.
 * 
 * @author Jo�o Paulo RA:408034
 * @author Breno Silveira RA:551481
 * @author Camilo Moreira RA:359645
 */
public class Espaco3D implements GLEventListener {

    Ponto3D posicaoFoco;
    Ponto3D posicaoCamera;
    Ponto3D vetorDirecaoDaCamera;
    final double distanciaCamera = -3;

    Ponto3D vetorDirecaoDaLuz = new Ponto3D(0, 0, 1);
    float intensidadeLuz = 0.7f;
    float intensidadeLuzAmbiente = 0.7f;
    float ka = 0.7f;
    float kd = 0.7f;

    ArrayList<Face> faces;
    ET tabelaET;
    No AET;

    int[][] buffer;

    public static void main(String[] args) {
        Espaco3D espaco = new Espaco3D();
        Scanner keyboard = new Scanner(System.in);
        keyboard.useLocale(Locale.FRENCH);
        Boolean ready = false;
        /*while(true){
            try{
                System.out.print("Digite a coordenada X inteira da direcao da luz: ");
                espaco.vetorDirecaoDaLuz.x = keyboard.nextInt();
                System.out.print("Digite a coordenada Y inteira da direcao da luz: ");
                espaco.vetorDirecaoDaLuz.y = keyboard.nextInt();
                System.out.print("Digite a coordenada Z inteira da direcao da luz: ");
                espaco.vetorDirecaoDaLuz.z = keyboard.nextInt();
                if(espaco.vetorDirecaoDaLuz.x == 0 &&
                   espaco.vetorDirecaoDaLuz.y == 0 &&
                   espaco.vetorDirecaoDaLuz.z == 0){
                    System.out.println("Vetor direção não pode ser nulo. Comece de novo.");
                    continue;
                }else 
                    break;
            } catch(Exception e){
                
                System.out.println("Digite o vetor novamente.");
                keyboard.next();
                continue;       
            }
        }
        System.out.println("Utilize virgula para separar casas decimais.");
        while(espaco.intensidadeLuz < 0 || espaco.intensidadeLuz > 1 )
            try {
                System.out.print("Digite um valor entre 0 e 1 da intensidade da luz distante: ");
                espaco.intensidadeLuz = (float) keyboard.nextDouble();
            } catch(Exception e){      
                keyboard.next();
                continue;       
            }
        while(espaco.intensidadeLuzAmbiente < 0 || espaco.intensidadeLuzAmbiente > 1 )
            try {
                System.out.print("Digite um valor entre 0 e 1 da intensidade da luz ambiente: ");
                espaco.intensidadeLuzAmbiente = (float) keyboard.nextDouble();
            } catch(Exception e){      
                keyboard.next();
                continue;       
            }
        while(espaco.kd < 0 || espaco.kd > 1 )
            try {
                System.out.print("Digite um valor entre 0 e 1 da constante de reflexão da luz difusa: ");
                espaco.kd = (float) keyboard.nextDouble();
            } catch(Exception e){      
                keyboard.next();
                continue;       
            }
        while(espaco.ka < 0 || espaco.ka > 1 )
            try {
                System.out.print("Digite um valor entre 0 e 1 da constante de reflexão da luz ambiente: ");
                espaco.ka = (float) keyboard.nextDouble();
            } catch(Exception e){      
                keyboard.next();
                continue;       
            }*/
        
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(espaco);
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

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        gl.setSwapInterval(1);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        //gl.glShadeModel(GL.GL_FLAT); 
    }

    /**
     * No m�todo reshape o vetor com a posi��o da camera, do foco de luz e da dire��o da c�mera s�o definidos.
     * @param drawable
     * @param x
     * @param y
     * @param width
     * @param height 
     */
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

        posicaoCamera = new Ponto3D(0, 0, distanciaCamera);
        posicaoFoco = new Ponto3D(0, 0, 0);
        vetorDirecaoDaCamera = new Ponto3D(0, 1, 0);
    }

    /**
     * No m�todo display, � chamado o m�todo drawcube da classe Drawer. Logo ap�s, as faces que foram definidas
     * por esse m�todo s�o ordenadas atrav�s do m�todo ordenaFaces e logo ap�s s�o preenchidas, de forma que a
     * face mais ao fundo � desenhada primeiro.
     * @param drawable 
     */
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        faces = new ArrayList<Face>();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();

        Drawer.drawCube(gl, faces, 0.5, 0, 0, 0);

        //Drawer.drawLine3D(gl, 0, 0, 0, 2, 0, 0);
        //gl.glColor3f(1.0f, 0f, 0f);
        //glut.glutWireCube(1.0f);
        // Flush all drawing operations to the graphics card
        ordenaFaces();
        preencheEspaco3D(gl);
        
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {

    }

    /**
     * M�todo de preenchimento. Lembrando que o cubo cont�m 6 faces, ent�o, cada uma delas � necessita ter sua
     * tabela ET incializada, e, logo ap�s, ter o algoritmo aplicado. Cada ponto da face � projetado atrav�s do
     * m�todo projetaPonto. O ponto resultante, que consistir� de coordenadas x ey, com z igual a 0, deve ser 
     * convertido, pois no espa�o 3D, os pontos s�o considerados indo de -1 a 1 no plano cartesiano, enquanto que 
     * no espa�o 2D, considera-se os valores dos pixeis da tela. Isso � feito atrav�s do m�todo normaliza, e para 
     * cada aresta em cada face do cubo, a tabela ET � inicializada. Depois de feita a inicializa��o da ET a intensidade
     * da cor � calculada, lembrando que essa intensidade considera as informa��es que foram lidas do usu�rio e � calculada
     * de acordo com a normal da face do cubo. Ap�s isso, as faces s�o preenchidas (considerando a intensidade).
     * @param gl 
     */
    public void preencheEspaco3D(GL gl) {
        //Nada para preencher
        if (faces == null || faces.size() == 0) {
            return;
        }
        
        IntBuffer buffer = BufferUtil.newIntBuffer(4);
        gl.glGetIntegerv(GL.GL_VIEWPORT, buffer);
        int width = buffer.get(2);
        int height = buffer.get(3);

        No novoNo;
        for (Face f : faces) {
            tabelaET = new ET(height);
            novoNo = null;
            
            for (Aresta3D a : f.arestas) {
                Ponto3D inicio = Ponto3D.projetaPonto(a.inicio, posicaoCamera);
                Ponto3D fim = Ponto3D.projetaPonto(a.fim, posicaoCamera);
                int inicio_x = normaliza(inicio.x, width);
                int inicio_y = normaliza(inicio.y, height);
                int fim_x = normaliza(fim.x,width);
                int fim_y = normaliza(fim.y, height);
                //se a linha não esta desenhada na horizontal
                if(inicio_y!=fim_y)
                {
                    if(inicio_y > fim_y)
                    {
                        novoNo = new No(inicio_y,fim_x,new Racional(0,fim_x-inicio_x,fim_y-inicio_y));
                        tabelaET.adicionaNo(novoNo, fim_y);
                    } else {
                        novoNo = new No(fim_y,inicio_x,new Racional(0,fim_x-inicio_x,fim_y-inicio_y));
                        tabelaET.adicionaNo(novoNo, inicio_y);
                    }
                }
            }

            float cor = f.getIntensidade(intensidadeLuzAmbiente, ka,
                    intensidadeLuz, kd, vetorDirecaoDaLuz);
            //System.out.println("Cor = " + cor);
            gl.glColor3f(cor, 0f, 0f);
            preencheFace(gl);
        }

    }
    
    /**
    * Algoritmo de preenchimento de poligono usando tabela de arestas ativas.
    */
    public void preencheFace(GL gl){
        No AET = null;
        
        IntBuffer buffer = BufferUtil.newIntBuffer(4);
        gl.glGetIntegerv(GL.GL_VIEWPORT, buffer);
        int height = buffer.get(3);
        
        int nivel = 0;
        int nivel_max = height;
        
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
            
            //Remove os nós que ymax = nivel
            //Remove os pontos de ymax no começo da AET
            while(AET != null && AET.getYmax() == nivel){
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
                if(p2.getYmax() == nivel){
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
            int x1, x2;
            while(p1 != null){
                //Caso especial
                x1 = p1.getXdoYmin().arredondaParaCima();
                x2 = p1.getProximo().getXdoYmin().arredondaParaBaixo();
                if(x1 > x2)
                    Drawer.drawLine2D(gl, x1, nivel, x1, nivel);
                else
                    Drawer.drawLine2D(gl, x1, nivel, x2, nivel);
                
                p1 = p1.getProximo().getProximo();
            }
            
            //Atualiza o nível
            nivel++;
            
            //Atualiza o valor dos Nós
            p1 = AET;
            while(p1 != null){
                p1.setXdoYmin(p1.getXdoYmin().soma(p1.getDXDY()));
                p1 = p1.getProximo();
            }
                
        }
    }

    /**
     * M�todo de ordena��o das faces, que percorre todas as faces do cubo e determina a maior dist�ncia da face
     * at� a posi��o da c�mere atrav�s do m�todo getMaiorDistanciaAtePonto da classe Face. 
     */
    public void ordenaFaces() {
        ArrayList<Face> facesOrdenadas = new ArrayList<Face>(faces.size());
        int size = 0;
        int i;
        double d, e;
        for (Face f : faces) {
            d = f.getMaiorDistanciaAtePonto(posicaoCamera);
            for (i = 0; i < size; i++) {
                e = facesOrdenadas.get(i)
                        .getMaiorDistanciaAtePonto(posicaoCamera);
                if (!(e >= d)) {
                    break;
                }
            }
            facesOrdenadas.add(i, f);
            size++;
        }

        faces = facesOrdenadas;

    }
    
    /**
     * Considerando que no Espa�o 3D, os pontos s�o definidos variando x e y de -1 a 1, e que no espa�o 2D
     * se considera os valores dos pixeis da janela, o m�todo normaliza tem inten��o de converter do espa�o
     * de coordenadas 3D pro 2D. Desse modo, primeiramente � verificado se o ponto projetado n�o ultrapassa os limites
     * de -1 e 1, se isso ocorrer, ent�o o valor -1 ou 1 (dependendo de que lado o ponto ultrapassa) � atribu�do pra
     * coordenada do ponto.
     * A convers�o ent�o � feita somando base/2 (base se refere a altura ou largura da tela)
     * ao valor da multiplica��o de base/2 pelo valor da coordenada. Isso se deve ao fato de que podem existir valores negativos
     * que seriam convertidos para pixeis com valores de x ou y menores, desse modo, como metade da janela � positiva, esse
     * valor � somado, enquanto que a outra metade � multiplicada.
     * @param var o valor da coordenada x ou y do ponto projetado
     * @param base valor utilizado para converter, refere-se a largura ou altura da tela.
     * @return 
     */
    public int normaliza(double var, int base){
        if(var < -1)
            var = -1;
        if(var > 1)
            var = 1;
        base--;
        return base/2 + (int)(var*base/2);
    }


}
