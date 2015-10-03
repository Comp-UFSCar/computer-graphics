package br.ufscar.cgm.preenchimento;


public class ET {
    private No posicoes[];
    
    public ET(int alturaTela)
    {
        this.posicoes = new No[alturaTela];
    }
    
    public void adicionaNo(No nocriado, int posicao)
    {
        if(posicoes[posicao] == null)
            posicoes[posicao] = nocriado;
        else
            posicoes[posicao].setUltimoProximo(nocriado);

    }
    
    public No getNivel(int i){
        return posicoes[i];
    }
    
    public boolean isNivelVazio(int i){
        return (posicoes[i] == null);
    }
    
    public int getTamanho(){
        return posicoes.length;
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