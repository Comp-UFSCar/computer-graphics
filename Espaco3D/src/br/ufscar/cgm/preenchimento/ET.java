package br.ufscar.cgm.preenchimento;

import java.util.HashMap;
import java.util.Set;

/**
 * Classe que representa a estrutura da tabela de arestas e que também possui a
 * lógica para adicionar e exibir nós. <br><br>
 * 
 * @author João Paulo RA:408034
 * @author Breno Silveira RA:551481
 * @author Camilo Moreira RA:35964
 */
public class ET {
    
    private HashMap<Integer,No> pos;
    private Integer min = null, max = null;
    
    public ET()
    {
        pos = new HashMap<Integer, No>();
    }
    

    public void adicionaNo(No nocriado, int posicao)
    {

        if(pos.containsKey(posicao))
            pos.get(posicao).setUltimoProximo(nocriado);
        else
            pos.put(posicao, nocriado);

    }
    
    /**
    * @return Primeiro nó da linha.
    * @param i linha.
    */
    public No getNivel(int i){
        if(pos.containsKey(i))
            return pos.get(i);
        else
            return null;
    }
    
    /**
    * @return Se linha não contém entradas na tabela.
    * @param i linha a ser verificada.
    */
    public boolean isNivelVazio(int i){
        return pos.containsKey(i);
    }
    
    /**
    * @return A quantidade de posições em ET.
    */
    public int getTamanho(){
        return max;
    }

    /**
    * Rotina responsável por mostrar uma visualização da ET no output para Debug.
    */
    public void exibe(){
        Set<Integer> s = pos.keySet();
        for(Integer ss: s){
            pos.get(ss);
            System.out.println("z="+ss);
            No n = pos.get(ss);
            System.out.println(n.toString());
            while(n.getProximo() != null){
                n = n.getProximo();
                System.out.println(n.toString());
            }
        }
    }
}