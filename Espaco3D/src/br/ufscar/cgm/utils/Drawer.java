/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.utils;

import br.ufscar.cgm.geometria.Pontos3D;
import br.ufscar.cgm.geometria.Aresta3D;
import br.ufscar.cgm.geometria.Face;
import br.ufscar.cgm.geometria.Ponto3D;
import com.sun.opengl.util.BufferUtil;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.media.opengl.GL;

public class Drawer {
    
    public static final int precision = 100;
    public static boolean original_size = false;
    
    public static void drawPixel2D(GL gl, double x, double y) {
        drawPixel3D(gl,x,y,0);
    }
    
    public static void drawPixel3D(GL gl, double x, double y, double z) {
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex3d(x, y, z);
        gl.glEnd();
    }
    
    public static void drawLine2D(GL gl, int x0, int y0, int x1, int y1) {

        /* NÃ£o Ã© mais necessÃ¡rio essa conversÃ£o.
        IntBuffer buffer = BufferUtil.newIntBuffer(4);
        gl.glGetIntegerv(GL.GL_VIEWPORT, buffer);
        int height = buffer.get(3);

        y0 = height - y0;
        y1 = height - y1;*/
        if(!original_size){
            x0 = x0*precision;
            y0 = y0*precision;
            x1 = x1*precision;
            y1 = y1*precision;
        }

        int dx = 0, dy = 0, incE, incNE, d;
        double x, y;
        // CondiÃ§Ã£o para saber se a inclinaÃ§Ã£o da reta
        // estÃ¡ entre 0 e 45 ou entre 46 e 90 graus.
        boolean dx_menor_dy = false;
        // Usado para saber se a reta Ã© 
        // crescente ou decrescente
        int dy_signal = 1;

        dx = x1 - x0;
        dy = y1 - y0;

        if (Math.abs(dx) < Math.abs(dy)) {
            dx_menor_dy = true;

            // Troca dx e dy
            int aux = dx;
            dx = dy;
            dy = aux;

            // Troca x0 e y0
            aux = x0;
            x0 = y0;
            y0 = aux;

            // Troca x1 e y1
            aux = x1;
            x1 = y1;
            y1 = aux;
        }
        
        if (x1 < x0) {
            int aux = x1;
            x1 = x0;
            x0 = aux;

            aux = y1;
            y1 = y0;
            y0 = aux;

            dx = -dx;
            dy = -dy;
        }

        // Se dy Ã© negativo, a reta Ã© decrescente.
        // Mudamos o valor de dy para ser desenhado entre 0 e 45 graus
        // Mas ao invÃ©s de incrementar, y Ã© decrementado (por dy_signal)
        if (dy < 0) {
            dy_signal = -1;
            dy = -dy;
        }

        //CÃ¡lculo das variÃ¡veis do algoritmo
        d = 2 * dy - dx;
        incE = 2 * dy;
        incNE = 2 * (dy - dx);

        x = x0;
        y = y0;

        while (x <= x1) {

            if (!dx_menor_dy) {
                drawPixel2D(gl, x/precision, y/precision);
            } else {
                drawPixel2D(gl, y/precision, x/precision);
            }

            x++;
            if (d <= 0) {
                d = d + incE;
            } else {
                d = d + incNE;
                y += dy_signal;
            }

        }

    }
    
    public static void drawLine3D(GL gl, int x0, int y0, int z0, int x1, int y1, int z1) {
        
        if(!original_size){
            x0 = x0*precision;
            y0 = y0*precision;
            z0 = z0*precision;
            x1 = x1*precision;
            y1 = y1*precision;
            z1 = z1*precision;
        }
        

        int dx_1 = 0, dx_2 = 0, dy = 0, dz = 0; 
        int incE_y, incNE_y, incE_z, incNE_z; 
        int d_1, d_2;
        double x, y, z;
        // CondiÃ§Ã£o para saber se a inclinaÃ§Ã£o da reta
        // estÃ¡ entre 0 e 45 ou entre 46 e 90 graus.
        boolean dx_menor_dy = false;
        boolean dx_menor_dz = false;
        // Usado para saber se a reta Ã© 
        // crescente ou decrescente
        int dy_signal = 1;
        int dz_signal = 1;

        dx_1 = dx_2 = x1 - x0;
        dy = y1 - y0;
        dz = z1 - z0;

        if (Math.abs(dx_1) < Math.abs(dy)) {
            dx_menor_dy = true;

            // Troca dx e dy
            int aux = dx_1;
            dx_1 = dy;
            dy = aux;

            // Troca x0 e y0
            aux = x0;
            x0 = y0;
            y0 = aux;

            // Troca x1 e y1
            aux = x1;
            x1 = y1;
            y1 = aux;
        }
        
        if (Math.abs(dx_2) < Math.abs(dz)) {
            dx_menor_dz = true;

            // Troca dx e dy
            int aux = dx_2;
            dx_2 = dz;
            dz = aux;

            // Troca x0 e y0
            aux = x0;
            x0 = z0;
            z0 = aux;

            // Troca x1 e y1
            aux = x1;
            x1 = z1;
            z1 = aux;
        }
        
        if (x1 < x0) {
            int aux = x1;
            x1 = x0;
            x0 = aux;

            aux = y1;
            y1 = y0;
            y0 = aux;
            
            aux = z1;
            z1 = z0;
            z0 = aux;

            dx_1 = -dx_1;
            dx_2 = -dx_2;
            dy = -dy;
            dz = -dz;
        }

        // Se dy Ã© negativo, a reta Ã© decrescente.
        // Mudamos o valor de dy para ser desenhado entre 0 e 45 graus
        // Mas ao invÃ©s de incrementar, y Ã© decrementado (por dy_signal)
        if (dy < 0) {
            dy_signal = -1;
            dy = -dy;
        }

        if (dz < 0) {
            dz_signal = -1;
            dz = -dz;
        }

        //CÃ¡lculo das variÃ¡veis do algoritmo
        d_1 = 2 * dy - dx_1;
        d_2 = 2 * dz - dx_2;
        incE_y = 2 * dy;
        incNE_y = 2 * (dy - dx_1);
        incE_z = 2 * dz;
        incNE_z = 2 * (dz - dx_2);

        x = x0;
        y = y0;
        z = z0;

        while (x <= x1) {

            if (!dx_menor_dy && !dx_menor_dz) {
                drawPixel3D(gl, x/precision, y/precision, z/precision);
            } else if (!dx_menor_dz) {
                drawPixel3D(gl, y/precision, x/precision, z/precision);
            } else if (!dx_menor_dy) {
                drawPixel3D(gl, z/precision, y/precision, x/precision);
            } else {
                drawPixel3D(gl, y/precision, x/precision, x/precision);
                //drawPixel3D(gl, z/precision, x/precision, x/precision);
            }

            x++;
            
            if (d_1 <= 0) {
                d_1 = d_1 + incE_y;
            } else {
                d_1 = d_1 + incNE_y;
                y += dy_signal;
            }
            
            if (d_2 <= 0) {
                d_2 = d_2 + incE_z;
            } else {
                d_2 = d_2 + incNE_z;
                z += dz_signal;
            }

        }

    }
    
