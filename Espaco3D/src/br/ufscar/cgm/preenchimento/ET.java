package br.ufscar.cgm.preenchimento;

/**
 * Classe que representa a estrutura da tabela de arestas e que também possui a
 * lógica para adicionar e exibir nós. <br><br>
 * 
 * @author João Paulo RA:408034
 * @author Breno Silveira RA:551481
 * @author Camilo Moreira RA:35964
 */
public class ET {
    private No posicoes[];
    
    /**
    * Inicializa uma estrutura de nós com no máximo de entradas igual à altura
    * da tela
    * @param alturaTela altura da tela.
    */
    public ET(int alturaTela)
    {
        this.posicoes = new No[alturaTela];
    }
    
    /**
    * Adiciona um novo nó na linha especificada.
    * @param nocriado Novo nó.
    * @param posicao linha.
    */
    public void adicionaNo(No nocriado, int posicao)
    {
        if(posicoes[posicao] == null)
            posicoes[posicao] = nocriado;
        else
            posicoes[posicao].setUltimoProximo(nocriado);

    }
    
    /**
    * @return Primeiro nó da linha.
    * @param i linha.
    */
    public No getNivel(int i){
        return posicoes[i];
    }
    
    /**
    * @return Se linha não contém entradas na tabela.
    * @param i linha a ser verificada.
    */
    public boolean isNivelVazio(int i){
        return (posicoes[i] == null);
    }
    
    /**
    * @return A quantidade de posições em ET.
    */
    public int getTamanho(){
        return posicoes.length;
    }

    /**
    * Rotina responsável por mostrar uma visualização da ET no output para Debug.
    */
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