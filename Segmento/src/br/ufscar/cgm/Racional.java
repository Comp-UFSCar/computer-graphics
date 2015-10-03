/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.cgm;

/**
 *
 * @author camilo
 */
public class Racional {
    private final int inteiro;
    private final int numerador;
    private final int denominador;
    
    public Racional(int inteiro, int numerador, int denominador){
                
        if(denominador == 0){
            throw new ArithmeticException("Denominador não pode ser nulo.");
        }
        
        if(denominador < 0){
            denominador *= -1;
            numerador *= -1;
        }
        
        if(numerador >= denominador){
            int tmp = numerador/denominador;
            inteiro += tmp;
            numerador -= tmp*denominador;
        }
        
        int mmc = MMC(Math.abs(numerador),denominador);
        
        if(mmc > 1){
            numerador /= mmc;
            denominador /= mmc;
        }
        
        this.inteiro = inteiro;
        this.numerador = numerador;
        this.denominador = denominador;
    }
    
    private int MMC(int a, int b){
        return (b == 0) ? a : MMC(b, a % b);
    }
    
    @Override
    public String toString(){
        return (String.valueOf(inteiro)
                + getSinal(numerador)
                + String.valueOf(numerador)
                + "/" 
                + String.valueOf(denominador));
    }
    
    private String getSinal(int i){
        if(i >= 0)
            return "+";
        else
            return "";
    }
    
    public Racional soma(Racional r){
        int i = this.inteiro + r.inteiro;
        int n = this.numerador*r.denominador + r.numerador*this.denominador;
        int d = this.denominador*r.denominador;
        
        return new Racional(i, n, d);
    }
    
    public Racional soma(int i){
        return this.soma(new Racional(i,0,1));
    }
    
    public Racional subtrai(Racional r){
        int i = this.inteiro - r.inteiro;
        int n = this.numerador*r.denominador - r.numerador*this.denominador;
        int d = this.denominador*r.denominador;
        
        return new Racional(i, n, d);
    }
    
    public Racional subtrai(int i){
        return this.subtrai(new Racional(i,0,1));
    }
    
    public Racional multiplica(Racional r){
        int i = this.inteiro * r.inteiro;
        int n = this.inteiro*this.denominador*r.numerador
                + this.numerador*r.inteiro*r.denominador + this.numerador*r.numerador;
        int d = this.denominador*r.denominador;
        
        return new Racional(i, n, d);
    }
    
    public Racional multiplica(int i){
        return this.multiplica(new Racional(i,0,1));
    }
    
    public Racional divide(Racional r){
        int n = (this.inteiro*this.denominador + this.numerador)*r.denominador;
        int d = (r.inteiro*r.denominador + r.numerador)*this.denominador;
        
        return new Racional(0, n, d);
    }
    
    public Racional divide(int i){
        return this.divide(new Racional(i,0,1));
    }
    
    public int arredondaParaCima(){
        if(this.numerador > 0)
            return inteiro + 1;
        else
            return inteiro;
    }
    
    public int arredondaParaBaixo(){
        if(this.numerador > 0)
            return inteiro;
        else
            return inteiro - 1;
    }
    
    
}
