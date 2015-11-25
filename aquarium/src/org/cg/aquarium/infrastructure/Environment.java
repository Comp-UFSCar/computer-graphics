package org.cg.aquarium.infrastructure;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cg.aquarium.infrastructure.base.GraphicsMutator;

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
    protected Set<GraphicsMutator> changed;

    protected long refreshPeriod = 10;
    protected Thread time;
    protected Lock tickL;
    protected Lock changeL;

    protected Random random;

    protected long tick;
    private boolean debugging;

    protected class Tick extends Thread {

        public Tick() {
            super();
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                tickL.lock();
                try {
                    update();
                    tick++;

                    Thread.sleep(refreshPeriod);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                } finally {
                    tickL.unlock();
                }
            }
        }
    }

    protected Environment() {
        camera = new Camera();
        light = new Lighting();
        bodies = new LinkedList<>();
        changed = new HashSet<>();

        random = new Random();

        tickL = new ReentrantLock();
        changeL = new ReentrantLock();
        time = new Tick();
    }

    public void start() {
        time.start();
    }

    /**
     * Update all environment components.
     *
     * Should be called when environmental changes happen, such as lighting or
     * camera movement.
     */
    public abstract void update();

    public void notifyChanged(GraphicsMutator c) {
        changeL.lock();

        try {
            changed.add(c);
        } finally {
            changeL.unlock();
        }
    }

    public Set<GraphicsMutator> getAndCleanChanged() {
        Set<GraphicsMutator> c;

        changeL.lock();

        try {
            c = changed;
            changed = new HashSet<>();

        } finally {
            changeL.unlock();
        }

        return c;
    }

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

    public long getTick() {
        return tick;
    }

    public Random getRandom() {
        return random;
    }

    public long getRefreshPeriod() {
        return refreshPeriod;
    }

    public void setRefreshPeriod(long refreshPeriod) {
        this.refreshPeriod = refreshPeriod;
    }

    public boolean isDebugging() {
        return debugging;
    }

    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }
}
