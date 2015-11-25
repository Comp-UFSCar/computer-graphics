package org.cg.aquarium.infrastructure.colliders;

import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Collider.
 *
 * Detects collision between objects.
 *
 * @author ldavid
 */
public abstract class Collider {

    protected Vector start;
    protected Vector end;

    public Collider(Vector start, Vector end) {
        this.start = start;
        this.end = end;
    }

    public boolean isColliding(Collider c) {
        return c.closestPointOf(start).delta(start).norm()
                < end.delta(start).norm();
    }

    public boolean contains(Collider c) {
        return c.farthestPointOf(start).delta(start).norm()
                < end.delta(start).norm();
    }

    public abstract Vector closestPointOf(Vector v);

    public abstract Vector farthestPointOf(Vector v);
}
