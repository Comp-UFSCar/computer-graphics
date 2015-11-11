package org.pixelpainter.infrastructure.representations;

import java.util.Random;
import org.pixelpainter.infrastructure.helpers.MathHelper;

/**
 * Represents an immutable RGBA color on the [0, 255] discrete spectrum.
 *
 * @author Diorge-Mephy
 */
public class Color {

    private final byte red;

    private final byte green;

    private final byte blue;

    private final byte alpha;

    /**
     * Instantiates a new Color with given parameters and alpha 255.
     *
     * @param red red component varying in the interval [0, 1].
     * @param green green component varying in the interval [0, 1].
     * @param blue blue component varying in the interval [0, 1].
     */
    public Color(float red, float green, float blue) {
        this(
                (byte) (MathHelper.clamp(0, 1, red) * 255),
                (byte) (MathHelper.clamp(0, 1, green) * 255),
                (byte) (MathHelper.clamp(0, 1, blue) * 255));
    }

    /**
     * Instantiates a new Color with given parameters and alpha 255.
     *
     * @param red red component
     * @param green green component
     * @param blue blue component
     */
    public Color(byte red, byte green, byte blue) {
        this(red, green, blue, (byte) 255);
    }

    /**
     * Instantiates a new Color with given parameters. Clamps the values to the valid spectrum.
     *
     * @param red red component
     * @param green green component
     * @param blue blue component
     * @param alpha alpha component (opacity)
     */
    public Color(byte red, byte green, byte blue, byte alpha) {
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
     * Instantiates a new color by adjusting the brightness of this color. This is done by simply adding the given value
     * to the three color components.
     *
     * @param value added to red, green and blue components
     * @return A new color with adjusted brightness
     */
    public Color adjustBrightness(int value) {
        return new Color(
                (byte) (red + value),
                (byte) (green + value),
                (byte) (blue + value),
                alpha
        );
    }

    /**
     * Applies an intensity tone to the color.
     *
     * @param tone Tone intensity.
     * @return the multiplication of each color component by the tone.
     */
    public Color applyTone(double tone) {
        return new Color((byte) (tone * red), (byte) (tone * green), (byte) (tone * blue));
    }

    /**
     * Instantiates a new random color.
     *
     * @param randGen random generator to be used
     * @return A new color with random red, green and blue values, and alpha 255
     */
    public static Color random(Random randGen) {
        byte[] values = new byte[3];
        randGen.nextBytes(values);
        return new Color(values[0], values[1], values[2]);
    }

}
