package org.CG.infrastructure.structures;

import java.util.Random;
import org.CG.infrastructure.helpers.MathHelper;

/**
 * Represents an immutable RGBA color on the [0, 255] discrete spectrum.
 *
 * @author Diorge-Mephy
 */
public class ColorByte {

    private final byte red;

    private final byte green;

    private final byte blue;

    private final byte alpha;
    
    public ColorByte() {
        this(0, 0, 0, 255);
    }

    /**
     * Instantiates a new Color with given parameters and alpha 255. Clamps the
     * values to the valid spectrum.
     *
     * @param red red component
     * @param green green component
     * @param blue blue component
     */
    public ColorByte(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    /**
     * Instantiates a new Color with given parameters. Clamps the values to the
     * valid spectrum.
     *
     * @param red red component
     * @param green green component
     * @param blue blue component
     * @param alpha alpha component (opacity)
     */
    public ColorByte(int red, int green, int blue, int alpha) {
        this.red = (byte) MathHelper.clamp(0, 255, red);
        this.green = (byte) MathHelper.clamp(0, 255, green);
        this.blue = (byte) MathHelper.clamp(0, 255, blue);
        this.alpha = (byte) MathHelper.clamp(0, 255, alpha);
    }

    /**
     * Get the Red component.
     *
     * @return value of red component
     */
    public byte getRed() {
        return red;
    }

    /**
     * Get the Green component.
     *
     * @return value of green component
     */
    public byte getGreen() {
        return green;
    }

    /**
     * Get the Blue component.
     *
     * @return value of blue component
     */
    public byte getBlue() {
        return blue;
    }

    /**
     * Get the Alpha component.
     *
     * @return value of alpha component
     */
    public byte getAlpha() {
        return alpha;
    }

    /**
     * Instantiates a new color by adjusting the brightness of this color. This
     * is done by simply adding the given value to the three color components.
     *
     * @param value added to red, green and blue components
     * @return A new color with adjusted brightness
     */
    public ColorByte adjustBrightness(int value) {
        return new ColorByte(
                getRed() + value,
                getGreen() + value,
                getBlue() + value,
                getAlpha()
        );
    }

    /**
     * Converts the color to the [0, 1] spectrum.
     *
     * @return a new ColorByte equivalent to this one
     */
    public ColorFloat toColorFloat() {
        return new ColorFloat(
                (int) MathHelper.normalize(0, 255, red),
                (int) MathHelper.normalize(0, 255, green),
                (int) MathHelper.normalize(0, 255, blue),
                (int) MathHelper.normalize(0, 255, alpha)
        );
    }

    /**
     * Instantiates a new random color.
     *
     * @param randGen random generator to be used
     * @return A new color with random red, green and blue values, and alpha 255
     */
    public static ColorByte random(Random randGen) {
        byte[] values = new byte[3];
        randGen.nextBytes(values);
        return new ColorByte(values[0], values[1], values[2]);
    }

}
