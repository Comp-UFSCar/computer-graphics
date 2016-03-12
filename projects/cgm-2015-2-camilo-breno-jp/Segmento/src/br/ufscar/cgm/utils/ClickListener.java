package br.ufscar.cgm.utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Classe criada para manipular os eventos do mouse.
 * 
 * @author João Paulo
 * @author Breno Silveira
 * @author Camilo Moreira
 */
public class ClickListener implements MouseListener{
    private ArrayList< int[]> pontosClicados = new ArrayList<int[]>();
    boolean outroBotao = false;
    
    /**
    * Método que retorna 0 n-ésimo clique do mouse
    *
    * @param n número do clique para ser recuperado de 0 a n-1 
    * @return retorna um vetor de inteiros com as duas coordenadas do mouse
    *
    */
    public int[] getClick(int n){
        return pontosClicados.get(n);
    }
    
    /**
     * Método que retorna o número total de cliques do mouse
     * 
     * @return número de cliques salvos até o momento
     */
    
    public int getNumeroCliques(){
        return pontosClicados.size();
    }
    
    /**
     * Método que indica se o último clique não foi com o botão esquerdo do mouse
     * @return falso se o último botão clicado foi o botão esquerdo 
     * do mouse e verdadeiro caso contrário (botão do meio ou direito) 
     */
    
    public boolean isLastClickDifferent(){
        return outroBotao;
    }
    
    /**
     * Método que captura o clique do mouse
     * @param e evento do clique do mouse
     */

    public void mouseClicked(MouseEvent e) {
        int p[] = {e.getX(), e.getY()};
        
        if(e.getButton() != 1)
            outroBotao = true;
        else{
            pontosClicados.add(p);
            outroBotao = false;
        }
            
    }
    /**
     * Método não é usado nesta implementação
     * @param e 
     */

    public void mousePressed(MouseEvent e) {
        
    }
    
    /**
     * Método não é usado nesta implementação
     * @param e 
     */

    public void mouseReleased(MouseEvent e) {
       
    }
    
    /**
     * Método não é usado nesta implementação
     * @param e 
     */

    public void mouseEntered(MouseEvent e) {
        
    }
    
    /**
     * Método não é usado nesta implementação
     * @param e 
     */

    public void mouseExited(MouseEvent e) {
        
    }
}
