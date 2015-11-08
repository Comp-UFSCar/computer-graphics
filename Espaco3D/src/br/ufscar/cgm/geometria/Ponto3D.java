/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.geometria;

public class Ponto3D {
    
    public int x, y, z;
    
    public Ponto3D(int x, int y, int z){
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
    
}
