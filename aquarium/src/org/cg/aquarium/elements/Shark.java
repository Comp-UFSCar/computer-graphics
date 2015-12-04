package org.cg.aquarium.elements;

import libs.modelparser.Material;
import libs.modelparser.Vertex;
import libs.modelparser.WavefrontObject;
import org.cg.aquarium.infrastructure.base.Graphics;
import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Shark.
 *
 * Sharks are predator for the Aquarium.
 *
 * @author ldavid
 */
public class Shark extends Fish {

    public static double MAXIMUM_DISTANCE = 100000;
    public static double MOMENTUM = 2;

    private static Graphics graphics;

    public Shark(Shoal shoal) {
        super(shoal);
    }

    public Shark(Shoal shoal, Vector direction, double speed) {
        super(shoal, direction, speed);
    }

    public Shark(Shoal shoal, Vector direction, double speed, Vector position) {
        super(shoal, direction, speed, position);
    }

    @Override
    public void initializeProperties() {
        if (graphics == null) {
            size = new Vector(6, 6, 6);

            Material material = new Material("shark");
            material.setKa(new Vertex(1f, 1f, 1));
            material.setKd(new Vertex(1f, 1f, 1));
            material.setKs(new Vertex(1f, 1f, 1f));
            material.setShininess(100f);

            graphics = new Graphics(
                    new WavefrontObject(
                            "/resources/shark/model.obj",
                            (float) size.getX(),
                            (float) size.getY(),
                            (float) size.getZ()),
                    material, "/resources/shark/texture.png");
        }
    }

    @Override
    public void update() {
        double d = position.squareDistance(shoal.getPosition());

        Debug.info("Shark-shoal delta: " + d);

        // Sharks always follow the shoal, but because of their high speed, 
        // mobility is highly compromised once they missed the Shaol kernel.
        // To simulate this, Sharks' directions are defined as a linear 
        // combination of momentum and mobility, where momentum is
        // highly important when close to the Shoal and gradually looses
        // importance as the shark distances itself from it.
        setDirection(direction.scale(MOMENTUM * (MAXIMUM_DISTANCE - d) / MAXIMUM_DISTANCE)
                .add(shoal.getPosition().delta(position).normalize().scale(d / MAXIMUM_DISTANCE))
                .add(Vector.random().normalize().scale(RANDOMNESS))
        );
        move();
    }

}
