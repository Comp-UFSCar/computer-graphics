package org.cg.aquarium.elements;

import com.sun.opengl.util.GLUT;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import libs.modelparser.Material;
import libs.modelparser.Vertex;
import libs.modelparser.WavefrontObject;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.base.Body;
import org.cg.aquarium.infrastructure.base.Graphics;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Vegetation.
 *
 * @author ldavid
 */
public class Vegetation extends Body {

    protected Vector direction;
    protected Graphics graphics;

    public Vegetation() {
        super();
    }

    public Vegetation(Vector position) {
        super(position);
    }

    public Vegetation(Vector position, Vector direction) {
        super(position);
        this.direction = direction;
    }

    @Override
    public void initializeProperties() {
        Random r = Environment.getEnvironment().getRandom();

        direction = new Vector(
                (r.nextDouble() - .5) * 2,
                0,
                (r.nextDouble() - .5) * 2);

        size = new Vector(6, 6, 6);

        Material material = new Material("shark");
        material.setKa(new Vertex(.6f, .6f, 1));
        material.setKd(new Vertex(.6f, .6f, 1));
        material.setKs(new Vertex(.2f, .2f, .2f));
        material.setShininess(.3f);

        graphics = new Graphics(
                new WavefrontObject("/resources/vegetation/model.obj",
                        (float) size.getX(),
                        (float) size.getY(),
                        (float) size.getZ()),
                material,
                "/resources/vegetation/texture.png"
        );
    }

    @Override
    public void update() {
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();

        gl.glTranslated(position.getX(), position.getY(), position.getZ());

        graphics.glDefineObjectMaterial(gl);
        graphics.glAlignObjectWithVector(gl, direction, Vector.FORWARD);
        graphics.glRenderObject(gl);

        gl.glPopMatrix();
    }
}
