package org.cg.aquarium.elements;

import libs.modelparser.Material;
import libs.modelparser.Vertex;
import libs.modelparser.WavefrontObject;
import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.base.Mobile;
import org.cg.aquarium.infrastructure.base.Graphics;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class Seahorse extends Mobile {

    public static float DISTANCE_FROM_CENTER = 100000;
    public static float RANDOMNESS = .2f;

    protected Graphics graphics;

    protected Vector spawn;

    public Seahorse() {
        super();
    }

    public Seahorse(Vector direction, float speed) {
        super(direction, speed);
    }

    public Seahorse(Vector direction, float speed, Vector position) {
        super(direction, speed, position);
    }

    @Override
    public void initializeProperties() {
        size = new Vector(2, 2, 2);
        spawn = Vector.random().normalize().scale(20);

        Material material = new Material("base");
        material.setKa(new Vertex(.6f, .6f, 1));
        material.setKd(new Vertex(.6f, .6f, 1));
        material.setKs(new Vertex(.2f, .2f, .2f));
        material.setShininess(30);

        graphics = new Graphics(new WavefrontObject("seahorse.obj", 2, 2, 2),
                material);
    }

    @Override
    public void update() {
        float distanceFromOrigin = position.dot(position);
        setDirection(
                direction.scale(
                        (DISTANCE_FROM_CENTER - distanceFromOrigin) / DISTANCE_FROM_CENTER).add(
                        position.scale(RANDOMNESS - 1)
                        .add(Vector.random().normalize().scale(RANDOMNESS))
                        .scale(distanceFromOrigin / DISTANCE_FROM_CENTER)));

        move();
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();

        gl.glTranslatef(position.getX(), position.getY(), position.getZ());

        graphics.glDefineMaterial(gl);
        graphics.glAlignDirection(gl, direction, Vector.LEFT);
        graphics.glRender(gl);

        debugDisplayDirectionVector(gl, glu, glut);

        gl.glPopMatrix();
    }

}
