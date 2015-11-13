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
/**
 * Classe com métodos para desenhar o cubo.
 * @author João Paulo RA:408034
 * @author Breno Silveira RA:551481
 * @author Camilo Moreira RA:359645
 */
public class Drawer {
    
    /**
     * Método para desenhar um pixel 2D, realizando os ajustes necessários na tela.
     * @param gl objeto gl
     * @param x coordenada x
     * @param y  coordenada y
     */
    public static void drawPixel2D(GL gl, int x, int y) {
        
        IntBuffer buffer = BufferUtil.newIntBuffer(4);
        gl.glGetIntegerv(GL.GL_VIEWPORT, buffer);
        int height = buffer.get(3);
        
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(x, y);
        gl.glEnd();
    }
    
    /**
     * Método para desenhar uma linha a partir de um ponto inicial e um ponto final. Consiste do algoritmo do
     * Ponto médio (algoritmo de Bresenham), implementado e documentado no trabalho 1.
     * @param gl objeto gl
     * @param x0 coordenada x do primeiro ponto
     * @param y0 coordenada y do primeiro ponto
     * @param x1 coordenada x do segundo ponto
     * @param y1 coordenada y do segundo ponto
     */
    public static void drawLine2D(GL gl, int x0, int y0, int x1, int y1) {

        int dx = 0, dy = 0, incE, incNE, d;
        int x, y;
        // Condição para saber se a inclinação da reta
        // está entre 0 e 45 ou entre 46 e 90 graus.
        boolean dx_menor_dy = false;
        // Usado para saber se a reta é 
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

        // Se dy é negativo, a reta é decrescente.
        // Mudamos o valor de dy para ser desenhado entre 0 e 45 graus
        // Mas ao invés de incrementar, y é decrementado (por dy_signal)
        if (dy < 0) {
            dy_signal = -1;
            dy = -dy;
        }

        //Cálculo das variáveis do algoritmo
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

    /**
     * Método para desenhar um cubo a partir de algumas informações iniciais que são passadas como parâmetro.
     * Os pontos foram determinados com o auxílio da biblioteca Jama, para manipulação de matrizes, estabelecendo
     * os valores do ponto e os valores de uma matriz de rotação que é aplicada em cada um dos pontos.
     * Após a determinação dos pontos em cada face, um array de faces passado como parâmetro é preenchido, depois
     * que todas as arestas das faces são determinadas.
     * @param gl objeto gl
     * @param faces array para armazenar todas as faces
     * @param r variação para determinar os 8 valores de x, y, z
     * @param x valor de x
     * @param y valor de y
     * @param z valor de z
     */
    public static void drawCube(GL gl, ArrayList<Face> faces, double r, double x, double y, double z) {
        
        ArrayList<Face> fs = new ArrayList<Face>();
        ArrayList<Aresta3D> arestas;
        Ponto3D[] p = new Ponto3D[8];
        
        double[][] rot = new double[4][4];
        
        // Rotação de -30 no eixo z e de -60 graus no eixo x
        rot[0][0] = 0.866;
        rot[0][1] = -0.5;
        rot[0][2] = 0;
        rot[1][0] = 0.25;
        rot[1][1] = 0.433;
        rot[1][2] = -0.866;
        rot[2][0] = 0.433;
        rot[2][1] = 0.75;
        rot[2][2] = 0.5;
        
        Matrix matriz_rotacao_cubo = new Matrix(rot); 
        Matrix matriz_coord_ponto = new Matrix(4,1); // 4 linhas e uma coluna
        matriz_coord_ponto.set(3, 0, 1.0);     
       
        //atribui a posição 0,0 da matriz o valor do terceiro argumento
        matriz_coord_ponto.set(0, 0, x+r); 
        matriz_coord_ponto.set(1, 0, y+r); 
        matriz_coord_ponto.set(2, 0, z+r);
        matriz_coord_ponto = matriz_rotacao_cubo.times(matriz_coord_ponto);
        p[0] = new Ponto3D(matriz_coord_ponto.get(0, 0), matriz_coord_ponto.get(1, 0), matriz_coord_ponto.get(2, 0));
        matriz_coord_ponto.set(0, 0, x+r); 
        matriz_coord_ponto.set(1, 0, y+r); 
        matriz_coord_ponto.set(2, 0, z-r);
        matriz_coord_ponto = matriz_rotacao_cubo.times(matriz_coord_ponto);
        p[1] = new Ponto3D(matriz_coord_ponto.get(0, 0), matriz_coord_ponto.get(1, 0), matriz_coord_ponto.get(2, 0));
        matriz_coord_ponto.set(0, 0, x+r); 
        matriz_coord_ponto.set(1, 0, y-r); 
        matriz_coord_ponto.set(2, 0, z+r);
        matriz_coord_ponto = matriz_rotacao_cubo.times(matriz_coord_ponto);
        p[2] = new Ponto3D(matriz_coord_ponto.get(0, 0), matriz_coord_ponto.get(1, 0), matriz_coord_ponto.get(2, 0));
        matriz_coord_ponto.set(0, 0, x+r); 
        matriz_coord_ponto.set(1, 0, y-r); 
        matriz_coord_ponto.set(2, 0, z-r);
        matriz_coord_ponto = matriz_rotacao_cubo.times(matriz_coord_ponto);
        p[3] = new Ponto3D(matriz_coord_ponto.get(0, 0), matriz_coord_ponto.get(1, 0), matriz_coord_ponto.get(2, 0));
        matriz_coord_ponto.set(0, 0, x-r); 
        matriz_coord_ponto.set(1, 0, y+r); 
        matriz_coord_ponto.set(2, 0, z+r);
        matriz_coord_ponto = matriz_rotacao_cubo.times(matriz_coord_ponto);
        p[4] = new Ponto3D(matriz_coord_ponto.get(0, 0), matriz_coord_ponto.get(1, 0), matriz_coord_ponto.get(2, 0));
        matriz_coord_ponto.set(0, 0, x-r); 
        matriz_coord_ponto.set(1, 0, y+r); 
        matriz_coord_ponto.set(2, 0, z-r);
        matriz_coord_ponto = matriz_rotacao_cubo.times(matriz_coord_ponto);
        p[5] = new Ponto3D(matriz_coord_ponto.get(0, 0), matriz_coord_ponto.get(1, 0), matriz_coord_ponto.get(2, 0));
        matriz_coord_ponto.set(0, 0, x-r); 
        matriz_coord_ponto.set(1, 0, y-r); 
        matriz_coord_ponto.set(2, 0, z+r);
        matriz_coord_ponto = matriz_rotacao_cubo.times(matriz_coord_ponto);
        p[6] = new Ponto3D(matriz_coord_ponto.get(0, 0), matriz_coord_ponto.get(1, 0), matriz_coord_ponto.get(2, 0));
        matriz_coord_ponto.set(0, 0, x-r); 
        matriz_coord_ponto.set(1, 0, y-r); 
        matriz_coord_ponto.set(2, 0, z-r);
        matriz_coord_ponto = matriz_rotacao_cubo.times(matriz_coord_ponto);
        p[7] = new Ponto3D(matriz_coord_ponto.get(0, 0), matriz_coord_ponto.get(1, 0), matriz_coord_ponto.get(2, 0));

        Aresta3D[] a = new Aresta3D[12];
        a[0] = new Aresta3D(p[0], p[1]);
        a[1] = new Aresta3D(p[0], p[2]);
        a[2] = new Aresta3D(p[0], p[4]);
        a[3] = new Aresta3D(p[1], p[3]);
        a[4] = new Aresta3D(p[1], p[5]);
        a[5] = new Aresta3D(p[2], p[3]);
        a[6] = new Aresta3D(p[2], p[6]);
        a[7] = new Aresta3D(p[3], p[7]);
        a[8] = new Aresta3D(p[4], p[5]);
        a[9] = new Aresta3D(p[4], p[6]);
        a[10] = new Aresta3D(p[5], p[7]);
        a[11] = new Aresta3D(p[6], p[7]);

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
