package org.cg.aquarium.elements;

import libs.modelparser.Material;
import libs.modelparser.Vertex;
import libs.modelparser.WavefrontObject;
import org.cg.aquarium.infrastructure.base.ObjectModel;
import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.representations.Color;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class Shark extends Fish {

    public static float MAXIMUM_DISTANCE = 100000;
    public static float MOMENTUM = .2f;

    public Shark(Shoal shoal) {
        super(shoal);
    }

    public Shark(Shoal shoal, Color color) {
        super(shoal, color);
    }

    public Shark(Shoal shoal, Vector direction, float speed) {
        this(shoal, Color.random(), direction, speed);
    }

    public Shark(Shoal shoal, Color color, Vector direction, float speed) {
        super(shoal, color, direction, speed);
    }

    public Shark(Shoal shoal, Vector direction, float speed, Vector position) {
        this(shoal, Color.random(), direction, speed, position);
    }

    public Shark(Shoal shoal, Color color, Vector direction, float speed, Vector position) {
        super(shoal, color, direction, speed, position);
    }

    @Override
    public void initializeAttributes() {
        size = new Vector(6, 6, 6);
        
        Material material = new Material("shark");
        material.setKa(new Vertex(.6f, .6f, 1));
        material.setKd(new Vertex(.6f, .6f, 1));
        material.setKs(new Vertex(.2f, .2f, .2f));
        material.setShininess(.3f);

        modelObject = new ObjectModel(
                new WavefrontObject("Shark.obj", size.getX(), size.getY(), size.getZ()),
                material
        );
    }

    @Override
    public void update() {
        float d = position.squareDistance(shoal.getPosition());

        Debug.info("Shark-shoal delta: " + d);

        setDirection(direction.scale((MAXIMUM_DISTANCE - d) / MAXIMUM_DISTANCE)
                .add(shoal.getPosition().delta(position).normalize().scale(d / MAXIMUM_DISTANCE))
                .add(Vector.random().normalize().scale(RANDOMNESS))
        );
        move();
    }

}
