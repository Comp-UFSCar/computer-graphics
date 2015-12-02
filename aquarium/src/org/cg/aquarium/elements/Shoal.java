package org.cg.aquarium.elements;

import com.sun.opengl.util.GLUT;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.base.Body;
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
    public float radius = 20;

    protected Vector rotation = new Vector(-1, 1, 0);
    protected List<Fish> shoal;
    protected Collider collider;

    public Shoal() {
        this(new LinkedList<>());

        this.collider = new SphereCollider(this, radius);
    }

    public Shoal(List<Fish> shoal) {
        super();
        this.shoal = shoal;

        this.collider = new SphereCollider(this, radius);
    }

    public Shoal(List<Fish> shoal, Vector direction, float speed) {
        super(direction, speed);
        this.shoal = shoal;

        this.collider = new SphereCollider(this, radius);
    }

    public Shoal(
            List<Fish> shoal, Vector direction,
            float speed, Vector position) {
        super(direction, speed, position);

        this.shoal = shoal;

        this.collider = new SphereCollider(this, radius);
    }

    public Shoal(int size) {
        this();

        Random r = Environment.getEnvironment().getRandom();

        for (int i = 0; i < size; i++) {
            Vector fishPosition = position.add(Vector
                    .random(r)
                    .normalize()
                    .scale(r.nextFloat() * radius));

            addFish(position.add(fishPosition));
        }
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        if (Environment.getEnvironment().isDebugging()) {
            gl.glPushMatrix();
            gl.glColor3f(.2f, .2f, .2f);
            gl.glTranslatef(position.getX(), position.getY(), position.getZ());
            glut.glutWireSphere(radius, 10, 10);
            gl.glPopMatrix();
        }
    }

    public Collider getCollider() {
        return collider;
    }

    public Vector getRotation() {
        return rotation;
    }

    public void setRotation(Vector rotation) {
        this.rotation = rotation;
    }

    public void addFish(Vector position) {
        Fish f = new Fish(
                this,
                direction.add(Vector.random()).normalize(),
                2 * speed, position);

        addFish(f);
    }

    protected void addFish(Fish fish) {
        shoal.add(fish);
    }

    @Override
    public void initializeProperties() {
    }

    @Override
    public void update() {
        if (!isInsideAquarium()) {
            Debug.info("Shoal's direction has changed:" + direction.toString());

            direction = Vector.ZERO.delta(position)
                    .add(Vector.random().normalize().scale(10f))
                    .normalize();
        }

        move();

        shoal.stream().forEach(f -> f.update());
    }

    public float distanceFromAquariumCenter() {
        return position.norm();
    }

    public boolean isInsideAquarium() {
        return distanceFromAquariumCenter() < maximumDistanceFromOrigin;
    }

    public List<Body> getInnerShoal() {
        return (List<Body>) (List<?>) shoal;
    }

}
