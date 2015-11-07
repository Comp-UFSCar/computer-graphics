/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm.geometria;

import java.util.ArrayList;

public class Face {
    
    public ArrayList<Aresta3D> arestas = new ArrayList<Aresta3D>();
    
    public Face(ArrayList<Aresta3D> as){
        arestas = as;
    }
}
