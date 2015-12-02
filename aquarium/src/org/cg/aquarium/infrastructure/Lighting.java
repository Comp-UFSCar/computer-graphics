package org.cg.aquarium.infrastructure;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.base.Visible;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Represents a basic lighting model (flat).
 *
 * @author diorge
 */
public class Lighting implements Visible {

    private Vector direction;

    private double intensity;

    private double ambientIntensity;

    private double ambientReflection;

    private double diffuseReflection;

    /**
     * Creates a new flat lighting model.
     *
     */
    public Lighting() {
        this(new Vector(1, 1, 0), .4f, .8f, .8f, .8f);
    }

    /**
     * Creates a new flat lighting model.
     *
     * @param direction direction of diffuse light.
     * @param intensity intensity of diffuse light.
     * @param ambientIntensity intensity of ambient light.
     * @param ambientReflection ambient reflection of the material.
     * @param diffuseReflection diffuse reflection of the material.
     */
    public Lighting(Vector direction, double intensity, double ambientIntensity,
            double ambientReflection, double diffuseReflection) {
        this.direction = direction.normalize();
        this.intensity = intensity;
        this.ambientIntensity = ambientIntensity;
        this.ambientReflection = ambientReflection;
        this.diffuseReflection = diffuseReflection;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        direction = direction.normalize();

        if (!direction.equals(this.direction)) {
            System.out.println(String.format("%s -> %s", this.direction,
                    direction));

            this.direction = direction;
            Environment.getEnvironment().notifyChanged(this);
        }
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        if (this.intensity != intensity) {
            this.intensity = intensity;
            Environment.getEnvironment().notifyChanged(this);
        }
    }

    public double getAmbientIntensity() {
        return ambientIntensity;
    }

    public void setAmbientIntensity(double ambientIntensity) {
        if (this.ambientIntensity != ambientIntensity) {
            this.ambientIntensity = ambientIntensity;
            Environment.getEnvironment().notifyChanged(this);
        }
    }

    public double getAmbientReflection() {
        return ambientReflection;
    }

    public void setAmbientReflection(double ambientReflection) {
        if (this.ambientReflection != ambientReflection) {
            this.ambientReflection = ambientReflection;
            Environment.getEnvironment().notifyChanged(this);
        }
    }

    public double getDiffuseReflection() {
        return diffuseReflection;
    }

    public void setDiffuseReflection(double diffuseReflection) {
        if (this.diffuseReflection != diffuseReflection) {
            this.diffuseReflection = diffuseReflection;
            Environment.getEnvironment().notifyChanged(this);
        }
    }

    /**
     * Gets the intensity in the flat model for a given face (by its normal).
     *
     * @param normal normal vector of the face.
     * @return the tone that should be used to fine the new color.
     */
    public double calculateLightingIntensity(Vector normal) {
        double cos = normal.normalize().dot(direction);

        return ambientIntensity * ambientReflection + intensity * diffuseReflection * cos;
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        float ambient[] = {0f, 0f, 0.4f, 0f};
        float diffuse[] = {0.8f, 0.8f, 1.0f, 0.0f};
        float specular[] = {0.8f, 0.8f, 1.0f, 1.0f};
        float position[] = {5.0f, 10.0f, 2.0f, 0.0f};
        float lmodel_ambient[] = {0.4f, 0.4f, 0.4f, 1.0f};
        float local_view[] = {0.0f};

        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0);
        gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient, 0);
        gl.glLightModelfv(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, local_view, 0);
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);

        gl.glEnable(GL.GL_COLOR_MATERIAL);
        gl.glShadeModel(GL.GL_SMOOTH);
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glClearColor(.7f, .7f, 1, 1);
    }

    public Lighting setup(GL gl) {
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LESS);

        return this;
    }

}
