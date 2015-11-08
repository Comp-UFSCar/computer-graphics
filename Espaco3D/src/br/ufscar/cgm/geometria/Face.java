/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.geometria;

import java.util.ArrayList;

public class Face {
    
    public ArrayList<Aresta3D> arestas = new ArrayList<Aresta3D>();
    public Pontos3D ptParaCalcNorma;
    public Ponto3D Vnormal;
    public double Intensidade;
    
    public Face(ArrayList<Aresta3D> as, Pontos3D pt){
        arestas = as;
        ptParaCalcNorma = pt;
        Vnormal = normalDaFace(pt);
        
    }
    
    public Ponto3D normalDaFace(Pontos3D arg)
    {
        Ponto3D v = arg.v();
        Ponto3D w = arg.w();
        return new Ponto3D(((v.x * w.z)-(w.y*v.z)), ((-(v.x*w.z))-(w.x*v.z)), ((v.x*w.y)-(w.x*v.y)));
    }
    
    public void ReflexaoDifusa(int Ip, int Kd, Ponto3D direcao)
    {
        int produtoEscalar = Vnormal.x * direcao.x + Vnormal.y * direcao.y + Vnormal.z * direcao.z;
	Intensidade =  Ip * Kd * produtoEscalar;
    }
    
    @Override
    public String toString(){
        return ("Normal :" + Vnormal.toString());
    }
}
