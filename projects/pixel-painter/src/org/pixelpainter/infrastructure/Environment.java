package org.pixelpainter.infrastructure;

import org.pixelpainter.editor.Editor;

/**
 * Environment singleton for Pixel Painter management.
 *
 * @author ldavid
 */
public class Environment {

    protected static Environment env;

    protected Camera camera;
    protected Lighting light;
    protected Editor editor;

    protected Environment() {
        camera = new Camera();
        light = new Lighting();
        editor = new Editor();
    }

    public static Environment getEnvironment() {
        if (env == null) {
            env = new Environment();
        }

        return env;
    }

    /**
     * Updates all components.
     * 
     * Should be called when environmental changes happen, such as lighting or camera movement.
     *
     * @return this.
     */
    public Environment update() {
        editor.update();
        
        return this;
    }

    public Camera getCamera() {
        return camera;
    }

    public Lighting getLight() {
        return light;
    }

    public Editor getEditor() {
        return editor;
    }
}
