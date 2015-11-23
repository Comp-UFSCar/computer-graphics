package org.cg.aquarium;

import com.sun.opengl.util.Animator;
import javax.media.opengl.GLCapabilities;
import org.cg.infrastructure.AquariumCanvas;
import org.cg.infrastructure.Environment;

/**
 * Aquarium singleton for scene coordination.
 *
 * @author ldavid
 */
public class Aquarium extends Environment {

    protected final AquariumCanvas canvas;
    protected final Animator animator;

    protected Aquarium() {
        super();

        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);

        canvas = new AquariumCanvas(1366, 768, capabilities);
        canvas.addGLEventListener(canvas);

        animator = new Animator(canvas);
        animator.start();
    }

    @Override
    public Environment update() {
        return this;
    }

    public AquariumCanvas getCanvas() {
        return canvas;
    }

    public Animator getAnimator() {
        return animator;
    }

    public static Aquarium getAquarium() {
        if (environment == null) {
            environment = new Aquarium();
        }

        return (Aquarium) environment;
    }

}
