package br.ufscar.cgm.geometria;

/**
 * Classe que representa uma aresta no espaço 3D, composta de 2 pontos 3D, representados pelos atributos
 * inicio e fim.<br>
 * @author João Paulo RA:408034
 * @author Breno Silveira RA:551481
 * @author Camilo Moreira RA:359645
 */
public class Aresta3D {
    
    public Ponto3D inicio, fim;
    
    public Aresta3D(Ponto3D i, Ponto3D f){
        inicio = i;
        fim = f;
    }
    
}
