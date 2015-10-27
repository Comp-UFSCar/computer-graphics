package org.CG.infrastructure.base;

import org.CG.infrastructure.structures.Vector;

/**
 *
 * @author ldavid
 */
public class Interactive {
    
    protected Vector location;
    
    public Interactive() {
        location = new Vector();
    }
    
    public void move(Vector v) {
        location = v;
    }
}
