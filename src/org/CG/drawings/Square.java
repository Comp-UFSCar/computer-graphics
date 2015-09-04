package org.CG.drawings;

import javax.media.opengl.GL;
import org.CG.infrastructure.Drawing;
import org.CG.infrastructure.Point;

/**
 *
 * @author ldavid
 */
public class Square extends Drawing {

    private int width;
    private int height;

    public Square() {
        super();
        
        this.width = 0;
        this.height = 0;
        glDrawingType = GL.GL_POLYGON;
    }

    @Override
    public Drawing updateLastCoordinate(Point point) {
        int s0 = point.getX() - start.getX();
        int s1 = point.getY() - start.getY();

        width = Math.abs(s0) >= Math.abs(s1) ? s0 : s1;
        height = s1 > 0 ? Math.abs(width) : -Math.abs(width);

        return this;
    }

    @Override
    public void drawShape(GL gl) {
        gl.glVertex2i(start.getX(), start.getY());
        gl.glVertex2i(start.getX() + width, start.getY());
        gl.glVertex2i(start.getX() + width, start.getY() + height);
        gl.glVertex2i(start.getX(), start.getY() + height);
    }
}
