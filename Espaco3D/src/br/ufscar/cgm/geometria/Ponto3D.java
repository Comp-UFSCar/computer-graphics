/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.geometria;

/**
 * Classe que representa um ponto 3d, composto pelas coordenadas x, y, z. <br>
 * @author Jo�o Paulo RA:408034
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
    
    public Ponto3D pontoNormalizado()
    {
        int norma = (int) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return new Ponto3D(this.x/norma,this.x/norma,this.x/norma );
    }
    
    @Override
    public String toString(){
        return ("["+String.valueOf(x)+", "+String.valueOf(y)+", "+String.valueOf(z)+"]" );
    }
    
    /**
     * M�todo para calcular a dist�ncia de um determinado ponto representado por p e o ponto representado
     * nessa inst�ncia da classe. Isso � feito por meio da subtra��o das coordenadas do ponto da classe e do 
     * ponto p.
     * 
     * @param p Ponto3D que se deseja determinar a dist�ncia
     * @return dist�ncia.
     */
    public double distanciaAte(Ponto3D p){
        double x = this.x - p.x;
        double y = this.y - p.y;
        double z = this.z - p.z;
        return Math.sqrt(x*x+y*y+z*z);
    }
    
    /**
     * M�todo para calcular a proje��o perspectiva de um ponto. Essa proje��o � feita considerando o caso em 
     * que o eixo z � igual a 0.
     * @param ponto ponto 3D a ser projetado
     * @param camera refer�ncia a c�mera para poder encontrar o melhor valor de d
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
