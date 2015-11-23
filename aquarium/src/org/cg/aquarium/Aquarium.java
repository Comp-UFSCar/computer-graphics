package org.cg.aquarium;

import com.sun.opengl.util.Animator;
import javax.media.opengl.GLCapabilities;
import org.cg.aquarium.infrastructure.AquariumCanvas;
import org.cg.aquarium.infrastructure.Body;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.representations.Vector;

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

        camera.setPosition(new Vector(0, 0, 100));
        camera.setLookAt(new Vector(0, 0, 0));
        camera.setUp(new Vector(0, 1, 0));

        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);

        canvas = new AquariumCanvas(capabilities);
        canvas.addGLEventListener(canvas);

        animator = new Animator(canvas);
        animator.start();
    }

    @Override
    public void update() {
        bodies.stream().forEach(b -> b.update());
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

    public void addToEcosystem(Body b) {
        timeLock.lock();
        try {
            bodies.add(b);
        } finally {
            timeLock.unlock();
        }
    }

    public boolean removeFromEcosystem() {
        if (bodies.isEmpty()) {
            return false;
        }

        return removeFromEcosystem(bodies.getFirst());
    }

    public boolean removeFromEcosystem(Body b) {
        boolean removed;
        timeLock.lock();

        try {
            removed = bodies.remove(b);
        } finally {
            timeLock.unlock();
        }

        return removed;
    }
}
