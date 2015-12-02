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
 * A minimal JOGL demo.
 * 
 * @author <a href="mailto:kain@land-of-kain.de">Kai Ruhl</a>
 * @since 26 Feb 2009
 */
public class SolarSystem extends GLCanvas implements GLEventListener {

    /** The GL unit (helper class). */
    private GLU glu;

    /** The frames per second setting. */
    private int fps = 60;

    /** The OpenGL animator. */
    private FPSAnimator animator;

    /** Textures. */
    private Texture sunTexture;
    private Texture mercuryTexture;
    private Texture venusTexture;
    private Texture earthTexture;
    private Texture marsTexture;
    private Texture starsTexture;
    
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
    
    /** The angle of the satellite orbit (0..359). */
    private float satelliteAngle = 0;

    /** The texture for a solar panel. */
    private Texture solarPanelTexture;
    
        /**
     * Starts the JOGL mini demo.
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
     * A new mini starter.
     * 
     * @param capabilities The GL capabilities.
     * @param width The window width.
     * @param height The window height.
     */
    public SolarSystem(GLCapabilities capabilities, int width, int height) {
        addGLEventListener(this);
    }

    /**
     * @return Some standard GL capabilities (with alpha).
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
     * Sets up the screen.
     * 
     * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
     */
    public void init(GLAutoDrawable drawable) {
        drawable.setGL(new DebugGL(drawable.getGL()));
        final GL gl = drawable.getGL();

        // Enable z- (depth) buffer for hidden surface removal. 
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);

        // Enable smooth shading.
        gl.glShadeModel(GL.GL_SMOOTH);

        // Define "clear" color.
        gl.glClearColor(0f, 0f, 0f, 0f);

        // We want a nice perspective.
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

        // Create GLU.
        glu = new GLU();

