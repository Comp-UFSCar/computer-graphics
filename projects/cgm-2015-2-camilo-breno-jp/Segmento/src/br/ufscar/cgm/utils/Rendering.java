/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere.Segmento.src.br.ufscar.cgm.utils;

/**
 *
 * @author Breno
 */
public class Rendering {
    
    public float[] calculaNormal(float a[], float b[], float c[])
    {
        //Recebe como parametro 3 vetores com os valores das coordenadas de 3 pontos (ou seja, os pontos necessarios
        //para determinar a equacao do plano.
        //tendo os vetores ab e bc, podemos calcular o vetor normal que é perpendicular ao plano
        
        float v[] = new float[3];
        float w[] = new float[3];
        float normal[] = new float[3];
        
        v[0] = b[0] - a[0];
        v[1] = b[1] - a[1];
        v[2] = b[2] - a[2];
        
        w[0] = c[0] - b[0];
        w[1] = c[1] - b[1];
        w[2] = c[2] - b[2];
        
        normal[0] = (v[1]*w[2])-( w[1]*v[2]); 
	normal[1] =  -((v[0]*w[2])-( w[0]*v[2])); 
	normal[2] = (v[0]*w[1])-( w[0]*v[1]);
        
        return normal;
               
    }
    
    /**
     * Metodo que representa a reflexao ambiente, recebendo como parametros a intensidade da luz ambiente
     * e a quantidade da luz ambiente refletida da superficie em um objeto, representado por Ka, que varia 
     * entre 0 e 1.
     * @param Ia
     * @param Ka
     * @return 
     */
    public float reflexaoAmbiente(int Ia, float Ka)
    {
        return Ia * Ka; 
    }
    
    public float reflexaoDifusa(int Ip, int Kd, int vetorNormal[], int direcao[])
    {
	int produtoEscalar = vetorNormal[0] * direcao[0] + vetorNormal[1] * direcao[1] + vetorNormal[2] * direcao[2];
	return Ip * Kd * produtoEscalar;
        
    }
}
