package org.pixelpainter.infrastructure.representations;

/**
 * Represents a basic lighting model (flat).
 *
 * @author diorge
 */
public class Lighting {

    private Vector direction;

    private float intensity;

    private float ambientIntensity;

    private float ambientReflection;

    private float diffuseReflection;

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
    public Lighting(Vector direction, float intensity, float ambientIntensity, float ambientReflection, float diffuseReflection) {
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

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getAmbientIntensity() {
        return ambientIntensity;
    }

    public void setAmbientIntensity(float ambientIntensity) {
        this.ambientIntensity = ambientIntensity;
    }

    public float getAmbientReflection() {
        return ambientReflection;
    }

    public void setAmbientReflection(float ambientReflection) {
        this.ambientReflection = ambientReflection;
    }

    public float getDiffuseReflection() {
        return diffuseReflection;
    }

    public void setDiffuseReflection(float diffuseReflection) {
        this.diffuseReflection = diffuseReflection;
    }

    /**
     * Gets the intensity in the flat model for a given face (by its normal).
     *
     * @param normal normal vector of the face.
     * @return the tone that should be used to fine the new color.
     */
    public float calculateLightingIntensity(Vector normal) {
        float cos = normal.normalize().dot(direction);

        return ambientIntensity * ambientReflection + intensity * diffuseReflection * cos;
    }

}
