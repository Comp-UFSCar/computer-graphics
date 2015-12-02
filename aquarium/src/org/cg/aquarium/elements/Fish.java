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
import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Fish.
 *
 * Represents a fish of the shoal.
 *
 * @author ldavid
 */
public class Fish extends Mobile {

    public static final double ALIGNMENT = .3f,
            COHESION = .3f,
            SEPARATION = .3f,
            EVASION = 0,
            RANDOMNESS = .01f,
            MOMENTUM = .1f;

    protected Shoal shoal;
    protected Graphics graphics;

    public Fish(Shoal shoal) {
        super();

        this.shoal = shoal;
    }

    public Fish(Shoal shoal, Vector direction, double speed) {
        super(direction, speed);

        this.shoal = shoal;
    }

    public Fish(Shoal shoal, Vector direction, double speed, Vector position) {
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
                new WavefrontObject("Shark.obj",
                        (float) size.getX(), (float) size.getY(),
                        (float) size.getZ()),
                material
        );
    }

    @Override
    public void update() {
        setDirection(computeMomentum()
                .add(computeAlignment())
                .add(computeCohesion())
                .add(computeSeparation())
                .add(computeEvasion())
                .add(computeRandomness()));

        move();
    }

    public double distanceFromShoalCenter() {
        return position.squareDistance(shoal.getPosition());
    }

    protected Vector computeMomentum() {
        return direction.scale(MOMENTUM);
    }

    protected Vector computeAlignment() {
        return shoal.getDirection().scale(ALIGNMENT);
    }

    /**
     * Compute cohesion factor for shoal movement.
     *
     * Cohesion is the factor that brings the shoal tight together, being
     * fundamentally high when the shoal is sparse and progressively loosing
     * importance as it becomes denser.
     *
     * @return the vector aligned to the direction that will increase the
     * cohesion of the shoal, scaled by a {@code COHESION} factor.
     */
    protected Vector computeCohesion() {
        double dist = distanceFromShoalCenter() / (shoal.radius * shoal.radius);
        return shoal.getPosition()
                .delta(position)
                .normalize()
                //                .scale(COHESION);
                .scale(COHESION * dist);
    }

    protected Vector computeSeparation() {
        return position.delta(shoal.getPosition())
                .normalize().scale(SEPARATION);
    }

    protected Vector computeRandomness() {
        return Vector.random().normalize().scale(RANDOMNESS);
    }

    protected Vector computeEvasion() {
        Vector v = Vector.ZERO;

        Mobile predator = (Mobile) Aquarium.getAquarium().getPredator(0);

        if (predator != null) {
            double distance = predator.getPosition().squareDistance(position);

            Debug.info("Predator-fish distance:" + distance);

            if (distance < 600) {
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

        gl.glTranslated(position.getX(), position.getY(), position.getZ());

        graphics.glDefineObjectMaterial(gl);
        graphics.glAlignObjectWithVector(gl, direction, Vector.FORWARD);
        graphics.glRenderObject(gl);
        graphics.glDebugPlotVector(gl, direction.scale(20 * speed));

        gl.glPopMatrix();
    }

}
