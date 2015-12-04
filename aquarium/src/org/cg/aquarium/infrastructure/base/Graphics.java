package org.cg.aquarium.infrastructure.base;

import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.GL;
import libs.modelparser.Material;
import libs.modelparser.Vertex;
import libs.modelparser.WavefrontObject;
import org.cg.aquarium.infrastructure.Environment;
import org.cg.aquarium.infrastructure.TextureLoader;
import org.cg.aquarium.infrastructure.helpers.MathHelper;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Graphics.
 *
 * Instances of this class can load {@code .obj} files and draw them onto the
 * canvas.
 *
 * @author ldavid
 */
public class Graphics {

    private String textureName;

    protected WavefrontObject model;
    protected Material material;
    protected Texture texture;

    public Graphics(String modelPath, String materialName, String texturePath) {
        this(new WavefrontObject(modelPath), new Material(materialName),
                texturePath);
    }

    public Graphics(WavefrontObject model, Material material,
            String texturePath) {
        this(model, material,
                TextureLoader.getTextureLoader().load(texturePath));

        this.textureName = texturePath;
    }

    public Graphics(WavefrontObject model, Material material, Texture texture) {
        this.model = model;
        this.material = material;
        this.texture = texture;
    }

    public WavefrontObject getModel() {
        return model;
    }

    public void setModel(WavefrontObject model) {
        this.model = model;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void glDefineObjectMaterial(GL gl) {
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, new float[]{
            material.getKa().getX(), material.getKa().getY(),
            material.getKa().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, new float[]{
            material.getKd().getX(), material.getKd().getY(),
            material.getKd().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, new float[]{
            material.getKs().getX(), material.getKs().getY(),
            material.getKs().getZ(), 1}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, new float[]{
            material.getShininess(), 0, 0, 0}, 0);
    }

    public void glBindTexture() {
        texture = texture != null
                ? texture
                : TextureLoader.getTextureLoader().getTexture(textureName);

        if (texture != null) {
            texture.enable();
            texture.bind();
        }
    }

    public void glUnbindTexture() {
        if (texture != null) {
            texture.disable();
        }
    }

    public void glAlignObjectWithVector(GL gl, Vector direction, Vector base) {
        double cos = base.dot(new Vector(
                direction.getX(), 0, direction.getZ()).normalize());

        gl.glRotated(
                -Math.signum(direction.getX())
                * MathHelper.radiansToDegree(Math.acos(cos)), 0, 1, 0);
    }

    public void glRenderObject(GL gl) {
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
    }

    public void glDebugPlotVector(GL gl, Vector v) {
        if (Environment.getEnvironment().isDebugging()) {
            gl.glBegin(GL.GL_LINES);

            gl.glVertex3d(0, 0, 0);
            gl.glVertex3d(v.getX(), v.getY(), v.getZ());

            gl.glEnd();
        }
    }
}
