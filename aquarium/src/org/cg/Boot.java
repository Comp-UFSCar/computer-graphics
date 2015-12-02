package org.cg;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.cg.aquarium.Aquarium;
import org.cg.aquarium.elements.Bubbles;
import org.cg.aquarium.elements.Seahorse;
import org.cg.aquarium.elements.Shark;
import org.cg.aquarium.elements.Shoal;
import org.cg.aquarium.infrastructure.KeyboardListener;
import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Bootstrap class for Aquarium.
 *
 * @author ldavid
 */
public class Boot {

    static final String TITLE = "Aquarium";
    static final boolean DEBUGGING = false;

    static final int FISH_COUNT = 100;
    static final int SHARK_COUNT = 3;
    static final int SEAHORSE_COUNT = 3;

    static final int BUBBLES_COUNT = 3;

    static Aquarium aquarium;

    public static void main(String[] args) {
        Frame frame = new Frame(TITLE);

        aquarium = Aquarium.getAquarium();
        aquarium.setDebugging(DEBUGGING);

        frame.add(aquarium.getCanvas());
        frame.setSize(aquarium.getCanvas().getWidth(),
                aquarium.getCanvas().getHeight());

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(() -> {
                    aquarium.getAnimator().stop();
                    System.exit(0);
                }).start();
            }
        });

        aquarium.getCanvas().addKeyListener(new KeyboardListener());

        Shoal s = new Shoal(FISH_COUNT);
        aquarium.addShoal(s);

        for (int i = 0; i < SHARK_COUNT; i++) {
            aquarium.addPredator(new Shark(
                    s, Vector.random().normalize(),
                    .5f, Vector.random().normalize().scale(20)
            ));
        }

        for (int i = 0; i < SEAHORSE_COUNT; i++) {
            aquarium.addToEcosystem(new Seahorse(
                    Vector.random().normalize(),
                    .1f,
                    Vector.random().normalize().scale(10)
            ));
        }

        for (int i = 0; i < BUBBLES_COUNT; i++) {
            aquarium.addToEcosystem(new Bubbles());
        }

        Debug.info(String.format("There are %d elements in ecosystem.",
                aquarium.getBodies().size()));

        aquarium.start();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
