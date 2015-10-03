
package br.ufscar.cgm.preenchimento;

import br.ufscar.cgm.Racional;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class No implements Comparable<No> {
    
    private No proximo;
    private int Ymax;
    private Racional XdoYmin; 
    private Racional DXDY;
    
    
    public No(int Ymax, int XdoYmin, Racional DXDY)
    {
        this.Ymax = Ymax;
        this.XdoYmin = new Racional(XdoYmin,0,1);
        this.DXDY = DXDY;
        this.proximo =null;
    }

    public void setProximo(No proximoNo) {
        this.proximo = proximoNo;
    }
    
    public void setUltimoProximo(No proximoNo) {
        No tmp = this;
        while(tmp.proximo != null)
            tmp = tmp.proximo;
        
        tmp.proximo = proximoNo;
    }

    public No getProximo() {
        return this.proximo;
    }

    public int getYmax() {
        return Ymax;
    }

    public Racional getXdoYmin() {
        return XdoYmin;
    }

    public Racional getDXDY() {
        return DXDY;
    }

    public void setXdoYmin(Racional XdoYmin) {
        this.XdoYmin = XdoYmin;
    }

    public void setDXDY(Racional DXDY) {
        this.DXDY = DXDY;
    }
    
    
    
    @Override
    public String toString(){
        boolean next = false;
        if(proximo != null)
            next = true;
        return "("+Ymax+","+XdoYmin+","+DXDY+")"+"->"+next;
    }
    
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
        System.out.println();
        for(int i = 0; i < todos_os_nos.size() - 1; i++){
            todos_os_nos.get(i).setProximo(todos_os_nos.get(i+1));
            System.out.println(todos_os_nos.get(i).XdoYmin);
            
        }
        
        //Atualiza o valor próximo do último nó
        todos_os_nos.get(todos_os_nos.size() - 1).setProximo(null);
        System.out.println(todos_os_nos.get(todos_os_nos.size() - 1).XdoYmin);
        System.out.println();
        
        return todos_os_nos.get(0);
    }

    public int compareTo(No t) {
        return this.XdoYmin.compareTo(t.getXdoYmin());
    }
    
  
}
