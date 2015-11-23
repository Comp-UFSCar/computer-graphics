package org.cg.aquarium.bodies;

import javax.media.opengl.GL;
import org.cg.aquarium.infrastructure.Body;
import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class ReferenceTriangle extends Body implements Mobile {
    
    public final int ITERATIONS_TO_CHANGE = 1000;
    public final float MAX_DISTANCE_FROM_ORIGIN = 50;
    int iteration = 0;
    
    protected Vector direction;
    protected float speed = .01f;

    public ReferenceTriangle() {
        super();
        
        direction = Vector.random().normalize();
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void display(GL gl) {
        gl.glColor3f(1f, .2f, .2f);
        gl.glTranslatef(position.getX(), position.getY(), position.getZ());
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(-1, -1, 0);
        gl.glVertex3f(+1, -1, 0);
        gl.glVertex3f(0, 1, 0);
        gl.glEnd();
    }

    @Override
    public void move() {
        iteration++;
        if (iteration > ITERATIONS_TO_CHANGE) {
            direction = Vector.random().normalize();
            iteration = 0;
            
            Vector v = Vector.ORIGIN.delta(position);
            if (v.norm() > MAX_DISTANCE_FROM_ORIGIN) {
                direction = v.normalize();
            }
        }

        setPosition(position.add(direction.scale(speed)));
    }
}
