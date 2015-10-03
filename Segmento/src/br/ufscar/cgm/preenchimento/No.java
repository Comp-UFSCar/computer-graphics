
package org.yourorghere;

public class No{
    
    private No proximo;
    Racional Ymax;
    Racional XdoYmin; 
    Racional DXDY;
    
    
    public No(Racional Ymax,Racional XdoYmin, Racional DXDY)
    {
        this.Ymax = Ymax;
        this.XdoYmin = XdoYmin;
        this.DXDY = DXDY;
        this.proximo =null;
    }
    
    public int getXdoYmin()
    {
        return this.XdoYmin.getInteiro();
    }

    public void setProximo(No proximoNo) {
        this.proximo = proximoNo;
    }

    public No getProximo() {
        return this.proximo;
    }
  
}
