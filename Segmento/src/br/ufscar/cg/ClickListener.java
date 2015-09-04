package org.yourorghere;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 *Classe criada para manipular os eventos do mouse e capturar os valore de x e y quando
 * ocorrer um clique.
 */
public class ClickListener implements MouseListener{
    private ArrayList< int[]> pontosClicados = new ArrayList<int[]>();
    boolean outroBotao = false;
    
    public int[] getClick(int i){
        return pontosClicados.get(i);
    }
    
    public int size(){
        return pontosClicados.size();
    }
    
    public boolean isLastClickDifferent(){
        return outroBotao;
    }

    public void mouseClicked(MouseEvent e) {
        int p[] = {e.getX(), e.getY()};
        
        if(e.getButton() != 1)
            outroBotao = true;
        else{
            pontosClicados.add(p);
            outroBotao = false;
        }
            
    }

    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {
       
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }
}
