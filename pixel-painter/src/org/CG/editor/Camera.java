package org.CG.editor;

import javax.media.opengl.glu.GLU;
import org.CG.infrastructure.abstractions.Vector3;

/**
 *
 * @author ldavid
 */
public class Camera {

    Vector3 position;
    Vector3 lookAt;
    Vector3 up;

    static Camera mainCamera;

    public Camera() {
        this(new Vector3(0, 0, -1000), Vector3.ORIGIN, new Vector3(0, 1, 0));
    }

    public Camera(Vector3 position, Vector3 lookAt, Vector3 up) {
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

    public Vector3 getPosition() {
        return this.position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getLookAt() {
        return this.lookAt;
    }

    public void setLookAt(Vector3 lookAt) {
        this.lookAt = lookAt;
    }

    public Vector3 getUp() {
        return this.up;
    }

    public void setUp(Vector3 up) {
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
