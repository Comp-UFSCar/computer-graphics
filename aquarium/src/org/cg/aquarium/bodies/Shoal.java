package org.cg.aquarium.bodies;

import com.sun.opengl.util.GLUT;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.colliders.Collider;
import org.cg.aquarium.infrastructure.colliders.SphereCollider;
import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Shoal body.
 *
 * @author ldavid
 */
public class Shoal extends Mobile {

    public float maximumDistanceFromOrigin = 50;
    public int maximumShoalSize = 100;
    public float radius = 20;

    protected List<Fish> shoal;
    protected Collider collider;

    public Shoal() {
        this(new LinkedList<>());

        this.collider = new SphereCollider(
                position,
                position.add(new Vector(radius, 0, 0)));
    }

    public Shoal(List<Fish> shoal) {
        super();
        this.shoal = shoal;

        this.collider = new SphereCollider(
                position,
                position.add(new Vector(radius, 0, 0)));
    }

    public Shoal(List<Fish> shoal, Vector direction, float speed) {
        super(direction, speed);
        this.shoal = shoal;

        this.collider = new SphereCollider(
                position,
                position.add(new Vector(radius, 0, 0)));
    }

    public Shoal(
            List<Fish> shoal, Vector direction,
            float speed, Vector position) {
        super(direction, speed, position);

        this.shoal = shoal;

        this.collider = new SphereCollider(
                position,
                position.add(new Vector(radius, 0, 0)));
    }

    public Shoal(int size) {
        this();

        Random r = Environment.getEnvironment().getRandom();
        size = Math.min(size, maximumShoalSize - shoal.size());

        for (int i = 0; i < size; i++) {
            Vector fishPosition = position.add(Vector
                    .random(r)
                    .normalize()
                    .scale(r.nextFloat() * radius));

            fishPosition = new Vector(
                    fishPosition.getX(),
                    fishPosition.getY(),
                    0);

            addFish(position.add(fishPosition));
        }
    }

    public boolean addFish(Vector position) {
        Fish f = new Fish(direction, speed, position);

        return addFish(f);
    }

    protected boolean addFish(Fish fish) {
        if (isFull()) {
            return false;
        }

        shoal.add(fish);
        return true;
    }

    public boolean isFull() {
        return shoal.size() >= maximumShoalSize;
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void display(GL gl, GLU glu) {
        if (Environment.getEnvironment().isDebugging()) {
            GLUT glut = new GLUT();

            gl.glPushMatrix();
            gl.glColor3f(.2f, .2f, .2f);
            gl.glTranslatef(position.getX(), position.getY(), position.getZ());
            glut.glutWireSphere(radius, 10, 10);
            gl.glPopMatrix();
        }

        shoal.stream().forEach(f -> f.display(gl, glu));
    }

    @Override
    public void move() {
        if (position.norm() < maximumDistanceFromOrigin) {
            shoal.stream().forEach(f -> f.move());
        } else {
            Debug.info("Shoal's direction has changed:" + direction.toString());

            direction = Vector.ORIGIN.delta(position)
                    .add(Vector.random().normalize().scale(10f))
                    .normalize();

            shoal.stream().forEach(f -> f.move(direction));
        }

        setPosition(position.add(direction.scale(speed)));
    }
}
