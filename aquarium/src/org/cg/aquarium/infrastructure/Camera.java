package org.cg.aquarium.infrastructure;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.base.Interactive;
import org.cg.aquarium.infrastructure.representations.Vector;
import org.cg.aquarium.infrastructure.base.GraphicsMutator;

/**
 * Camera in the environment.
 *
 * Class used by Environment to calculate projections.
 *
 * @author ldavid
 */
public class Camera implements Interactive, GraphicsMutator {

    Vector position;
    Vector lookAt;
    Vector up;

    public Camera() {
        this(new Vector(0, 0, 100), Vector.ORIGIN, new Vector(0, 1, 0));
    }

    public Camera(Vector position, Vector lookAt, Vector up) {
        this.position = position;
        this.lookAt = lookAt;
        this.up = up;
    }

    public Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getLookAt() {
        return this.lookAt;
    }

    public void setLookAt(Vector lookAt) {
        this.lookAt = lookAt;
    }

    public Vector getUp() {
        return this.up;
    }

    public void setUp(Vector up) {
        this.up = up;
    }

    @Override
    public void moveTo(Vector v) {
        setPosition(v);
    }

    @Override
    public void move(Vector v) {
        moveTo(position.add(v));
        Environment.getEnvironment().notifyChanged(this);
    }

    @Override
    public void processChanges(GL gl, GLU glu) {
        if (Environment.getEnvironment().isDebugging()) {
            System.out.println("Process changes on camera: " + position.toString());
        }

        glu.gluLookAt(position.getX(),
                position.getY(),
                position.getZ(),
                lookAt.getX(),
                lookAt.getY(),
                lookAt.getZ(),
                up.getX(),
                up.getY(),
                up.getZ());
    }
}
