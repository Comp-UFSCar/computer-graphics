package org.cg.aquarium.infrastructure.colliders;

import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class SphereCollider implements Collider {

    protected Vector center;
    protected float radius;

    public SphereCollider(Vector center, float radius) {
        if (radius < 0) {
            Debug.error(String.format("Cannot create SphereCollider with negative radius: ", radius));
        }

        this.center = center;
        this.radius = radius;
    }

    public Vector getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public boolean isColliding(Collider c) {
        return c.closestPointFrom(center).delta(center).norm()
                <= radius;
    }

    @Override
    public boolean contains(Collider c) {
        return c.farthestPointFrom(center).delta(center).norm()
                <= radius;
    }

    @Override
    public Vector closestPointFrom(Vector v) {
        return v.delta(center).normalize().scale(getRadius());
    }

    @Override
    public Vector farthestPointFrom(Vector v) {
        return v.delta(center).normalize().scale(-getRadius());
    }

}
