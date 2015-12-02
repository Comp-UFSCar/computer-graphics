package org.cg.aquarium.infrastructure.base;

import javax.media.opengl.GL;
import libs.modelparser.Material;
import libs.modelparser.Vertex;
import libs.modelparser.WavefrontObject;
import org.cg.aquarium.infrastructure.helpers.MathHelper;
import org.cg.aquarium.infrastructure.representations.Vector;

/**
 * Object model.
 *
 * Object models can load {@code .obj} files and draw them onto the canvas.
 *
 * @author ldavid
 */
public class ObjectModel {

    protected WavefrontObject model;
    protected Material material;

    public ObjectModel(String modelPath, String materialName) {
        model = new WavefrontObject(modelPath);
        material = new Material(materialName);
    }

    public ObjectModel(WavefrontObject model, Material material) {
        this.model = model;
        this.material = material;
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

    public void glDefineMaterial(GL gl) {
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

    public void glAlignDirection(GL gl, Vector direction, Vector base) {
        float cos = base.dot(new Vector(
                direction.getX(), 0, direction.getZ()).normalize());

        gl.glRotated(
                -Math.signum(direction.getX())
                * MathHelper.radiansToDegree(Math.acos(cos)), 0, 1, 0);
    }

    public void glRender(GL gl) {
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

}
