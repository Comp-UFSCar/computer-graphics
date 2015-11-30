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

        Aquarium.getAquarium().getLight()
                .setup(gl)
                .display(gl, glu, glut);

        animator = new FPSAnimator(this, 60);
        animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 10, 500);

        Aquarium.getEnvironment().getCamera().display(gl, glu, glut);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        Aquarium.getAquarium()
                .getAndCleanChanged().stream()
                .forEach(o -> o.display(gl, glu, glut));

        Aquarium.getEnvironment().getBodies().forEach(b -> b.display(gl, glu, glut));
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
