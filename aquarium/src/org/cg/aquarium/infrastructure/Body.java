package org.cg.aquarium.infrastructure;

import org.cg.aquarium.infrastructure.base.Visible;
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

    public Body() {
        this(Vector.ORIGIN);
    }

    public Body(Vector position) {
        this.position = position;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public abstract void update();

}
