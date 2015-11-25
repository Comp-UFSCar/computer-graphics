package org.cg.aquarium.elements;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.representations.Color;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Fish.
 *
 * Represents a fish of the shoal.
 *
 * @author ldavid
 */
public class Fish extends Mobile {

    public final static float DIRECTION_RANDOMNESS = .1f,
            SPEED_RANDOMNESS = .005f;

    public final static boolean RANDOMIZE_SPEED = true,
            RANDOMIZE_DIRECTION = true;

    protected Color color;

    public Fish() {
        this(Color.random());
    }

    public Fish(Color color) {
        super();
        setColor(color);
    }
    
    public Fish(Vector direction, float speed) {
        this(Color.random(), direction, speed);
    }

    public Fish(Color color, Vector direction, float speed) {
        super(direction, speed);
        setColor(color);
    }
    
    public Fish(Vector direction, float speed, Vector position) {
        this(Color.random(), direction, speed, position);
    }

    public Fish(Color color, Vector direction, float speed, Vector position) {
        super(direction, speed, position);
        setColor(color);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void display(GL gl, GLU glu) {
        gl.glPushMatrix();
        gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
        gl.glTranslatef(position.getX(), position.getY(), position.getZ());
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex3f(-1, -1, 0);
        gl.glVertex3f(+1, -1, 0);
        gl.glVertex3f(0, 1, 0);
        gl.glEnd();
        gl.glPopMatrix();
    }

    @Override
    public void setDirection(Vector direction) {
        if (RANDOMIZE_DIRECTION) {
            direction = direction.add(Vector
                    .random()
                    .normalize()
                    .scale(DIRECTION_RANDOMNESS)
            ).normalize();
        }

        this.direction = direction;
    }

    @Override
    public void setSpeed(float speed) {
        if (RANDOMIZE_SPEED) {
            speed += Environment.getEnvironment().getRandom().nextFloat()
                    * SPEED_RANDOMNESS;
        }

        this.speed = speed;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
