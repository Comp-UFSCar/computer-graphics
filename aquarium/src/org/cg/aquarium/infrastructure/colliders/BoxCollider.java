package org.cg.aquarium.infrastructure.colliders;

import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class BoxCollider extends Collider {

    public BoxCollider(Vector start, Vector end) {
        super(start, end);
    }

    @Override
    public Vector closestPointOf(Vector v) {
        return start.delta(v).norm() < end.delta(v).norm() ? start : end;
    }

    @Override
    public Vector farthestPointOf(Vector v) {
        return start.delta(v).norm() > end.delta(v).norm() ? start : end;
    }

}
