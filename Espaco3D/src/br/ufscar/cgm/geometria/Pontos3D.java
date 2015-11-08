/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.geometria;

/**
 *
 * @author Breno
 */
public class Pontos3D {
    
    Ponto3D p1, p2, p3;
    
    public Pontos3D(Ponto3D p1, Ponto3D p2, Ponto3D p3)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
    
    public Ponto3D v()
    {
        return new Ponto3D(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z);
    }
    
    public Ponto3D w()
    {
       return new Ponto3D(p3.x - p2.x, p3.y - p2.y, p3.z - p2.z); 
    }
    
    public Ponto3D getP1()
    {
        return this.p1;
    }
    
    public Ponto3D getP2()
    {
        return this.p2;
    }
    
    public Ponto3D getP3()
    {
        return this.p3;
    }
}
