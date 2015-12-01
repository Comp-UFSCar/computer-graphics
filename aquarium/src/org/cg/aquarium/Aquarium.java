package org.cg.aquarium;

import com.sun.opengl.util.Animator;
import java.util.LinkedList;
import java.util.List;
import javax.media.opengl.GLCapabilities;
import org.cg.aquarium.elements.Shoal;
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

    protected List<Shoal> shoals;
    protected List<Body> predators;

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

        shoals = new LinkedList<>();
        predators = new LinkedList<>();
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

    public void addToEcosystem(List<Body> bodies) {
        Debug.info("Adding all to the ecosystem: " + bodies.toString());
        this.bodies.addAll(bodies);
    }

    public void addToEcosystem(Body b) {
        Debug.info("Adding to the ecosystem: " + b.toString());
        bodies.add(b);
    }

    public boolean removeFromEcosystem() {
        if (bodies.isEmpty()) {
            return false;
        }

        return removeFromEcosystem(bodies.getFirst());
    }

    public boolean removeFromEcosystem(Body b) {
        return bodies.remove(b);
    }

    public void cleanEcosystem() {
        bodies.clear();
        predators.clear();
        shoals.clear();
    }

    public Collider getCollider() {
        return collider;
    }

    public boolean addPredator(Mobile predator) {
        boolean added = predators.add(predator);

        if (added) {
            addToEcosystem(predator);
        }

        return added;
    }

    public List<Body> getPredators() {
        return predators;
    }

    public Body getPredator(int index) {
        return predators.get(index);
    }

    public boolean addShoal(Shoal shoal) {
        boolean added = this.shoals.add(shoal);
        if (added) {
            this.addToEcosystem(shoal);
            this.addToEcosystem(shoal.getInnerShoal());
        }

        return added;
    }

    public List<Shoal> getShoals() {
        return shoals;
    }

    public Shoal getShoal(int index) {
        return shoals.get(index);
    }
}
