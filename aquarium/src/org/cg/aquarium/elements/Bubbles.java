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
 * @author ldavid
 */
public class Bubbles extends Mobile {

    public final double CEIL = 100;
    public int bubblesCount = 4;

    @Override
    public void update() {
        if (position.getY() > CEIL) {
            setPosition(position.mirrorOnHorizontalAxis());
        }

        move();
    }

    @Override
    public void initializeProperties() {
        setPosition(Vector.DOWN.scale(20).add(
                Vector.random().normalize().scale(10)));
        
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

        for (int i = 0; i < bubblesCount; i++) {
            gl.glTranslated(position.getX() + r.nextDouble(),
                    position.getY() - 3,
                    position.getZ());

            for (int j = 0; j < 3; j++) {
                gl.glTranslated(
                        r.nextDouble() - .5,
                        r.nextDouble() - .5,
                        r.nextDouble() - .5
                );
                
                glut.glutSolidSphere(size.getX(), 10, 10);
            }
        }

        gl.glPopMatrix();
    }

}
