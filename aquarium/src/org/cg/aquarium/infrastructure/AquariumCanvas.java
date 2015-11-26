package org.cg.aquarium.infrastructure;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.Aquarium;

/**
 *
 * @author ldavid
 */
public class AquariumCanvas extends GLCanvas implements GLEventListener {

    protected GL gl;
    protected GLU glu;
    protected GLUT glut;
    protected FPSAnimator animator;

    public AquariumCanvas(GLCapabilities capabilities) {
        this(1366, 768, capabilities);
    }

    public AquariumCanvas(int width, int height, GLCapabilities capabilities) {
        super(capabilities);
        setSize(width, height);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL();
        glu = new GLU();
        glut = new GLUT();
        drawable.setGL(new DebugGL(gl));

        gl.glEnable(GL.GL_DEPTH_TEST);
//        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glDepthFunc(GL.GL_LESS);

        float ambient[] = {0f, 0f, 0.4f, 0f};
        float diffuse[] = {0.8f, 0.8f, 1.0f, 0.0f};
        float specular[] = {0.8f, 0.8f, 1.0f, 1.0f};
        float position[] = {5.0f, 10.0f, 2.0f, 0.0f};
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

        gl.glEnable(GL.GL_COLOR_MATERIAL);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
//        gl.glClearColor(.2f, .6f, 1f, 1f);
        gl.glClearColor(1, 1, 1, 1);

        animator = new FPSAnimator(this, 60);
        animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);

        Aquarium.getEnvironment().getCamera().processChanges(gl, glu, glut);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        Aquarium.getAquarium()
                .getAndCleanChanged().stream()
                .forEach(o -> o.processChanges(gl, glu, glut));

        Aquarium.getEnvironment().getBodies().forEach(b -> b.display(gl, glu, glut));
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
