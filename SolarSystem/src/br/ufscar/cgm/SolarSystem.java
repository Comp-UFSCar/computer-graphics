package br.ufscar.cgm;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JFrame;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * Nesse trabalho, foi implementada uma animação que representa o sistema solar (considerando o sol e os quatro
 * primeiros planetas) simulando os movimentos de rotação e translação. Além disso, as luas da Terra e de Marte
 * foram modeladas. Os planetas, o sol e a lua foram modelados a partir de uma esfera do jogl, aplicando textura
 * ao seu contorno por meio de imagens png. Foi adicionado também luz para a cena. A animação foi realizada
 * a partir de transformações de rotação e translação. Para esse trabalho, foi usado como referência o tutorial que
 * pode ser encontrado em:
 * {@link} http://www.land-of-kain.de/docs/jogl/
 * 
 * @author João Paulo RA:408034
 * @author Breno Silveira RA:551481
 * @author Camilo Moreira RA:359645
 * 
 */
public class SolarSystem extends GLCanvas implements GLEventListener {

    
    private GLU glu;

    /** Quantidade de frames por segundo para animar. */
    private int fps = 60;

    /** Animador da OpenGL */
    private FPSAnimator animator;

    /** Texturas. */
    private Texture sunTexture;
    private Texture mercuryTexture;
    private Texture venusTexture;
    private Texture earthTexture;
    private Texture marsTexture;
    private Texture moonTexture;
    private Texture starsTexture;
    private Texture solarPanelTexture;
    
    /** Ângulos de cada objeto da animação, que irão variar em diferentes escalas */
    private float galaxyAngle = 0;
    private float sunAngle = 0;
    private float mercuryAngle = 0;
    private float venusAngle = 0;
    private float earthAngle = 0;
    private float marsAngle = 0;
    private float mercuryPlanetAngle = 0;
    private float venusPlanetAngle = 0;
    private float earthPlanetAngle = 0;
    private float marsPlanetAngle = 0;
    private float moonAngle = 0;
   
    
    
     /**
     * 
     * 
     * @param args Command line args.
     */
    public final static void main(String[] args) {
        GLCapabilities capabilities = createGLCapabilities();
        SolarSystem canvas = new SolarSystem(capabilities, 800, 500);
        JFrame frame = new JFrame("Trabalho 4 CGM");
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        canvas.requestFocus();
    }

    /**
     * Capabilities definem o que será usado da implementação OpenGL mais baixo nível. Se a máquina não puder
     * suportar, uma excessão será lançada, e será possível saber o que funcionará e o que não.
     * 
     * @param capabilities capabilities da GL.
     * @param width largura da janela.
     * @param height altura da janela.
     */
    public SolarSystem(GLCapabilities capabilities, int width, int height) {
        addGLEventListener(this);
    }

