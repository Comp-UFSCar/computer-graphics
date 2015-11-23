package org.cg.aquarium.infrastructure;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Environment for scene coordination.
 *
 * This class will most likely be extended by a singleton.
 *
 * @author ldavid
 */
public abstract class Environment {

    protected static Environment environment;

    protected Camera camera;
    protected Lighting light;
    protected LinkedList<Body> bodies;

    protected long refreshPeriod = 1;
    protected Thread time;
    protected Lock timeLock;

    protected Environment() {
        camera = new Camera();
        light = new Lighting();
        bodies = new LinkedList<>();

        this.timeLock = new ReentrantLock();

        time = new Thread(() -> {
            while (true) {
                timeLock.lock();
                try {
                    update();
                    Thread.sleep(refreshPeriod);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                } finally {
                    timeLock.unlock();
                }
            }
        });

        time.setDaemon(true);
        time.start();
    }

    /**
     * Update all environment components.
     *
     * Should be called when environmental changes happen, such as lighting or
     * camera movement.
     */
    public abstract void update();

    public Camera getCamera() {
        return camera;
    }

    public Lighting getLight() {
        return light;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public LinkedList<Body> getBodies() {
        return bodies;
    }

    public long getRefreshPeriod() {
        return refreshPeriod;
    }

    public void setRefreshPeriod(long refreshPeriod) {
        this.refreshPeriod = refreshPeriod;
    }
}
