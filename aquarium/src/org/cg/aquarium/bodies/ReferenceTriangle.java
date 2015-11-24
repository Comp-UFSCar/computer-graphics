package org.cg.aquarium.bodies;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.Body;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.representations.Color;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class ReferenceTriangle extends Body implements Mobile {

    public final int ITERATIONS_TO_CHANGE = 1000;
    public final float MAX_DISTANCE_FROM_ORIGIN = 50;

    protected Vector direction;
    private final Color color;
    protected float speed = .005f;

    public ReferenceTriangle() {
        super();

        position = Vector.ORIGIN;

        direction = Vector.random(Environment.getEnvironment()
                .getRandom()).normalize();
        color = Color.random(Environment.getEnvironment().getRandom());
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void display(GL gl, GLU glu) {
        gl.glColor3f(color.getRed(), color.getBlue(), color.getGreen());
        gl.glTranslatef(position.getX(), position.getY(), position.getZ());
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(-1, -1, 0);
        gl.glVertex3f(+1, -1, 0);
        gl.glVertex3f(0, 1, 0);
        gl.glEnd();
    }

    @Override
    public void move() {
        if (Environment.getEnvironment().getTick() % ITERATIONS_TO_CHANGE == 0) {
            direction = Vector
                    .random(Environment.getEnvironment().getRandom())
                    .normalize();

            Vector v = Vector.ORIGIN.delta(position);
            if (v.norm() > MAX_DISTANCE_FROM_ORIGIN) {
                direction = v.normalize();
            }
        }

        setPosition(position.add(direction.scale(speed)));
    }
}
