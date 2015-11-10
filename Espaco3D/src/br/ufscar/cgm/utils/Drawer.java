/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.utils;

import Jama.Matrix;
import br.ufscar.cgm.geometria.Pontos3D;
import br.ufscar.cgm.geometria.Aresta3D;
import br.ufscar.cgm.geometria.Face;
import br.ufscar.cgm.geometria.Ponto3D;
import com.sun.opengl.util.BufferUtil;
import java.nio.IntBuffer;
import java.util.ArrayList;
import javax.media.opengl.GL;

public class Drawer {

    public static void drawPixel2D(GL gl, int x, int y) {
        
        IntBuffer buffer = BufferUtil.newIntBuffer(4);
        gl.glGetIntegerv(GL.GL_VIEWPORT, buffer);
        int height = buffer.get(3);
        
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(x, y);
        gl.glEnd();
    }

    public static void drawLine2D(GL gl, int x0, int y0, int x1, int y1) {

        /* NÃ£o Ã© mais necessÃ¡rio essa conversÃ£o.
         IntBuffer buffer = BufferUtil.newIntBuffer(4);
         gl.glGetIntegerv(GL.GL_VIEWPORT, buffer);
         int height = buffer.get(3);

         y0 = height - y0;
         y1 = height - y1;*/

        int dx = 0, dy = 0, incE, incNE, d;
        int x, y;
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
                drawPixel2D(gl, x , y );
            } else {
                drawPixel2D(gl, y , x );
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

    public static void drawCube(GL gl, ArrayList<Face> faces, double r, double x, double y, double z) {
        
        ArrayList<Face> fs = new ArrayList<Face>();
        ArrayList<Aresta3D> arestas;
        Ponto3D[] p = new Ponto3D[8];
        
        double[][] rot = new double[4][4];
        
        // Rotação de 30 no eixo z e de 60 graus no eixo x
        double sqrt3 = Math.sqrt(3);
        rot[0][0] = 0.86;//Math.cos(Math.PI/4);
        rot[0][1] = -0.5;//-Math.sin(Math.PI/4);
        rot[0][2] = 0;//Math.sin(Math.PI/4);
        rot[1][0] = 0.25;//Math.cos(Math.PI/4);
        rot[1][1] = 0.43;
        rot[1][2] = 0.86;
        rot[2][0] = -0.43;
        rot[2][1] = -0.75;
        rot[2][2] = 0.5;
        
        Matrix rotacao = new Matrix(rot);
        Matrix ponto = new Matrix(4,1);
        ponto.set(3, 0, 1.0);     
       
        ponto.set(0, 0, x+r); 
        ponto.set(1, 0, y+r); 
        ponto.set(2, 0, z+r);
        ponto = rotacao.times(ponto);
        p[0] = new Ponto3D(ponto.get(0, 0), ponto.get(1, 0), ponto.get(2, 0));
        ponto.set(0, 0, x+r); 
        ponto.set(1, 0, y+r); 
        ponto.set(2, 0, z-r);
        ponto = rotacao.times(ponto);
        p[1] = new Ponto3D(ponto.get(0, 0), ponto.get(1, 0), ponto.get(2, 0));
        ponto.set(0, 0, x+r); 
        ponto.set(1, 0, y-r); 
        ponto.set(2, 0, z+r);
        ponto = rotacao.times(ponto);
        p[2] = new Ponto3D(ponto.get(0, 0), ponto.get(1, 0), ponto.get(2, 0));
        ponto.set(0, 0, x+r); 
        ponto.set(1, 0, y-r); 
        ponto.set(2, 0, z-r);
        ponto = rotacao.times(ponto);
        p[3] = new Ponto3D(ponto.get(0, 0), ponto.get(1, 0), ponto.get(2, 0));
        ponto.set(0, 0, x-r); 
        ponto.set(1, 0, y+r); 
        ponto.set(2, 0, z+r);
        ponto = rotacao.times(ponto);
        p[4] = new Ponto3D(ponto.get(0, 0), ponto.get(1, 0), ponto.get(2, 0));
        ponto.set(0, 0, x-r); 
        ponto.set(1, 0, y+r); 
        ponto.set(2, 0, z-r);
        ponto = rotacao.times(ponto);
        p[5] = new Ponto3D(ponto.get(0, 0), ponto.get(1, 0), ponto.get(2, 0));
        ponto.set(0, 0, x-r); 
        ponto.set(1, 0, y-r); 
        ponto.set(2, 0, z+r);
        ponto = rotacao.times(ponto);
        p[6] = new Ponto3D(ponto.get(0, 0), ponto.get(1, 0), ponto.get(2, 0));
        ponto.set(0, 0, x-r); 
        ponto.set(1, 0, y-r); 
        ponto.set(2, 0, z-r);
        ponto = rotacao.times(ponto);
        p[7] = new Ponto3D(ponto.get(0, 0), ponto.get(1, 0), ponto.get(2, 0));

        Aresta3D[] a = new Aresta3D[12];
        a[0] = new Aresta3D(p[0], p[1]);
        //drawLine3D(gl, a[0]);
        a[1] = new Aresta3D(p[0], p[2]);
        //drawLine3D(gl, a[1]);
        a[2] = new Aresta3D(p[0], p[4]);
        //drawLine3D(gl, a[2]);
        a[3] = new Aresta3D(p[1], p[3]);
        //drawLine3D(gl, a[3]);
        a[4] = new Aresta3D(p[1], p[5]);
        //drawLine3D(gl, a[4]);
        a[5] = new Aresta3D(p[2], p[3]);
        //drawLine3D(gl, a[5]);
        a[6] = new Aresta3D(p[2], p[6]);
        //drawLine3D(gl, a[6]);
        a[7] = new Aresta3D(p[3], p[7]);
        //drawLine3D(gl, a[7]);
        a[8] = new Aresta3D(p[4], p[5]);
        //drawLine3D(gl, a[8]);
        a[9] = new Aresta3D(p[4], p[6]);
        //drawLine3D(gl, a[9]);
        a[10] = new Aresta3D(p[5], p[7]);
        //drawLine3D(gl, a[10]);
        a[11] = new Aresta3D(p[6], p[7]);
        //drawLine3D(gl, a[11]);

        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[0]);
        arestas.add(a[1]);
        arestas.add(a[3]);
        arestas.add(a[5]);
        fs.add(new Face(arestas, new Pontos3D(p[2],p[1],p[0])));
        
        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[0]);
        arestas.add(a[2]);
        arestas.add(a[4]);
        arestas.add(a[8]);
        fs.add(new Face(arestas, new Pontos3D(p[4], p[1], p[0])));

        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[1]);
        arestas.add(a[2]);
        arestas.add(a[6]);
        arestas.add(a[9]);
        fs.add(new Face(arestas, new Pontos3D(p[4],p[2],p[0])));

        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[5]);
        arestas.add(a[6]);
        arestas.add(a[7]);
        arestas.add(a[11]);
        fs.add(new Face(arestas, new Pontos3D(p[2],p[3],p[6])));
        
        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[3]);
        arestas.add(a[4]);
        arestas.add(a[7]);
        arestas.add(a[10]);
        fs.add(new Face(arestas, new Pontos3D(p[1], p[3], p[5])));

        arestas = new ArrayList<Aresta3D>();
        arestas.add(a[8]);
        arestas.add(a[9]);
        arestas.add(a[10]);
        arestas.add(a[11]);
        fs.add(new Face(arestas, new Pontos3D(p[4],p[5],p[6]))); 
        
        if(faces != null)
            faces.addAll(fs);
    }

}
