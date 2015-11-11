/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.geometria;

import java.util.ArrayList;

/**
 * Classe que representa a face do polígono. A face possui como atributos uma lista de arestas (usadas para desenhar 
 * o "contorno" de cada face), um atributo do tipo Pontos3D para armaznar 3 pontos dessa face (3 pontos não colineares
 * determinam um plano, e a partir da dos coeficientes da equação do plano será calculado o vetor normal) e um atributo
 * do tipo Ponto3D para representar o vetor normal. Na construção de uma instância da classe Face o vetor normal dessa 
 * face é calculado para ser utilizado nas equações de determinação de intensidade. <br>
 * @author João Paulo RA:408034
 * @author Breno Silveira RA:551481
 * @author Camilo Moreira RA:359645
 */
public class Face {
    
    public ArrayList<Aresta3D> arestas = new ArrayList<Aresta3D>();
    public Pontos3D ptParaCalcNorma;
    public Ponto3D vetorNormal;
    
    public Face(ArrayList<Aresta3D> as, Pontos3D pt){
        arestas = as;
        ptParaCalcNorma = pt;
        vetorNormal = normalDaFace(pt); 
        
    }

    /**
     * O cálculo da normal da face é feito determinando 2 vetores que pertencem ao plano a partir dos pontos informados.
     * Assim, a partir cálculo do determinante são da matriz formada por esses vetores, obtém-se os valores
     * de i, j, k do vetor normal.
     * 
     * @param arg: representa o conjunto de pontos 3D para calcular o vetor normal
     * @return o vetor normal representado por uma instância da classe Ponto3D
     */
    private Ponto3D normalDaFace(Pontos3D arg)
    {
        Ponto3D v = arg.v();
        Ponto3D w = arg.w();
        Ponto3D normal = new Ponto3D(((v.x * w.z)-(w.y*v.z)), ((-(v.x*w.z))-(w.x*v.z)), ((v.x*w.y)-(w.x*v.y)));     
        
        return normal;
    }
    
    /**
     * Para determinar a intendidade, primeiramente é realizado o cálculo do produtoEscalar entre o vetor normal
     * da face e a direção da luz. Após isso é feito o cálculo da norma da direção e do vetor normal. Por fim, a fórmula
     * para se obter a intensidade da luz é calculada (considerando a luz ambiente e a luz difusa).
     * 
     * @param Ia - intensidade da luz ambiente
     * @param Ka - constante de reflexão da luz ambiente
     * @param Ip - intensidade da luz pontual
     * @param Kd - constante de reflexão da luz difusa
     * @param direcao - vetor que representa a direcao da luz
     * @return intensidade da luz (considerando a luz ambiente e a luz difusa)
     */
    public float getIntensidade(float Ia, float Ka, float Ip, float Kd, Ponto3D direcao)
    {
        double produtoEscalar = vetorNormal.x * direcao.x + vetorNormal.y * direcao.y + vetorNormal.z * direcao.z;
        float normaDoNormal = (float) Math.sqrt(vetorNormal.x*vetorNormal.x 
                + vetorNormal.y*vetorNormal.y 
                + vetorNormal.z*vetorNormal.z);
        float normaDaDirecao = (float) Math.sqrt(direcao.x*direcao.x 
                + direcao.y*direcao.y 
                + direcao.z*direcao.z);
	return (float) (Ia*Ka + (Ip * Kd * produtoEscalar) / (normaDoNormal*normaDaDirecao));
    }
    
    /**
     * Método auxiliar para determinar a maior distância de uma face e um ponto (no caso, o ponto que representa
     * a deireção da câmera). A partir dos pontos que formam a face é determinadp a maior distância por meio
     * do cálculo da distância de cada ponto do vetor passado como parâmetro, por meio do método distanciaAte da
     * classe Ponto3D.
     * @param camera - vetor com a direção da câmera
     * @return a distância máxima da face até a câmera
     */
    public double getMaiorDistanciaAtePonto(Ponto3D camera){
        ArrayList<Ponto3D> pontos = new ArrayList<Ponto3D>(4);
        for(Aresta3D a : arestas){
            if(!pontos.contains(a.inicio))
                pontos.add(a.inicio);
            if(!pontos.contains(a.fim))
                pontos.add(a.fim);
        }
        double max = -1;
        for(Ponto3D p : pontos){
            max = Math.max(max, p.distanciaAte(camera));
        }
        return max;
    }
    
    @Override
    public String toString(){
        return ("Normal :" + vetorNormal.toString());
    }
}
