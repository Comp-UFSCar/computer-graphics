package org.cg.aquarium.elements;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import libs.modelparser.Material;
import libs.modelparser.Vertex;
import libs.modelparser.WavefrontObject;
import org.cg.aquarium.infrastructure.helpers.Debug;
import org.cg.aquarium.infrastructure.helpers.MathHelper;
import org.cg.aquarium.infrastructure.representations.Color;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class Shark extends Fish {

    private static WavefrontObject model;
    private static Material material;

    protected static void initializeShark() {
        if (model == null) {
            model = new WavefrontObject("Shark.obj", 6, 6, 6);

            material = new Material("shark");
            material.setKa(new Vertex(.6f, .6f, 1));
            material.setKd(new Vertex(.6f, .6f, 1));
            material.setKs(new Vertex(.2f, .2f, .2f));
            material.setShininess(.3f);
        }
    }

    public static float MAXIMUM_DISTANCE = 100000;
    public static float MOMENTUM = .2f;

    public Shark(Shoal shoal) {
        super(shoal);
        initializeShark();
    }

    public Shark(Shoal shoal, Color color) {
        super(shoal, color);
        initializeShark();
    }

    public Shark(Shoal shoal, Vector direction, float speed) {
        this(shoal, Color.random(), direction, speed);
    }

    public Shark(Shoal shoal, Color color, Vector direction, float speed) {
        super(shoal, color, direction, speed);
        initializeShark();
    }

    public Shark(Shoal shoal, Vector direction, float speed, Vector position) {
        this(shoal, Color.random(), direction, speed, position);
    }

    public Shark(Shoal shoal, Color color, Vector direction, float speed, Vector position) {
        super(shoal, color, direction, speed, position);
        initializeShark();
    }

    @Override
    public void update() {
        float d = position.squareDistance(shoal.getPosition());

        Debug.info("Shark-shoal delta: " + d);

        setDirection(direction.scale((MAXIMUM_DISTANCE - d) / MAXIMUM_DISTANCE)
                .add(shoal.getPosition().delta(position).normalize().scale(d / MAXIMUM_DISTANCE))
                .add(Vector.random().normalize().scale(RANDOMNESS))
        );
        move();
    }

    @Override
    public void display(GL gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();
        setMaterial(material, gl);

        float cos = Vector.FORWARD.dot(new Vector(
                direction.getX(), 0, direction.getZ()).normalize());

        gl.glTranslatef(position.getX(), position.getY(), position.getZ());
        gl.glRotated(
                -Math.signum(direction.getX())
                * MathHelper.radiansToDegree(Math.acos(cos)), 0, 1, 0);

        model.getGroups().stream().forEach((g) -> {
            g.getFaces().stream().forEach((f) -> {
                gl.glBegin(GL.GL_TRIANGLES);

                for (Vertex v : f.getVertices()) {
                    gl.glVertex3d(v.getX(), v.getY(), v.getZ());
                }

                for (Vertex v : f.getNormals()) {
                    gl.glVertex3d(v.getX(), v.getY(), v.getZ());
                }

                gl.glEnd();
            });
        });

        debugDisplayDirectionVector(gl, glu, glut);
        gl.glPopMatrix();
    }

    protected void setMaterial(Material mat, GL gl) {
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, new float[]{mat.getKa().getX(), mat.getKa().getY(), mat.getKa().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, new float[]{mat.getKd().getX(), mat.getKd().getY(), mat.getKd().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, new float[]{mat.getKs().getX(), mat.getKs().getY(), mat.getKs().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, new float[]{mat.getShininess(), 0, 0, 0}, 0);
    }

}
