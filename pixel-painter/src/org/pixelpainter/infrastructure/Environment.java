package org.pixelpainter.infrastructure;

import org.pixelpainter.editor.Editor;
import org.pixelpainter.infrastructure.representations.Lighting;

/**
 *
 * @author ldavid
 */
public class Environment {

    protected static Environment env;

    protected Camera mainCamera;
    protected Lighting mainLight;
    protected Editor editor;

    protected Environment() {
        mainCamera = new Camera();
        mainLight = new Lighting();
        editor = new Editor();
    }

    public static Environment getEnvironment() {
        if (env == null) {
            env = new Environment();
        }

        return env;
    }

    public Camera getCamera() {
        return mainCamera;
    }

    public Lighting getMainLight() {
        return mainLight;
    }

    public Editor getEditor() {
        return editor;
    }
}
