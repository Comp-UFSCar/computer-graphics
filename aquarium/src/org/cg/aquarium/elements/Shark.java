package org.cg.aquarium.elements;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import libs.modelparser.Material;
import libs.modelparser.Vertex;
import libs.modelparser.WavefrontObject;
import org.cg.aquarium.infrastructure.representations.Color;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 *
 * @author ldavid
 */
public class Shark extends Fish {
    
    private static WavefrontObject wavefrontObject;
    private static Material material;
    
    protected static void initializeShark(){
        if(wavefrontObject == null) {
            wavefrontObject = new WavefrontObject("Shark.obj", 10, 10, 10);
            
            material = new Material("shark");
            material.setKa(new Vertex(.6f, .6f, 1));
            material.setKd(new Vertex(.6f, .6f, 1));
            material.setKs(new Vertex(.2f, .2f, .2f));
            material.setShininess(.3f);
        }
    }

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
    public void display(GL gl, GLU glu, GLUT glut) {
        setMaterial(material, gl);

        gl.glPushMatrix();

        gl.glTranslatef(position.getX(), position.getY(), position.getZ());

        wavefrontObject.getGroups().stream().forEach((g) -> {
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

        gl.glPopMatrix();
    }

    @Override
    public void update() {
        Vector v = computeCohersion().scale(.1f)
                .add(computeSeparation().scale(0.05f))
                .add(computeRandomness());

        setDirection(direction.add(v).normalize());
        move();
    }

    protected void setMaterial(Material mat, GL gl) {
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, new float[]{mat.getKa().getX(), mat.getKa().getY(), mat.getKa().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, new float[]{mat.getKd().getX(), mat.getKd().getY(), mat.getKd().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, new float[]{mat.getKs().getX(), mat.getKs().getY(), mat.getKs().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, new float[]{mat.getShininess(), 0, 0, 0}, 0);
    }

}
