package br.ufscar.cgm.preenchimento;


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
            No i = posicoes[posicao];
            
            while(i.getProximo() != null)
            {
                i = i.getProximo();
            }
            
            i.setProximo(nocriado);
            
        }
    }

    public void exibe(){
        for(int i=0; i < posicoes.length; i++){
            if(posicoes[i] != null){
                System.out.println("y="+i);
                No tmp = posicoes[i];
                while(tmp != null){
                    System.out.print(tmp.toString() + " ");
                    tmp = tmp.getProximo();
                }
                System.out.println();
            }
        }
    }
}