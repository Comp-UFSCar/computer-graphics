package org.cg.aquarium.infrastructure;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class KeyboardListener implements KeyListener {

    char CAMERA_FORWARD = 'w', CAMERA_BACKWARD = 's',
            CAMERA_LEFT = 'a', CAMERA_RIGHT = 'd',
            CAMERA_UP = 'q', CAMERA_DOWN = 'e';

    public KeyboardListener() {
        super();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();

        Vector v = Vector.ZERO;

        if (key == CAMERA_FORWARD) {
            v = new Vector(0, 0, 1);
        } else if (key == CAMERA_BACKWARD) {
            v = new Vector(1, 0, -1);
        } else if (key == CAMERA_LEFT) {
            v = new Vector(-1, 0, 0);
        } else if (key == CAMERA_RIGHT) {
            v = new Vector(1, 0, 0);
        } else if (key == CAMERA_UP) {
            v = new Vector(0, 1, 0);
        } else if (key == CAMERA_DOWN) {
            v = new Vector(0, -1, 0);
        }

        if (v != Vector.ZERO) {
            Environment.getEnvironment().getCamera().move(v);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
