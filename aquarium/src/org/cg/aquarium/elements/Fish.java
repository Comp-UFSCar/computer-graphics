package org.cg.aquarium.elements;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.helpers.Debug;
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
    protected Shoal shoal;

    public Fish(Shoal shoal) {
        this(shoal, Color.random());
    }

    public Fish(Shoal shoal, Color color) {
        super();

        this.shoal = shoal;
        setColor(color);
    }

    public Fish(Shoal shoal, Vector direction, float speed) {
        this(shoal, Color.random(), direction, speed);
    }

    public Fish(Shoal shoal, Color color, Vector direction, float speed) {
        super(direction, speed);

        this.shoal = shoal;
        setColor(color);
    }

    public Fish(Shoal shoal, Vector direction, float speed, Vector position) {
        this(shoal, Color.random(), direction, speed, position);
    }

    public Fish(Shoal shoal, Color color, Vector direction, float speed, Vector position) {
        super(direction, speed, position);

        this.shoal = shoal;
        setColor(color);
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

    @Override
    public void update() {
        if (isPossiblyInDanger()) {
            Debug.info("Fish in danger zone. Distance from shoal center: " + distanteFromShoalCenter());
            headBackToShoal();
        }
    }

    private void headBackToShoal() {
        setDirection(shoal.getPosition().delta(position).normalize());
    }

    public boolean isPossiblyInDanger() {
        return distanteFromShoalCenter() > shoal.radius;
    }

    public float distanteFromShoalCenter() {
        return position.l2Distance(shoal.getPosition());
    }

}
