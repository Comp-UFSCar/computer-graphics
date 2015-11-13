/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.geometria;

/**
 * Classe que representa um ponto 3d, composto pelas coordenadas x, y, z. <br>
 * @author João Paulo RA:408034
 * @author Breno Silveira RA:551481
 * @author Camilo Moreira RA:359645
 */
public class Ponto3D {
    
    public double x, y, z;
    
    public Ponto3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public String toString(){
        return ("["+String.valueOf(x)+", "+String.valueOf(y)+", "+String.valueOf(z)+"]" );
    }
    
    /**
     * Método para calcular a distância de um determinado ponto representado por p e o ponto representado
     * nessa instância da classe. Isso é feito por meio da subtração das coordenadas do ponto da classe e do 
     * ponto p.
     * 
     * @param p Ponto3D que se deseja determinar a distância
     * @return distância.
     */
    public double distanciaAte(Ponto3D p){
        double x = this.x - p.x;
        double y = this.y - p.y;
        double z = this.z - p.z;
        return Math.sqrt(x*x+y*y+z*z);
    }
    
    /**
     * Método para calcular a projeção perspectiva de um ponto. Essa projeção é feita considerando o caso em 
     * que o eixo z é igual a 0.
     * @param ponto ponto 3D a ser projetado
     * @param camera referência a câmera para poder encontrar o melhor valor de d
     * @return ponto projetado, com a coordenada z igual a 0.
     */
    public static Ponto3D projetaPonto(Ponto3D ponto, Ponto3D camera)
    {
        double novoX, novoY;
        double d = Math.abs((double)camera.z);
        
        novoX = d*ponto.x/(d+ponto.z);
        novoY = d*ponto.y/(d+ponto.z);
        
        return new Ponto3D(novoX, novoY, 0);
    }
    
}
