package org.cg.aquarium.elements;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
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

    public static final double EVASION_SPEED_INCREASE = 2;
    public static final double MAXIMUM_SQUARE_OFFSET = 100000;
    public static final double ALIGNMENT = .01,
            EVASIVENESS = .8,
            RANDOMNESS = .01,
            MOMENTUM = .1;

    protected Shoal shoal;
    protected Graphics graphics;
    protected Texture texture;

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

    /**
     * Update Fish direction based on different factors.
     *
     * Each computation returns a direction scaled by its factor. Check each
     * method's respective documentation for more info.
     *
     */
    @Override
    public void update() {
        setDirection(computeMomentum()
                .add(computeCohesiveness())
                .add(computeAlignment())
                .add(computeEvasiveness())
                .add(computeRandomness())
        );

        move();
    }

    public double squareDistanceFromShoalCenter() {
        return position.squareDistance(shoal.getPosition());
    }

    protected Vector computeMomentum() {
        return direction
                .scale((MAXIMUM_SQUARE_OFFSET - squareDistanceFromShoalCenter())
                        / MAXIMUM_SQUARE_OFFSET);
    }

    /**
     * Compute the alignment of a Fish.
     *
     * Alignment is the factor that forces each fish in the Shoal to align with
     * the Shoal movement itself.
     *
     * @return Vector scaled by the {@code ALIGNMENT} factor defined.
     */
    protected Vector computeAlignment() {
        return shoal.getDirection().scale(ALIGNMENT);
    }

    /**
     * Compute cohesiveness of a Fish movement.
     *
     * Cohesion is the factor that brings the shoal tight together, being
     * fundamentally high when the shoal is sparse and progressively loosing
     * importance as it becomes denser.
     *
     * @return Vector aligned to the direction that will increase the cohesion
     * of the shoal, scaled by a {@code COHESION} factor.
     */
    protected Vector computeCohesiveness() {
        return shoal.getPosition()
                .delta(position)
                .normalize()
                .scale(squareDistanceFromShoalCenter() / MAXIMUM_SQUARE_OFFSET);
    }

    /**
     * Compute randomness of a Fish movement.
     *
     * Randomness if the factor that represent the individual movement
     * willingness of each component of the shoal.
     *
     * @return Vector scaled by the {@code RANDOMNESS} factor defined.
     */
    protected Vector computeRandomness() {
        return Vector.random().normalize().scale(RANDOMNESS);
    }

    /**
     * Compute evasiveness of a Fish movement.
     *
     * Evasiveness is the most complicated from all factors, and also the
     * strongest. In normal conditions, evasiveness is the Vector.Direction. It
     * will, however, drastically change when the fish notice that a predator is
     * dangerously close.
     *
     * @return Vector scaled by the {@code EVASIVENESS} factor defined.
     */
    protected Vector computeEvasiveness() {
        Vector v = Vector.ZERO;

        setSpeed(baseSpeed);

        Mobile predator = (Mobile) Aquarium.getAquarium().getPredator(0);

        if (predator != null) {
            double distance = predator.getPosition().squareDistance(position);

            if (distance < 600) {
                Debug.info("Danger! Predator-fish distance: " + distance);

                // Find a scape route from the predator.
                // alpha is the vector predator.position-this.position
                // projected onto the vector predator.direction.
                // predator.direction scaled by alpha will return the estimated
                // point p where the shark will pierce the shoal. p-position
                // is the best bet for a fish to flee.
                double alpha = position
                        .delta(predator.getPosition())
                        .dot(predator.getDirection());

                v = predator.getDirection().scale(alpha);
                v = position.delta(v).normalize().scale(EVASIVENESS);

                setSpeed(baseSpeed * EVASION_SPEED_INCREASE);
            }
        }

        return v;
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();

        gl.glTranslated(position.getX(), position.getY(), position.getZ());

        graphics.glDefineObjectMaterial(gl);
        if(texture != null) {
            texture.enable();
            texture.bind();
        }
        graphics.glAlignObjectWithVector(gl, direction, Vector.FORWARD);
        graphics.glRenderObject(gl);
        graphics.glDebugPlotVector(gl, direction.scale(20 * speed));
        if(texture != null) {
            texture.disable();
        }

        gl.glPopMatrix();
    }

}
