/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.geometria;

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
    
    public double distanciaAte(Ponto3D p){
        double x = this.x - p.x;
        double y = this.y - p.y;
        double z = this.z - p.z;
        return Math.sqrt(x*x+y*y+z*z);
    }
    
    public static Ponto3D projetaPonto(Ponto3D ponto, Ponto3D camera)
    {
        double novoX, novoY;
        double d = Math.abs((double)camera.z);
        
        novoX = d*ponto.x/(d+ponto.z);
        novoY = d*ponto.y/(d+ponto.z);
        
        return new Ponto3D(novoX, novoY, 0);
    }
    
}
