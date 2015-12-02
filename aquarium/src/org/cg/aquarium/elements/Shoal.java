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
import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Shoal body.
 *
 * @author ldavid
 */
public class Shoal extends Mobile {

    public final static double MAXIMUM_ORIGIN_OFFSET = 1200;
    public final static double RADIUS = 20;

    protected List<Fish> shoal;

    public Shoal() {
        this(new LinkedList<>());
    }

    public Shoal(List<Fish> shoal) {
        super();
        this.shoal = shoal;
    }

    public Shoal(int size) {
        this();

        Random r = Environment.getEnvironment().getRandom();

        for (int i = 0; i < size; i++) {
            Vector fishPosition = position.add(Vector
                    .random(r)
                    .normalize()
                    .scale(r.nextFloat() * RADIUS));

            addFish(position.add(fishPosition));
        }
    }

    @Override
    public void initializeProperties() {
        position = Vector.random().normalize().scale(20);
        direction = Vector.random().normalize();

        setSpeed(.05);
    }

    public void addFish(Vector position) {
        Fish f = new Fish(
                this,
                direction.add(Vector.random()).normalize(),
                4 * speed, position);

        addFish(f);
    }

    protected void addFish(Fish fish) {
        shoal.add(fish);
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

    public boolean isInsideAquarium() {
        return position.squareDistance(Vector.ZERO) < MAXIMUM_ORIGIN_OFFSET;
    }

    public List<Body> getInnerShoal() {
        return (List<Body>) (List<?>) shoal;
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        if (Environment.getEnvironment().isDebugging()) {
            gl.glPushMatrix();
            gl.glColor3f(.2f, .2f, .2f);
            gl.glTranslated(position.getX(), position.getY(), position.getZ());
            glut.glutWireSphere(RADIUS, 10, 10);
            gl.glPopMatrix();
        }
    }

}
