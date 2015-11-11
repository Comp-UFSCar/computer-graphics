package org.CG.infrastructure.abstractions;

/**
 * Represents a basic lighting model (flat).
 *
 * @author diorge
 */
public class Lighting {

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
        this(Vector.DOWN, 1, 1, 1, 1);
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
    public Lighting(Vector direction, double intensity, double ambientIntensity, double ambientReflection, double diffuseReflection) {
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
        this.direction = direction.normalize();
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public double getAmbientIntensity() {
        return ambientIntensity;
    }

    public void setAmbientIntensity(double ambientIntensity) {
        this.ambientIntensity = ambientIntensity;
    }

    public double getAmbientReflection() {
        return ambientReflection;
    }

    public void setAmbientReflection(double ambientReflection) {
        this.ambientReflection = ambientReflection;
    }

    public double getDiffuseReflection() {
        return diffuseReflection;
    }

    public void setDiffuseReflection(double diffuseReflection) {
        this.diffuseReflection = diffuseReflection;
    }

    /**
     * Gets the intensity in the flat model for a given face (by its normal).
     *
     * @param normal normal vector of the face.
     * @return the tone that should be used to fine the new color.
     */
    public double calculateLightingIntensity(Vector normal) {
        double cos = normal.normalize().dot(getDirection());
        return getAmbientIntensity() * getAmbientReflection()
                + getIntensity() * getDiffuseReflection() * cos;
    }

}
