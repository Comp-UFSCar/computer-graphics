package org.cg.aquarium.bodies;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import org.cg.aquarium.infrastructure.Body;

/**
 * Earth placeholder.
 *
 * @author ldavid
 */
public class Earth extends Body {

    @Override
    public void update() {

    }

    @Override
    public void display(GL gl, GLU glu) {
        // Draw sphere (possible styles: FILL, LINE, POINT).
        gl.glColor3f(0.3f, 0.5f, 1f);
        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = 6.378f;
        final int slices = 16;
        final int stacks = 16;
        glu.gluSphere(earth, radius, slices, stacks);
        glu.gluDeleteQuadric(earth);
    }

}
