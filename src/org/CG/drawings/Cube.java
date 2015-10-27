package org.CG.drawings;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import org.CG.infrastructure.base.Drawing;
import org.CG.infrastructure.structures.Point;

/**
 *
 * @author ldavid
 */
public class Cube extends Drawing {
    
    float size;

    public Cube() {
        super();
        size = 0;
    }
    
    public Cube(float size) {
        this();
        this.size = size;
    }
    
    @Override
    public Drawing updateLastCoordinate(Point point) {
        size = (float) start.euclidianDistance(point);
        return this;
    }

    @Override
    protected void drawShape(GL gl, GLUT glut) {
        gl.glPushMatrix();
        
        gl.glTranslated(location.getX(), location.getY(), location.getZ());
        glut.glutSolidCube(size);
        
        gl.glPopMatrix();
    }
}
