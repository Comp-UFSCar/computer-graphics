package org.yourorghere;


public class ET {
    private No posicoes[];
    
    public ET(int alturaTela)
    {
        this.posicoes = new No[alturaTela];
    }
    
    public void AdicionaNo(No nocriado, int posicao)
    {
        if(posicoes[posicao] == null)
            posicoes[posicao] = nocriado;
        else
        {
            No i = posicoes[posicao].getProximo();
            while(i.getProximo() != null)
            {
                i = i.getProximo();
            }
            
            i.setProximo(nocriado);
            
        }
    }
}