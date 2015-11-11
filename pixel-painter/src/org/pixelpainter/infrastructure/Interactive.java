package org.CG.infrastructure;

import org.CG.infrastructure.abstractions.Vector;

/**
 *
 * @author ldavid
 */
public interface Interactive {

    public Drawing moveTo(Vector v);

    public Drawing move(Vector v);
}
