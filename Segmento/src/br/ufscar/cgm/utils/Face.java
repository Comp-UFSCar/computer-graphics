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
public class Face {
   
    private double pontos[][] = new double[4][3];
    private float vetorNormal[] = new float[3];
    private Rendering render;
    
    public Face()
    {
        
    }
    
    public void inserePontos(double ponto[][])
    {
        for(int i = 0; i < 4; i++)    
        {
            for(int j = 0; j < 3; j ++)
            {
                pontos[i][j] = ponto[i][j];
                //System.out.println(pontos[i][j] +"\n");
            }
            
        }
    }
    
}
