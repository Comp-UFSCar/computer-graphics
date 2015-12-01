package org.cg.aquarium.infrastructure.base;

import org.cg.aquarium.infrastructure.Body;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Mobile.
 *
 * Besides localization, subclasses will also have the capacity to move in the
 * 3-dimensional space.
 *
 * @author ldavid
 */
public abstract class Mobile extends Body {

    protected Vector direction;
    protected float speed;

    public Mobile() {
        super();

        setDirection(Vector.RIGHT);
        setSpeed(.1f);
    }

    public Mobile(Vector direction, float speed) {
        super();

        setDirection(direction);
        setSpeed(speed);
    }

    public Mobile(Vector direction, float speed, Vector position) {
        super(position);

        setDirection(direction);
        setSpeed(speed);
    }

    public void move() {
        setPosition(position.add(direction.scale(speed)));
    }

    public void move(Vector direction) {
        setDirection(direction);
        move();
    }

    public void setDirection(Vector direction) {
        this.direction = direction.normalize();
    }

    public Vector getDirection() {
        return direction;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
