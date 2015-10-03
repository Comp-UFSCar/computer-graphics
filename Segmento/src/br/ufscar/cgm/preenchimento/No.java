
package br.ufscar.cgm.preenchimento;

import br.ufscar.cgm.Racional;

public class No{
    
    private No proximo;
    private Racional Ymax;
    private Racional XdoYmin; 
    private Racional DXDY;
    
    
    public No(int Ymax, int XdoYmin, Racional DXDY)
    {
        this.Ymax = new Racional(Ymax,0,1);
        this.XdoYmin = new Racional(XdoYmin,0,1);
        this.DXDY = DXDY;
        this.proximo =null;
    }

    public void setProximo(No proximoNo) {
        this.proximo = proximoNo;
    }

    public No getProximo() {
        return this.proximo;
    }
    
    @Override
    public String toString(){
        boolean next = false;
        if(proximo != null)
            next = true;
        return "("+Ymax+","+XdoYmin+","+DXDY+")"+"->"+next;
    }
  
}
