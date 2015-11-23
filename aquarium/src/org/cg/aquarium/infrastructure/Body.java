package org.cg.aquarium.infrastructure;

import org.cg.aquarium.infrastructure.base.Visible;

/**
 * Body base class.
 *
 * Subclasses of Body are elements of an environment.
 *
 * @author ldavid
 */
public abstract class Body implements Visible {

    public abstract void update();

}
