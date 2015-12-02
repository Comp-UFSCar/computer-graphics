package org.cg.aquarium.elements;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import libs.modelparser.Material;
import libs.modelparser.Vertex;
import libs.modelparser.WavefrontObject;
import org.cg.aquarium.Aquarium;
import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.base.Graphics;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Fish.
 *
 * Represents a fish of the shoal.
 *
 * @author ldavid
 */
public class Fish extends Mobile {

    public static final float MAXIMUM_SAFE_DISTANCE = 2;
    public static final float ALIGNMENT = .8f,
            COHERSION = .2f,
            SEPARATION = .4f,
            EVASION = .4f,
            RANDOMNESS = .01f;

    protected Shoal shoal;
    protected Graphics graphics;

    public Fish(Shoal shoal) {
        super();

        this.shoal = shoal;
    }

    public Fish(Shoal shoal, Vector direction, float speed) {
        super(direction, speed);

        this.shoal = shoal;
    }

    public Fish(Shoal shoal, Vector direction, float speed, Vector position) {
        super(direction, speed, position);

        this.shoal = shoal;
    }

    @Override
    public void initializeProperties() {
        size = new Vector(1, 1, 1);

        Material material = new Material("shark");
        material.setKa(new Vertex(.6f, .6f, 1));
        material.setKd(new Vertex(.6f, .6f, 1));
        material.setKs(new Vertex(.2f, .2f, .2f));
        material.setShininess(.3f);

        graphics = new Graphics(
                new WavefrontObject("Shark.obj", size.getX(), size.getY(), size.getZ()),
                material
        );
    }

    @Override
    public void update() {
        Vector v = computeAlignment()
                .add(computeCohersion())
                .add(computeSeparation())
                .add(computeEvasion())
                .add(computeRandomness());

        setDirection(direction.add(v).normalize());

        move();
    }

    public boolean isPossiblyInDanger() {
        return distanceFromShoalCenter() > shoal.radius + MAXIMUM_SAFE_DISTANCE;
    }

    public float distanceFromShoalCenter() {
        return position.l2Distance(shoal.getPosition());
    }

    protected Vector computeAlignment() {
        return shoal.getDirection().scale(ALIGNMENT);
    }

    protected Vector computeCohersion() {
        return shoal.getPosition()
                .delta(position)
                .normalize()
                .scale(COHERSION * distanceFromShoalCenter() / shoal.radius);
    }

    protected Vector computeSeparation() {
        return shoal.getPosition().delta(position).reflected()
                .normalize().scale(SEPARATION);
    }

    protected Vector computeRandomness() {
        return Vector.random().normalize().scale(RANDOMNESS);
    }

    protected Vector computeEvasion() {
        Vector v = Vector.ZERO;

        Mobile predator = (Mobile) Aquarium.getAquarium().getPredator(0);

        if (predator != null) {
            float distance = predator.getPosition().squareDistance(position);

            if (distance < 200) {
                // Find a scape route and scale inversibly proportional to
                // distance between this and predator.
                v = predator.getDirection().cross(
                        predator.getDirection().mirrorOnVerticalAxis()
                ).scale(EVASION);
            }
        }

        return v;
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();

        gl.glTranslatef(position.getX(), position.getY(), position.getZ());

        graphics.glDefineObjectMaterial(gl);
        graphics.glAlignObjectWithVector(gl, direction, Vector.FORWARD);
        graphics.glRenderObject(gl);
        graphics.glDebugPlotVector(gl, direction.scale(20 * speed));

        gl.glPopMatrix();
    }

}
