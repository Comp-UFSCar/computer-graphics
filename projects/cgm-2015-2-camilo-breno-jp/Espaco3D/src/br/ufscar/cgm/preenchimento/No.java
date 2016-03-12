package br.ufscar.cgm.preenchimento;

import br.ufscar.cgm.utils.Racional;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe com estrutura básica de entrada para a tabela de arestas.
 * Possui referência para próximo nó da lista encadeada, Ymax da aresta, 
 * XdoYmin e a inclinação.<br><br>
 * 
 * @author João Paulo RA:408034
 * @author Breno Silveira RA:551481
 * @author Camilo Moreira RA:35964
 */
public class No implements Comparable<No> {
    
    private No proximo;
    private int Ymax;
    private Racional XdoYmin; 
    private Racional DXDY;
    
    /**
    * Inicialização da estrutura.
    * @param Ymax Valor de y para o ponto mais alto da aresta.
    * @param XdoYmin Valor de x para o ponto mais baixo da aresta.
    * @param DXDY Inclinação da aresta.
    */
    public No(int Ymax, int XdoYmin, Racional DXDY)
    {
        this.Ymax = Ymax;
        this.XdoYmin = new Racional(XdoYmin,0,1);
        this.DXDY = DXDY;
        this.proximo =null;
    }

    /**
    * Insere referência para próximo nó da lista encadeada.
    * @param proximoNo No a ser referenciado.
    */
    public void setProximo(No proximoNo) {
        this.proximo = proximoNo;
    }
    
    /**
    * Insere nó como último elemento da lista encadeada.
    * @param proximoNo No a ser inserido.
    */
    public void setUltimoProximo(No proximoNo) {
        No tmp = this;
        while(tmp.proximo != null)
            tmp = tmp.proximo;
        
        tmp.proximo = proximoNo;
    }

    /**
    * @return Próximo nó da lista encadeada.
    */
    public No getProximo() {
        return this.proximo;
    }

    /**
     * @return Valor de y para o ponto mais alto da aresta.
     */
    public int getYmax() {
        return Ymax;
    }

    /**
     * 
     * @return Valor de x para o ponto mais baixo da aresta.
     */
    public Racional getXdoYmin() {
        return XdoYmin;
    }

    /**
     * 
     * @return Inclinação da aresta.
     */
    public Racional getDXDY() {
        return DXDY;
    }

    /**
     * Insere XdoYmin
     * @param XdoYmin Valor de x para o ponto mais baixo da aresta. 
     */
    public void setXdoYmin(Racional XdoYmin) {
        this.XdoYmin = XdoYmin;
    }

    /**
     * Insere DXDY
     * @param DXDY Inclinação da aresta. 
     */
    public void setDXDY(Racional DXDY) {
        this.DXDY = DXDY;
    }
    
    /**
     * 
     * @return Representação em texto do nó.
     */
    @Override
    public String toString(){
        boolean next = false;
        if(proximo != null)
            next = true;
        return "("+Ymax+","+XdoYmin+","+DXDY+")"+"->"+next;
    }
    
    /**
     * Ordena linha de AET
     * @param n Nó a partir do qual deve-se ser ordenado.
     * @return Todos os nós.
     */
    public static No ordena(No n){
        if(n == null)
            return null;
        
        List<No> todos_os_nos = new ArrayList<No>();
        while(n != null){
            todos_os_nos.add(n);
            n = n.getProximo();
        }
        //Ordena o ArrayList pelo valor XdoYmin
        Collections.sort(todos_os_nos);
        //Atualiza o próximo de cada nó
        for(int i = 0; i < todos_os_nos.size() - 1; i++)
            todos_os_nos.get(i).setProximo(todos_os_nos.get(i+1));   
        //Atualiza o próximo do último nó
        todos_os_nos.get(todos_os_nos.size() - 1).setProximo(null);
        
        return todos_os_nos.get(0);
    }

    /**
     * 
     * @param t Nó a ser comparado.
     * @return Comparação entre nós.
     */
    public int compareTo(No t) {
        return this.XdoYmin.compareTo(t.getXdoYmin());
    }
    
  
}