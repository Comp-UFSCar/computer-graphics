package org.cg;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.cg.aquarium.Aquarium;
import org.cg.aquarium.bodies.Shoal;
import org.cg.aquarium.infrastructure.KeyboardListener;

/**
 * Bootstrap class for Aquarium.
 *
 * @author ldavid
 */
public class Boot {

    static final String TITLE = "Aquarium";
    static final boolean DEBUGGING = true;

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

        aquarium.addToEcosystem(new Shoal(90));
        aquarium.start();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
