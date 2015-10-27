package org.CG.infrastructure.structures;

import java.util.Random;
import org.CG.infrastructure.helpers.MathHelper;

/**
 * Represents an immutable RGBA color on the [0, 1] continuous spectrum.
 *
 * @author Diorge-Mephy
 */
public final class ColorFloat {

    private final float red;

    private final float green;

    private final float blue;

    private final float alpha;

    /**
     * Instantiates a new Color with given parameters and alpha 1.0. Clamps the
     * values to the valid spectrum.
     *
     * @param red red component
     * @param green green component
     * @param blue blue component
     */
    public ColorFloat(float red, float green, float blue) {
        this(red, green, blue, 1f);
    }

    /**
     * Instantiates a new Color with given parameters Clamps the values to the
     * valid spectrum.
     *
     * @param red red component
     * @param green green component
     * @param blue blue component
     * @param alpha alpha component (opacity)
     */
    public ColorFloat(float red, float green, float blue, float alpha) {
        this.red = MathHelper.clamp(0f, 1f, red);
        this.green = MathHelper.clamp(0f, 1f, green);
        this.blue = MathHelper.clamp(0f, 1f, blue);
        this.alpha = MathHelper.clamp(0f, 1f, alpha);
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
    public ColorFloat adjustBrightness(float value) {
        return new ColorFloat(
                getRed() + value,
                getGreen() + value,
                getBlue() + value,
                getAlpha()
        );
    }

    /**
     * Converts the color to the [0, 255] discrete spectrum.
     *
     * @return a new ColorByte equivalent to this one
     */
    public ColorByte toColorByte() {
        return new ColorByte(
                (int) MathHelper.lerp(0, 255, red),
                (int) MathHelper.lerp(0, 255, green),
                (int) MathHelper.lerp(0, 255, blue),
                (int) MathHelper.lerp(0, 255, alpha)
        );
    }

    /**
     * Instantiates a new random color.
     *
     * @param randGen random generator to be used.
     * @return A new color with random red, green and blue values, and alpha 1.
     */
    public static ColorFloat random(Random randGen) {
        return new ColorFloat(
                randGen.nextFloat(),
                randGen.nextFloat(),
                randGen.nextFloat()
        );
    }

}
