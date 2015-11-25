package org.cg.aquarium.infrastructure.colliders;

import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class BoxCollider implements Collider {

    protected Vector start;
    protected Vector end;

    public BoxCollider(Vector start, Vector end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Vector closestPointFrom(Vector v) {
        return start.delta(v).norm() < end.delta(v).norm() ? start : end;
    }

    @Override
    public Vector farthestPointFrom(Vector v) {
        return start.delta(v).norm() > end.delta(v).norm() ? start : end;
    }

    @Override
    public boolean isColliding(Collider c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(Collider c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector getCenter() {
        return start.delta(end).scale(.5f).add(start);
    }

}
