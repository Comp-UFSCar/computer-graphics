package org.cg.aquarium.infrastructure;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.media.opengl.GLException;
import org.cg.aquarium.infrastructure.helpers.Debug;

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

    /**
     * Load a texture file and add it to the textures map.
     *
     * Assume name and paths are the same, and that the texture file format is
     * "png".
     *
     * @param name the name and path of a texture file.
     * @return Texture instance referencing the loaded data.
     */
    public Texture load(String name) {
        return load(name, name);
    }

    /**
     * Load a texture file and add it to the textures map.
     *
     * Assume that file format is "png".
     *
     * @param name the identifier associated to that texture.
     * @param path the full path to the texture file.
     * @return Texture instance referencing the loaded data.
     */
    public Texture load(String name, String path) {
        return load(name, path, "png");
    }

    /**
     * Load a texture file and add it to the textures map.
     *
     * @param name the identifier associated to that texture.
     * @param path the full path to the texture file.
     * @param format the format of the file. Default is "png".
     * @return a Texture instance referencing the loaded data or null, in any
     * errors occurred.
     */
    public Texture load(String name, String path, String format) {
        Texture t = textures.get(name);

        if (t == null) {
            try {
                InputStream stream = getClass().getResourceAsStream(path);
                TextureData data = TextureIO.newTextureData(stream, false, format);
                t = TextureIO.newTexture(data);
                textures.put(name, t);

            } catch (IOException | GLException | IllegalArgumentException ex) {
                Debug.error(String.format("Could not load texture {%s}: %s.",
                        path, ex.getLocalizedMessage()));
            }
        }

        return t;
    }

    /**
     * Get a texture that has already been loaded.
     *
     * If such texture wasn't loaded already, doesn't attempt to load it.
     *
     * @param name the name associated to the desired texture.
     * @return Texture previously loaded, or null, if it wasn't loaded.
     */
    public Texture getTexture(String name) {
        return textures.get(name);
    }

    public static TextureLoader getTextureLoader() {
        return instance = instance != null ? instance : new TextureLoader();
    }

}
