package org.CG.editor;

import javax.media.opengl.glu.GLU;
import org.CG.infrastructure.abstractions.Point;

/**
 *
 * @author ldavid
 */
public class Camera {

    Point position;
    Point lookAt;
    Point up;

    static Camera mainCamera;

    public Camera() {
        this(new Point((double) 0, (double) 0, (double) 10), Point.ORIGIN, new Point((double) 0, (double) 1, (double) 0));
    }

    public Camera(Point position, Point lookAt, Point up) {
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

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getLookAt() {
        return this.lookAt;
    }

    public void setLookAt(Point lookAt) {
        this.lookAt = lookAt;
    }

    public Point getUp() {
        return this.up;
    }

    public void setUp(Point up) {
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