    /**
     * @return algumas capabilities padrão.
     */
    private static GLCapabilities createGLCapabilities() {
        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);
        return capabilities;
    }

    /**
     *
     */
    public void init(GLAutoDrawable drawable) {
        drawable.setGL(new DebugGL(drawable.getGL()));
        final GL gl = drawable.getGL();
 
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);

        gl.glShadeModel(GL.GL_SMOOTH);

        gl.glClearColor(0f, 0f, 0f, 0f);

        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

        glu = new GLU();

        //Carregando as texturas
        try {
            InputStream stream = getClass().getResourceAsStream("textures/sun.png");
            TextureData data = TextureIO.newTextureData(stream, false, "png");
            sunTexture = TextureIO.newTexture(data);
            stream = getClass().getResourceAsStream("textures/mercury.png");
            data = TextureIO.newTextureData(stream, false, "png");
            mercuryTexture = TextureIO.newTexture(data);
            stream = getClass().getResourceAsStream("textures/neptune.png");
            data = TextureIO.newTextureData(stream, false, "png");
            venusTexture = TextureIO.newTexture(data);
            stream = getClass().getResourceAsStream("textures/earth2.png");
            data = TextureIO.newTextureData(stream, false, "png");
            earthTexture = TextureIO.newTexture(data);
            stream = getClass().getResourceAsStream("textures/mars.png");
            data = TextureIO.newTextureData(stream, false, "png");
            marsTexture = TextureIO.newTexture(data);
            stream = getClass().getResourceAsStream("textures/stars2.png");
            data = TextureIO.newTextureData(stream, false, "png");
            starsTexture = TextureIO.newTexture(data);
            stream = getClass().getResourceAsStream("textures/moon.png");
            data = TextureIO.newTextureData(stream, false, "png");
            moonTexture = TextureIO.newTexture(data);
        }
        catch (IOException exc) {
            System.out.print("Arquivos de Textura nÃ£o foram encontrados.");
            System.exit(1);
        }

        try {
            InputStream stream = getClass().getResourceAsStream("textures/stars2.png");
            TextureData data = TextureIO.newTextureData(stream, false, "png");
            solarPanelTexture = TextureIO.newTexture(data);
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(2);
        }
        animator = new FPSAnimator(this, fps);
        animator.start();
    }

    /**
     * Nesse método, os objetos são modelados a partir do objeto quadric que é adicionado em esferas e a 
     * textura é adicionada a partir das texturas que foram carregadas anteriormente. Para animação da cena, 
     * uma rotação e uma translação são definidas para simular o movimento de translação dos planetas em torno do
     * sol. Como o tempo que cada planeta leva para realizar isso é diferente, os ângulos para cada planeta variam em 
     * valores diferentes, dando a impressão de que eles possuem velocidades distintas. Através de uma rotação, 
     * o movimento de rotação também é definido para cada planeta.
     * As luas não possuem movimento de rotação associado, apenas movimento de translação, que é relativo ao sol e ao
     * planeta que elas circundam (Terra e Marte).
     */
    public void display(GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }
        final GL gl = drawable.getGL();

        // limpando a tela
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Mudando para matrix de projeção
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        // Setando a perspectiva
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(200, 200, 200, 
                0, 0, 0, 
                0, 1, 0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Definindo os parâmetros da luz, a posição da luz da luz que será especular
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {-30, 0, 0, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // setando os parâmetros de luz definidos anteriormente
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, lightColorSpecular, 0);

        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_LIGHTING);

        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
        
        
        //rotacionando a galáxia um pouco
        galaxyAngle++;
        gl.glRotated(galaxyAngle,0,1,0);
        gl.glPushMatrix();
        float radius;
        int slices;
        int stacks;
        
        
        // Criando o sol, a partir da classe GLUquadric
        sunTexture.bind();
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricTexture(quadric, true); //setando o tipo de textura
        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL); //setando o estilo de renderização dessa quadric
        glu.gluQuadricNormals(quadric, GLU.GLU_FLAT);   //setando a forma de cálculo das normais
        glu.gluQuadricOrientation(quadric, GLU.GLU_INSIDE);  //setando a orientação da normal para dentro
        radius = 20f;
        slices = 10;
        stacks = 10;
        sunAngle += 0.1;
        gl.glRotated(-galaxyAngle,0,1,0);
        gl.glRotated(sunAngle,0,1,0); //rotacionando o sol em torno de si mesmo
        glu.gluSphere(quadric, radius, slices, stacks); //desenhando a esfera a partir do quadric criado
        
        //Modelando o planera mercúrio
        mercuryTexture.bind();
        glu.gluQuadricOrientation(quadric, GLU.GLU_OUTSIDE);   //setando a orientação da normal para fora (ao contráio do sol)
        radius = 3.4f;
        slices = 16;
        stacks = 16;
        gl.glPopMatrix();
        gl.glPushMatrix();
        mercuryPlanetAngle += 0.74;
        mercuryAngle += Math.E/10;
        gl.glRotated(mercuryPlanetAngle,0,1,0);  //a partir de uma rotação e uma translação, definindo o ângulo de rotação em volta do ol
        gl.glTranslated(20, 0, 20);  //transladando mercúrio em torno do ponto que se localiza o sol
        gl.glRotated(mercuryAngle,0,1,0); //rotacionando mercúrio em torno de si mesmo
        glu.gluSphere(quadric, radius, slices, stacks);
        
        //Modelando o planeta Venus, e aplicando a animação à ele
        venusTexture.bind();
        radius = 8.6f;
        slices = 16;
        stacks = 16;
        gl.glPopMatrix();
        gl.glPushMatrix();
        venusPlanetAngle += 0.54;
        venusAngle += -1.0/10;
        gl.glRotated(venusPlanetAngle,0,1,0);
        gl.glTranslated(35, 0, 40);
        gl.glRotated(venusAngle,0,1,0);
        glu.gluSphere(quadric, radius, slices, stacks);
        
        // Modelando o planeta Terra e a lua ao redor dele
        earthTexture.bind();
        radius = 9.1f;
        slices = 16;
        stacks = 16;
        gl.glPopMatrix();
        gl.glPushMatrix();
        earthPlanetAngle += 0.33;
        earthAngle++;
        gl.glRotated(earthPlanetAngle,0,1,0);
        gl.glTranslated(60, 0, 60);
        gl.glRotated(earthAngle,0,1,0);
        glu.gluSphere(quadric, radius, slices, stacks);
        
        moonTexture.bind();
        radius = 2.0f;
        slices = 16;
        stacks = 16;
        moonAngle += 1;
        gl.glRotated(moonAngle,0.1,0.5,-0.5);  //lua tem apenas movimento de translação
        gl.glTranslated(10, 0, 10);
        glu.gluSphere(quadric, radius, slices, stacks);

        //Modelando Marte
        marsTexture.bind();
        radius = 4.8f;
        slices = 16;
        stacks = 16;
        gl.glPopMatrix();
        gl.glPushMatrix();
        marsPlanetAngle += Math.sqrt(2)/10;
        marsAngle += Math.sqrt(7)/10;
        gl.glRotated(marsPlanetAngle,0,1,0);
        gl.glTranslated(80, 0, 80);
        gl.glRotated(marsAngle,0,1,0);
        glu.gluSphere(quadric, radius, slices, stacks);
        
        //Modelando as luas de Marte
        
        moonTexture.bind();
        radius = 1.4f;
        slices = 9;
        stacks = 9;
        gl.glPushMatrix();
        gl.glRotated(2*moonAngle,1,0,-0.1);
        gl.glTranslated(5, -2, 5);
        glu.gluSphere(quadric, radius, slices, stacks);
        
        radius = 0.9f;
        slices = 7;
        stacks = 7;
        gl.glPopMatrix();
        gl.glRotated(3*moonAngle,1,-1,-1);
        gl.glTranslated(7, 0, 7);
        glu.gluSphere(quadric, radius, slices, stacks);
        
        //Modelando o fundo do universo. com algumas estrelas e com uma velocidade de rotação bem pequena
        starsTexture.bind();
        glu.gluQuadricOrientation(quadric, GLU.GLU_INSIDE);
        radius = 500f;
        slices = 10;
        stacks = 10;
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotated(-galaxyAngle,0,1,0);
        gl.glRotated(-0.01*galaxyAngle,0,1,0);
        glu.gluSphere(quadric, radius, slices, stacks);
        glu.gluDeleteQuadric(quadric);

        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0f);

        // Restore old state.
        gl.glPopMatrix();
    }

    /**
     *
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL gl = drawable.getGL();
        gl.glViewport(0, 0, width, height);
    }

    /**
     * 
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

}