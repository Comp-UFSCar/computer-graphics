/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.cg;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author camilo.moreira
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
