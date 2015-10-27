package org.CG.editor;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.CG.infrastructure.base.Interactive;
import org.CG.infrastructure.structures.Vector;

/**
 *
 * @author ldavid
 */
public class Camera extends Interactive {

    Vector center, up;
    private final GL gl;
    private final GLU glu;

    public Camera(GL gl, GLU glu) {
        super();
        
        this.gl = gl;
        this.glu = glu;

        location = new Vector(-3, 2, 10);
        center = new Vector(0, 0, 0);
        up = new Vector(0, 1, 0);
    }

    public Camera syncGraphics() {
        gl.glLoadIdentity();
        glu.gluLookAt(
            location.getX(), location.getY(), location.getZ(),
            center.getX(), center.getY(), center.getZ(),
            up.getX(), up.getY(), up.getZ());

        return this;
    }

    @Override
    public void move(Vector v) {
        location = location.add(v);
        center = center.add(v);
    }
}
