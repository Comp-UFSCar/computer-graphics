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

    public static float MAXIMUM_DISTANCE = 100000;
    public static float RANDOMNESS = .01f;

    protected Graphics graphics;

    protected Vector lairLocation;

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
        size = new Vector(1, 1, 1);
        lairLocation = Vector.random().normalize().scale(40);

        Material material = new Material("base");
        material.setKa(new Vertex(.6f, .6f, 1));
        material.setKd(new Vertex(.6f, .6f, 1));
        material.setKs(new Vertex(.2f, .2f, .2f));
        material.setShininess(30);

        graphics = new Graphics(new WavefrontObject("seahorse.obj",
                (float) size.getX(), (float) size.getY(),
                (float) size.getZ()),
                material);
    }

    @Override
    public void update() {
        double d = position.squareDistance(lairLocation);

        setDirection(direction.scale((MAXIMUM_DISTANCE - d) / MAXIMUM_DISTANCE)
                .add(lairLocation.delta(position).normalize().scale(d / MAXIMUM_DISTANCE))
                .add(Vector.random().normalize().scale(RANDOMNESS))
        );

        move();
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();

        gl.glTranslated(position.getX(), position.getY(), position.getZ());

        graphics.glDefineObjectMaterial(gl);
        graphics.glAlignObjectWithVector(gl, direction, Vector.LEFT);
        graphics.glRenderObject(gl);

        graphics.glDebugPlotVector(gl, direction.scale(20 * speed));

        gl.glPopMatrix();
    }

}
