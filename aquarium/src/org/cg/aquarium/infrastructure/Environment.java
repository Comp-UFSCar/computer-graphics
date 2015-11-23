package org.cg.infrastructure;

import java.util.LinkedList;
import org.cg.solar.Body;

/**
 * Environment singleton for scene coordination.
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
     * Updates all components.
     *
     * Should be called when environmental changes happen, such as lighting or
     * camera movement.
     *
     * @return this.
     */
    public abstract Environment update();

    public Camera getCamera() {
        return camera;
    }

    public Lighting getLight() {
        return light;
    }

    public static Environment getEnvironment() {
        return environment;
    }
}