        // Load textures.
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
        }
        catch (IOException exc) {
            System.out.print("Arquivos de Textura não foram encontrados.");
            System.exit(1);
        }

        // Load the solar panel texture.
        try {
            InputStream stream = getClass().getResourceAsStream("textures/stars2.png");
            TextureData data = TextureIO.newTextureData(stream, false, "png");
            solarPanelTexture = TextureIO.newTexture(data);
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(2);
        }

        // Start animator.
        animator = new FPSAnimator(this, fps);
        animator.start();
    }

    /**
     * The only method that you should implement by yourself.
     * 
     * @see javax.media.opengl.GLEventListener#display(javax.media.opengl.GLAutoDrawable)
     */
    public void display(GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }
        final GL gl = drawable.getGL();

        // Clear screen.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Set camera.
        // Change to projection matrix.
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        // Perspective.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(200, 200, 200, 
                0, 0, 0, 
                0, 1, 0);

        // Change back to model view matrix.
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Prepare light parameters.
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {-30, 0, 0, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(GL.GL_LIGHT1);
        gl.glEnable(GL.GL_LIGHTING);

        // Set material properties.
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);
        
        
        galaxyAngle++;
        gl.glRotated(galaxyAngle,0,1,0);
        gl.glPushMatrix();
        float radius;
        int slices;
        int stacks;
        //Sun
        sunTexture.bind();
        GLUquadric sun = glu.gluNewQuadric();
        glu.gluQuadricTexture(sun, true);
        glu.gluQuadricDrawStyle(sun, GLU.GLU_FILL);
        glu.gluQuadricNormals(sun, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(sun, GLU.GLU_OUTSIDE);
        radius = 20f;
        slices = 16;
        stacks = 16;
        sunAngle += 10;
        gl.glRotated(sunAngle,0,1,0);
        glu.gluSphere(sun, radius, slices, stacks);
        glu.gluDeleteQuadric(sun);
        
        //Mercury
        mercuryTexture.bind();
        GLUquadric mercury = glu.gluNewQuadric();
        glu.gluQuadricTexture(mercury, true);
        glu.gluQuadricDrawStyle(mercury, GLU.GLU_FILL);
        glu.gluQuadricNormals(mercury, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(mercury, GLU.GLU_OUTSIDE);
        radius = 3.4f;
        slices = 16;
        stacks = 16;
        gl.glPopMatrix();
        gl.glPushMatrix();
        mercuryPlanetAngle += 7;
        mercuryAngle += Math.E;
        gl.glRotated(mercuryPlanetAngle,0,1,0);
        gl.glTranslated(20, 0, 20);
        gl.glRotated(mercuryAngle,0,1,0);
        glu.gluSphere(mercury, radius, slices, stacks);
        glu.gluDeleteQuadric(mercury);
        
        //Venus
        venusTexture.bind();
        GLUquadric venus = glu.gluNewQuadric();
        glu.gluQuadricTexture(venus, true);
        glu.gluQuadricDrawStyle(venus, GLU.GLU_FILL);
        glu.gluQuadricNormals(venus, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(venus, GLU.GLU_OUTSIDE);
        radius = 8.6f;
        slices = 16;
        stacks = 16;
        gl.glPopMatrix();
        gl.glPushMatrix();
        venusPlanetAngle += Math.PI;
        venusAngle += -1;
        gl.glRotated(venusPlanetAngle,0,1,0);
        gl.glTranslated(40, 0, 40);
        gl.glRotated(venusAngle,0,1,0);
        glu.gluSphere(venus, radius, slices, stacks);
        glu.gluDeleteQuadric(venus);
        
        // Apply texture.
        //earthTexture.enable();
        earthTexture.bind();
        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricTexture(earth, true);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        radius = 9.1f;
        slices = 16;
        stacks = 16;
        gl.glPopMatrix();
        gl.glPushMatrix();
        earthPlanetAngle += 3.3;
        earthAngle++;
        gl.glRotated(earthPlanetAngle,0,1,0);
        gl.glTranslated(60, 0, 60);
        gl.glRotated(earthAngle,0,1,0);
        glu.gluSphere(earth, radius, slices, stacks);
        glu.gluDeleteQuadric(earth);

        //Mars
        marsTexture.bind();
        GLUquadric mars = glu.gluNewQuadric();
        glu.gluQuadricTexture(mars, true);
        glu.gluQuadricDrawStyle(mars, GLU.GLU_FILL);
        glu.gluQuadricNormals(mars, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(mars, GLU.GLU_OUTSIDE);
        radius = 4.8f;
        slices = 16;
        stacks = 16;
        gl.glPopMatrix();
        gl.glPushMatrix();
        marsPlanetAngle += Math.sqrt(2);
        marsAngle += Math.sqrt(7);
        gl.glRotated(marsPlanetAngle,0,1,0);
        gl.glTranslated(80, 0, 80);
        gl.glRotated(marsAngle,0,1,0);
        glu.gluSphere(mars, radius, slices, stacks);
        glu.gluDeleteQuadric(mars);
        
        //Stars
        starsTexture.bind();
        GLUquadric stars = glu.gluNewQuadric();
        glu.gluQuadricTexture(stars, true);
        glu.gluQuadricDrawStyle(stars, GLU.GLU_FILL);
        glu.gluQuadricNormals(stars, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(stars, GLU.GLU_OUTSIDE);
        radius = 500f;
        slices = 16;
        stacks = 16;
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glRotated(-galaxyAngle,0,1,0);
        gl.glRotated(0.03*galaxyAngle,0,1,0);
        glu.gluSphere(stars, radius, slices, stacks);
        glu.gluDeleteQuadric(stars);

        // Compute satellite position.
        satelliteAngle = (satelliteAngle + 1f) % 360f;
        final float distance = 10.000f;
        final float x = (float) Math.sin(Math.toRadians(satelliteAngle)) * distance;
        final float y = (float) Math.cos(Math.toRadians(satelliteAngle)) * distance;
        final float z = 0;
        gl.glTranslatef(x, y, z);
        gl.glRotatef(satelliteAngle, 0, 0, -1);
        gl.glRotatef(45f, 0, 1, 0);

        // Set silver color, and disable texturing.
        gl.glDisable(GL.GL_TEXTURE_2D);
        float[] ambiColor = {0.3f, 0.3f, 0.3f, 1f};
        float[] specColor = {0.8f, 0.8f, 0.8f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambiColor, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specColor, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 90f);

        // Draw satellite body.
        final float cylinderRadius = 1f;
        final float cylinderHeight = 2f;
        final int cylinderSlices = 16;
        final int cylinderStacks = 16;
        GLUquadric body = glu.gluNewQuadric();
        glu.gluQuadricTexture(body, false);
        glu.gluQuadricDrawStyle(body, GLU.GLU_FILL);
        glu.gluQuadricNormals(body, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(body, GLU.GLU_OUTSIDE);
        gl.glTranslatef(0, 0, -cylinderHeight / 2);
        glu.gluDisk(body, 0, cylinderRadius, cylinderSlices, 2);
        glu.gluCylinder(body, cylinderRadius, cylinderRadius, cylinderHeight, cylinderSlices, cylinderStacks);
        gl.glTranslatef(0, 0, cylinderHeight);
        glu.gluDisk(body, 0, cylinderRadius, cylinderSlices, 2);
        glu.gluDeleteQuadric(body);
        gl.glTranslatef(0, 0, -cylinderHeight / 2);

        // Set white color, and enable texturing.
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0f);

        // Draw solar panels.
        gl.glScalef(6f, 0.7f, 0.1f);
        
        solarPanelTexture.bind();
        gl.glBegin(GL.GL_QUADS);
        final float[] frontUL = {-1.0f, -1.0f, 1.0f};
        final float[] frontUR = {1.0f, -1.0f, 1.0f};
        final float[] frontLR = {1.0f, 1.0f, 1.0f};
        final float[] frontLL = {-1.0f, 1.0f, 1.0f};
        final float[] backUL = {-1.0f, -1.0f, -1.0f};
        final float[] backLL = {-1.0f, 1.0f, -1.0f};
        final float[] backLR = {1.0f, 1.0f, -1.0f};
        final float[] backUR = {1.0f, -1.0f, -1.0f};
        // Front Face.
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3fv(frontUR, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3fv(frontUL, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3fv(frontLL, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3fv(frontLR, 0);
        // Back Face.
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3fv(backUL, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3fv(backUR, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3fv(backLR, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3fv(backLL, 0);
        gl.glEnd();

        // Restore old state.
        gl.glPopMatrix();
    }

    /**
     * Resizes the screen.
     * 
     * @see javax.media.opengl.GLEventListener#reshape(javax.media.opengl.GLAutoDrawable,
     *      int, int, int, int)
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL gl = drawable.getGL();
        gl.glViewport(0, 0, width, height);
    }

    /**
     * Changing devices is not supported.
     * 
     * @see javax.media.opengl.GLEventListener#displayChanged(javax.media.opengl.GLAutoDrawable,
     *      boolean, boolean)
     */
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

}