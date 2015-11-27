package org.cg.aquarium.elements;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import org.cg.aquarium.infrastructure.representations.Color;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class Shark extends Fish {

    public Shark(Shoal shoal) {
        super(shoal);
    }

    public Shark(Shoal shoal, Color color) {
        super(shoal, color);
    }

    public Shark(Shoal shoal, Vector direction, float speed) {
        this(shoal, Color.random(), direction, speed);
    }

    public Shark(Shoal shoal, Color color, Vector direction, float speed) {
        super(shoal, color, direction, speed);
    }

    public Shark(Shoal shoal, Vector direction, float speed, Vector position) {
        this(shoal, Color.random(), direction, speed, position);
    }

    public Shark(Shoal shoal, Color color, Vector direction, float speed, Vector position) {
        super(shoal, color, direction, speed, position);
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();
        gl.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
        gl.glTranslatef(position.getX(), position.getY(), position.getZ());
        glut.glutSolidSphere(5, 20, 20);
        gl.glPopMatrix();
    }

    @Override
    public void update() {
        Vector v = computeCohersion().scale(.4f)
                .add(computeSeparation().scale(.4f))
                .add(computeRandomness());

        setDirection(direction.add(v).normalize());
        move();
    }

}
