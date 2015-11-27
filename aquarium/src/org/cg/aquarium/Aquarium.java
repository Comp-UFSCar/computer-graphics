package org.cg.aquarium;

import com.sun.opengl.util.Animator;
import javax.media.opengl.GLCapabilities;
import org.cg.aquarium.elements.Shark;
import org.cg.aquarium.infrastructure.AquariumCanvas;
import org.cg.aquarium.infrastructure.Body;
import org.cg.aquarium.infrastructure.colliders.Collider;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.colliders.BoxCollider;
import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Aquarium singleton for scene coordination.
 *
 * @author ldavid
 */
public class Aquarium extends Environment {

    protected final AquariumCanvas canvas;
    protected final Animator animator;
    protected final Collider collider;
    protected Mobile predator;

    protected Aquarium() {
        super();

        collider = new BoxCollider(
                Vector.ZERO, new Vector(100, 100, 100)
        );

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
        Debug.info("Populating ecosystem...");

        tickL.lock();
        try {
            bodies.add(b);
        } finally {
            tickL.unlock();
        }

        Debug.info("Ecosystem population complete.");
    }

    public boolean removeFromEcosystem() {
        if (bodies.isEmpty()) {
            return false;
        }

        return removeFromEcosystem(bodies.getFirst());
    }

    public boolean removeFromEcosystem(Body b) {
        boolean removed;
        tickL.lock();

        try {
            removed = bodies.remove(b);
        } finally {
            tickL.unlock();
        }

        return removed;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setPredator(Mobile predator) {
        this.predator = predator;
    }

    public Mobile getPredator() {
        return predator;
    }
}
