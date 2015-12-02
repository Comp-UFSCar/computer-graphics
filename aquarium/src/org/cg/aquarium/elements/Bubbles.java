package org.cg.aquarium.elements;

import com.sun.opengl.util.GLUT;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Bubbles.
 *
 * Bubbles animation in aquarium.
 *
 * @author ldavid
 */
public class Bubbles extends Mobile {

    public final static Vector VARIANCE = new Vector(40, 20, 4);

    public static int N_BUBBLES = 4;
    public static int N_BUBBLES_GROUPS = 2;

    /**
     * Update bubbles.
     *
     * Simulates bubbles default behavior, which tends to make them to go up,
     * considering water/air density disparity.
     *
     * When it leaves the screen, it's automatically repositioned to the bottom
     * and has its X and Z components randomized according to the
     * {@code VARIANCE} factor.
     *
     */
    @Override
    public void update() {
        if (position.getY() > VARIANCE.getY()) {
            setPosition(new Vector(
                    (Environment.getEnvironment().getRandom().nextDouble() - .5)
                    * 2 * VARIANCE.getX(),
                    -position.getY(),
                    position.getZ()
            ));
        }

        move();
    }

    @Override
    public void initializeProperties() {
        Random r = Environment.getEnvironment().getRandom();

        setPosition(new Vector(
                (r.nextDouble() - .5) * 2 * VARIANCE.getX(),
                -r.nextDouble() * VARIANCE.getY(),
                (r.nextDouble() - .5) * 2 * VARIANCE.getZ()));

        setDirection(Vector.UP);
        setSpeed(.8f);
        setSize(Vector.RIGHT);
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        Random r = Environment.getEnvironment().getRandom();

        gl.glPushMatrix();
        gl.glTranslated(position.getX(), position.getY(), position.getZ());
        gl.glColor3f(1, 1, 1);

        for (int i = 0; i < N_BUBBLES_GROUPS; i++) {
            gl.glTranslated(position.getX() + 4 * r.nextDouble(),
                    position.getY() - 3,
                    position.getZ());

            for (int j = 0; j < N_BUBBLES; j++) {
                gl.glTranslated(
                        r.nextDouble() - .05,
                        r.nextDouble() - .05,
                        r.nextDouble() - .05
                );

                glut.glutSolidSphere(r.nextDouble() * size.getX(), 10, 10);
            }
        }

        gl.glPopMatrix();
    }
}
