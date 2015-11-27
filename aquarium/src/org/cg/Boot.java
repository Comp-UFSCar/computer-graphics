package org.cg;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.cg.aquarium.Aquarium;
import org.cg.aquarium.elements.Shark;
import org.cg.aquarium.elements.Shoal;
import org.cg.aquarium.infrastructure.KeyboardListener;
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
    static final boolean ADD_SHARK = true;

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
        aquarium.addToEcosystem(s);

        if (ADD_SHARK) {
            Shark shark = new Shark(
                    s, Vector.random().normalize(), .5f,
                    Vector.random().normalize().scale(20)
            );

            aquarium.addToEcosystem(shark);
            aquarium.setPredator(shark);
        }

        aquarium.start();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
