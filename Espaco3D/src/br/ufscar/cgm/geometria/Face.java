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
    public Ponto3D vetorNormal;
    
    public Face(ArrayList<Aresta3D> as, Pontos3D pt){
        arestas = as;
        ptParaCalcNorma = pt;
        vetorNormal = normalDaFace(pt); 
        //System.out.println(this.toString());
    }

    private Ponto3D normalDaFace(Pontos3D arg)
    {
        Ponto3D v = arg.v();
        Ponto3D w = arg.w();
        Ponto3D normal = new Ponto3D(((v.x * w.z)-(w.y*v.z)), ((-(v.x*w.z))-(w.x*v.z)), ((v.x*w.y)-(w.x*v.y)));     
        
        return normal;
    }
    
    public float getIntensidade(float Ia, float Ka, float Ip, float Kd, Ponto3D direcao)
    {
        int produtoEscalar = vetorNormal.x * direcao.x + vetorNormal.y * direcao.y + vetorNormal.z * direcao.z;
        float normaDoNormal = (float) Math.sqrt(vetorNormal.x*vetorNormal.x 
                + vetorNormal.y*vetorNormal.y 
                + vetorNormal.z*vetorNormal.z);
        float normaDaDirecao = (float) Math.sqrt(direcao.x*direcao.x 
                + direcao.y*direcao.y 
                + direcao.z*direcao.z);
	return Ia*Ka + (Ip * Kd * produtoEscalar) / (normaDoNormal*normaDaDirecao);
    }
    
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