    public static void drawLine3D(GL gl, Aresta3D a) {
      
        Drawer.drawLine3D(gl, a.inicio.x, a.inicio.y, a.inicio.z, a.fim.x, a.fim.y, a.fim.z);
    }
    
    
    public static ArrayList<Face> drawCube(GL gl, int r, int x, int y, int z){
        ArrayList<Face> faces = new ArrayList<Face>();
        ArrayList<Aresta3D> arestas;
        
        Ponto3D[] p = new Ponto3D[8];
        p[0] = new Ponto3D(x+r, y+r, z+r);
        p[1] = new Ponto3D(x+r, y+r, z-r);
        p[2] = new Ponto3D(x+r, y-r, z+r);
        p[3] = new Ponto3D(x+r, y-r, z-r);
        p[4] = new Ponto3D(x-r, y+r, z+r);
        p[5] = new Ponto3D(x-r, y+r, z-r);
        p[6] = new Ponto3D(x-r, y-r, z+r);
        p[7] = new Ponto3D(x-r, y-r, z-r);
        
        Aresta3D[] a = new Aresta3D[12];
        a[0] = new Aresta3D(p[0], p[1]);
        drawLine3D(gl, a[0]);
        a[1] = new Aresta3D(p[0], p[2]);
        drawLine3D(gl, a[1]);
        a[2] = new Aresta3D(p[0], p[4]);
        drawLine3D(gl, a[2]);
        a[3] = new Aresta3D(p[1], p[3]);
        drawLine3D(gl, a[3]);
        a[4] = new Aresta3D(p[1], p[5]);
        drawLine3D(gl, a[4]);
        a[5] = new Aresta3D(p[2], p[3]);
        drawLine3D(gl, a[5]);
        a[6] = new Aresta3D(p[2], p[6]);
        drawLine3D(gl, a[6]);
        a[7] = new Aresta3D(p[3], p[7]);
        drawLine3D(gl, a[7]);
        a[8] = new Aresta3D(p[4], p[5]);
        drawLine3D(gl, a[8]);
        a[9] = new Aresta3D(p[4], p[6]);
        drawLine3D(gl, a[9]);
        a[10] = new Aresta3D(p[5], p[7]);
        drawLine3D(gl, a[10]);
        a[11] = new Aresta3D(p[6], p[7]);
        drawLine3D(gl, a[11]);
        
        /*arestas = new ArrayList<Aresta3D>();
        arestas.add(a[0]);
        arestas.add(a[1]);
        arestas.add(a[3]);
        arestas.add(a[5]);
        faces.add(new Face(arestas, new Pontos3D(p[0],p[1],p[2])));*/
        
        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[0]);
        arestas.add(a[2]);
        arestas.add(a[4]);
        arestas.add(a[8]);
        faces.add(new Face(arestas, new Pontos3D(p[0],p[1],p[4])));
        
        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[1]);
        arestas.add(a[2]);
        arestas.add(a[6]);
        arestas.add(a[9]);
        faces.add(new Face(arestas, new Pontos3D(p[0],p[2],p[4])));
        
        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[5]);
        arestas.add(a[6]);
        arestas.add(a[7]);
        arestas.add(a[11]);
        faces.add(new Face(arestas, new Pontos3D(p[2],p[3],p[6])));
        
        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[3]);
        arestas.add(a[4]);
        arestas.add(a[7]);
        arestas.add(a[10]);
        faces.add(new Face(arestas, new Pontos3D(p[1],p[3],p[5])));
        
        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[8]);
        arestas.add(a[9]);
        arestas.add(a[10]);
        arestas.add(a[11]);
        faces.add(new Face(arestas, new Pontos3D(p[4],p[5],p[6])));
        
        return faces;
    }


}