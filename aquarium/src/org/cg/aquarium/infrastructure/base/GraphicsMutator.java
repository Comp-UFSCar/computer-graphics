package org.cg.aquarium.infrastructure.base;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/**
 * Graphics mutator.
 *
 * Objects of subclasses can mutate and need to be updated. Differently from
 * {@code Body}, these mutations don't happen every tick and will only take
 * effect once {@code Environment.getEnvironment().addChanged(this)} is called.
 *
 * @author ldavid
 */
public interface GraphicsMutator {

    /**
     * Process changes for mutator.
     *
     * Once a object is added to Environment.changed set, {@code processChanges}
     * will eventually be called.
     *
     * @param gl the OpenGL object associated.
     * @param glu the GLU object associated.
     */
    public void processChanges(GL gl, GLU glu);
}
