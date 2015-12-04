package org.cg.aquarium.infrastructure;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.media.opengl.GLException;

/**
 * Texture Loader.
 *
 * Loads textures once and keep them in memory to avoid repetition in
 * Environment elements.
 *
 * @author ldavid
 */
public class TextureLoader {

    protected static TextureLoader instance;

    HashMap<String, Texture> textures;

    protected TextureLoader() {
        textures = new HashMap<>();
    }

    public Texture load(String name) {
        return load(name, name);
    }

    public Texture load(String name, String path) {
        return load(name, path, "png");
    }

    public Texture load(String name, String path, String format) {
        Texture t = textures.get(name);

        if (t == null) {
            try {
                InputStream stream = getClass().getResourceAsStream(path);
                TextureData data = TextureIO.newTextureData(stream, false, format);
                t = TextureIO.newTexture(data);
                textures.put(name, t);

            } catch (IOException | GLException | IllegalArgumentException ex) {
                System.err.println(String.format("Could not load texture {%s}.",
                        path));
            }
        }

        return t;
    }

    public static TextureLoader getTextureLoader() {
        return instance = instance != null ? instance : new TextureLoader();
    }

}
