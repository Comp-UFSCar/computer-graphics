package org.CG.infrastructure;

import javax.media.opengl.glu.GLU;
import org.CG.infrastructure.abstractions.Vector;

/**
 *
 * @author ldavid
 */
public class Camera {

    Vector position;
    Vector lookAt;
    Vector up;

    static Camera mainCamera;

    public Camera() {
        this(new Vector(0, 0, -1000), Vector.ORIGIN, new Vector(0, 1, 0));
    }

    public Camera(Vector position, Vector lookAt, Vector up) {
        this.position = position;
        this.lookAt = lookAt;
        this.up = up;
    }

    public static Camera getMainCamera() {
        return mainCamera;
    }

    public static void setMainCamera(Camera mainCamera) {
        Camera.mainCamera = mainCamera;
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

    public void adjustCameraOnScene(GLU glu) {
        glu.gluLookAt(position.getX(),
                position.getY(),
                position.getZ(),
                position.getX(),
                lookAt.getY(),
                lookAt.getZ(),
                up.getX(),
                up.getY(),
                up.getZ());
    }
}
