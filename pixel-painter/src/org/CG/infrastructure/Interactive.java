package org.CG.infrastructure;

import org.CG.infrastructure.abstractions.Vector3;

/**
 *
 * @author ldavid
 */
public interface Interactive {

    public Drawing moveTo(Vector3 v);

    public Drawing move(Vector3 v);
}
