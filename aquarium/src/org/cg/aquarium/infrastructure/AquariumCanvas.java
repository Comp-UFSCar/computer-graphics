package org.cg.aquarium.infrastructure;

import com.sun.opengl.util.FPSAnimator;
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

    private GL gl;
    private GLU glu;
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
        drawable.setGL(new DebugGL(gl));

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glClearColor(0f, 0f, 0f, 1f);

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

        Aquarium.getEnvironment().getCamera().processChanges(gl, glu);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        Aquarium.getAquarium()
                .getAndCleanChanged().stream()
                .forEach(o -> o.processChanges(gl, glu));

        Aquarium.getEnvironment().getBodies().forEach(b -> b.display(gl, glu));
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
