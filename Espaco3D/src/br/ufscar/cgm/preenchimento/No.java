package br.ufscar.cgm.preenchimento;

import br.ufscar.cgm.geometria.Aresta3D;
import br.ufscar.cgm.utils.Drawer;
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
    
    No proximo;
    
    int z_max;
    Racional x_do_min, y_do_min;
    int dx, dy, dz;
    Racional dx_dz, dy_dz;
    
    public No(Aresta3D a){
        if(a.inicio == null || a.fim == null)
            throw new ExceptionInInitializerError("Aresta não pode ter pontos vazios.");
        
        dx = (a.inicio.x - a.fim.x);
        dy = (a.inicio.y - a.fim.y);
        dz = (a.inicio.z - a.fim.z);
        
        dx_dz = new Racional(0, dx, dz);
        dy_dz = new Racional(0, dy, dz);
        
        if(a.inicio.z <= a.fim.z){
            x_do_min = new Racional(a.inicio.x*Drawer.precision,0,1);
            y_do_min = new Racional(a.inicio.y*Drawer.precision,0,1);
            z_max = a.fim.z*Drawer.precision;
        } else {
            x_do_min = new Racional(a.fim.x*Drawer.precision,0,1);
            y_do_min = new Racional(a.fim.y*Drawer.precision,0,1);
            z_max = a.inicio.z*Drawer.precision;
        }
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
     * @return Valor de z para o ponto mais alto da aresta.
     */
    public int getZmax() {
        return z_max;
    }

    /**
     * 
     * @return Valor de x para o ponto mais baixo da aresta.
     */
    public Racional getXdoMin() {
        return x_do_min;
    }
    
    public Racional getYdoMin() {
        return y_do_min;
    }

    /**
     * 
     * @return Inclinação da aresta.
     */
    public Racional getDxDz() {
        return dx_dz;
    }
    
        public Racional getDyDz() {
        return dy_dz;
    }

    /**
     * Insere XdoYmin
     * @param XdoYmin Valor de x para o ponto mais baixo da aresta. 
     */
    public void setXdoMin(Racional XdoYmin) {
        this.x_do_min = XdoYmin;
    }
    
    public void setYdoMin(Racional XdoYmin) {
        this.y_do_min = XdoYmin;
    }

    /**
     * Insere DXDY
     * @param DXDY Inclinação da aresta. 
     */
    public void setDxDz(Racional d) {
        this.dx_dz = d;
    }
    
    public void setDyDz(Racional d) {
        this.dy_dz = d;
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
        return "("+z_max+",("+x_do_min+","+y_do_min+"),"+dx_dz+" "+dy_dz+")"+"->"+next;
    }
    
    public String toFullString(){
        String next = null;
        if(proximo != null)
            next = "\n" + proximo.toFullString();
        else
            next = "";
        return "("+z_max+",("+x_do_min+","+y_do_min+"),"+dx_dz+" "+dy_dz+")"+"->"+next;
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
        int xx = this.x_do_min.compareTo(t.getXdoMin());
        int yy = this.y_do_min.compareTo(t.getYdoMin());
        if (xx != 0)
            return xx;
        else
            return yy;
    }
    
  
}
