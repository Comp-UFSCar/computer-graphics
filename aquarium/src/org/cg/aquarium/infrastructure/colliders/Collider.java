package org.cg.aquarium.infrastructure.colliders;

import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Collider.
 *
 * Detects collision between objects.
 *
 * @author ldavid
 */
public interface Collider {

    public boolean isColliding(Collider c);

    public boolean contains(Collider c);

    public Vector closestPointFrom(Vector v);

    public Vector farthestPointFrom(Vector v);

    public Vector getCenter();
}
