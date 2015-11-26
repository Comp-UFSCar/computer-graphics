package org.cg.aquarium.infrastructure.colliders;

import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class SphereCollider implements Collider {

    protected Mobile parent;
    protected float radius;

    public SphereCollider(Mobile parent, float radius) {
        if (radius < 0) {
            Debug.error(String.format("Cannot create SphereCollider with negative radius: ", radius));
        }

        this.parent = parent;
        this.radius = radius;
    }

    public Mobile getParent() {
        return parent;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public boolean isColliding(Collider c) {
        return c.closestPointFrom(parent.getPosition()).delta(parent.getPosition()).norm()
                <= radius;
    }

    @Override
    public boolean contains(Collider c) {
        return c.farthestPointFrom(parent.getPosition()).delta(parent.getPosition()).norm()
                <= radius;
    }

    @Override
    public Vector closestPointFrom(Vector v) {
        Vector center = getCenter();
        return v.delta(center).normalize().scale(getRadius()).add(center);
    }

    @Override
    public Vector farthestPointFrom(Vector v) {
        Vector center = getCenter();
        return v.delta(center).normalize().scale(-getRadius()).add(center);
    }

    @Override
    public Vector getCenter() {
        return parent.getPosition();
    }

}
