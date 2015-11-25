package org.cg.aquarium.infrastructure.representations;

import java.util.Random;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.helpers.MathHelper;

/**
 * Represents an immutable RGBA color on the [0, 255] discrete spectrum.
 *
 * @author Diorge-Mephy
 */
public class Color {

    public final static Color RED = new Color(1, 0, 0);
    public final static Color GREEN = new Color(0, 1, 0);
    public final static Color BLUE = new Color(0, 0, 1);

    private final float red;

    private final float green;

    private final float blue;

    private final float alpha;

    /**
     * Instantiates a new Color with given parameters and alpha 255.
     *
     * @param red red component.
     * @param green green component.
     * @param blue blue component.
     */
    public Color(float red, float green, float blue) {
        this(red, green, blue, 255f);
    }

    /**
     * Instantiates a new Color with given parameters.
     *
     * Clamps the values to the valid spectrum.
     *
     * @param red red component
     * @param green green component
     * @param blue blue component
     * @param alpha alpha component (opacity)
     */
    public Color(float red, float green, float blue, float alpha) {
        this.red = MathHelper.clamp(0, 1, red);
        this.green = MathHelper.clamp(0, 1, green);
        this.blue = MathHelper.clamp(0, 1, blue);
        this.alpha = MathHelper.clamp(0, 1, alpha);
    }

    /**
     * Get the Red component.
     *
     * @return value of red component
     */
    public float getRed() {
        return red;
    }

    /**
     * Get the Green component.
     *
     * @return value of green component
     */
    public float getGreen() {
        return green;
    }

    /**
     * Get the Blue component.
     *
     * @return value of blue component
     */
    public float getBlue() {
        return blue;
    }

    /**
     * Get the Alpha component.
     *
     * @return value of alpha component
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * Instantiates a new color by adjusting the brightness of this color. This
     * is done by simply adding the given value to the three color components.
     *
     * @param value added to red, green and blue components
     * @return A new color with adjusted brightness
     */
    public Color adjustBrightness(float value) {
        return new Color(red + value, green + value, blue + value, alpha);
    }

    /**
     * Applies an intensity tone to the color.
     *
     * @param tone Tone intensity.
     * @return the multiplication of each color component by the tone.
     */
    public Color applyTone(float tone) {
        return new Color(tone * red, tone * green, tone * blue);
    }

    public static Color random() {
        return random(Environment.getEnvironment().getRandom());
    }

    /**
     * Instantiates a new random color.
     *
     * @param r random generator to be used.
     * @return the new random color.
     */
    public static Color random(Random r) {
        return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f, %.2f)", red, green, blue, alpha);
    }

}
