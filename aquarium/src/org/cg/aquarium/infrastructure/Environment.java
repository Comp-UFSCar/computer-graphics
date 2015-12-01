package org.cg.aquarium.infrastructure;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cg.aquarium.infrastructure.base.Visible;

/**
 * Environment for scene coordination.
 *
 * This class will most likely be extended by a singleton.
 *
 * @author ldavid
 */
public abstract class Environment {

    protected static Environment environment;
    public static final int THREADS = 6;

    protected Camera camera;
    protected Lighting light;
    protected LinkedList<Body> bodies;
    protected Set<Visible> changed;

    protected List<Tick> tickers;
    protected long refreshPeriod = 16;
    protected Lock changeL;

    protected Random random;

    private boolean debugging;

    /**
     * Tick.
     *
     * Thread Class helper for asynchronous bodies update.
     */
    protected class Tick extends Thread {

        public int tickid;

        public Tick(int tickId) {
            super();
            setDaemon(true);

            this.tickid = tickId;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    update(
                            this.tickid * bodies.size() / THREADS,
                            (this.tickid + 1) * bodies.size() / THREADS);

                    Thread.sleep(refreshPeriod);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }
        }
    }

    protected Environment() {
        camera = new Camera();
        light = new Lighting();
        bodies = new LinkedList<>();
        changed = new HashSet<>();

        tickers = new LinkedList<>();

        for (int i = 0; i < THREADS; i++) {
            tickers.add(new Tick(i));
        }

        changeL = new ReentrantLock();

        random = new Random();
    }

    /**
     * Start tickers.
     *
     * Adjacent threads will start executing, calling for the update methods of
     * the bodies contained in this environment.
     */
    public void start() {
        tickers.stream().forEach(t -> t.start());
    }
    
    /**
     * Update all environment components.
     *
     * Should be called when environmental changes happen, such as lighting or
     * camera movement.
     *
     * This method supports partial update, useful when using more than a single
     * thread. Use the {@code start} and {@code end} parameters to control the
     * interval that should be updated.
     *
     * @param start start of the interval that should be updated.
     * @param end end of the interval that should be updated.
     */
    public void update(int start, int end) {
        bodies.subList(start, end).forEach(b -> b.update());
    }

    /**
     * Notify change in component c.
     *
     * If {@code c} is a low priority component, rarely changes its state or is
     * simply costly to update, one might consider implementing the
     * {@code Visible} interface and using NotifyChanged to update it.
     *
     * Components passed as argument to this method will be updated ONCE and
     * removed from the updating queue.
     *
     * @param c
     */
    public void notifyChanged(Visible c) {
        changeL.lock();

        try {
            changed.add(c);
        } finally {
            changeL.unlock();
        }
    }

    public Set<Visible> getAndCleanChanged() {
        Set<Visible> c;

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
