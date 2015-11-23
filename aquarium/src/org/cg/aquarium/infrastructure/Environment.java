package org.cg.aquarium.infrastructure;

import java.util.LinkedList;

/**
 * Environment for scene coordination.
 * 
 * This class will most likely be extended by a singleton.
 *
 * @author ldavid
 */
public abstract class Environment {

    protected static Environment environment;

    protected Camera camera;
    protected Lighting light;
    protected LinkedList<Body> bodies;

    protected Environment() {
        camera = new Camera();
        light = new Lighting();
        bodies = new LinkedList<>();
    }
    
    /**
     * Update all environment components.
     * 
     * Should be called when environmental changes happen, such as lighting or
     * camera movement.
     */
    public abstract void update();

    public Camera getCamera() {
        return camera;
    }

    public Lighting getLight() {
        return light;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public LinkedList<Body> getBodies() {
        return bodies;
    }
}
