package org.cg.aquarium.infrastructure.colliders;

import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class SphereCollider extends Collider {

    public SphereCollider(Vector start, Vector end) {
        super(start, end);
    }

    public float radius() {
        return end.delta(start).norm();
    }

    @Override
    public Vector closestPointOf(Vector v) {
        return v.delta(start).normalize().scale(radius());
    }

    @Override
    public Vector farthestPointOf(Vector v) {
        return v.delta(start).normalize().scale(-radius());
    }

}
