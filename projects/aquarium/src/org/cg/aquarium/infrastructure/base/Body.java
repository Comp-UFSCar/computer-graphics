package org.cg.aquarium.infrastructure.base;

import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Body base class.
 *
 * Subclasses of Body are elements of an environment.
 *
 * @author ldavid
 */
public abstract class Body implements Visible {

    protected Vector position;
    protected Vector size;

    public Body() {
        this(Vector.ZERO);
        initializeProperties();
    }

    public Body(Vector position) {
        this.position = position;
        initializeProperties();
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getSize() {
        return size;
    }

    public void setSize(Vector size) {
        this.size = size;
    }

    public abstract void update();

    public abstract void initializeProperties();
}
