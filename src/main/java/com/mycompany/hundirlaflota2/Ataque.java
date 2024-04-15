/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hundirlaflota2;
import java.util.Scanner;

/**
 *
 * @author juane
 */
public class Ataque {
    Scanner sc = new Scanner(System.in);
    
    private boolean bandera=true;
    private int normal;
    private int barrena;
    private int atomica;
    private int pista;
    private int flash;
    private int fila;
    private int columna;
    private int municion;
    private char[][] tablero;
    
    
    
    Ataque(Tablero otro,int municion){
        this.columna=otro.getN();
        this.fila=otro.getM();
        this.municion=municion;
        this.tablero=otro.getTablero();
    }
    
    //GETTER
    public int getMunicion(){
        return this.municion;
    }
    public boolean getBandera(){
        return this.bandera;
    }
    //TIPOS DE ATAQUES
    public void disparoNormal(){
    //Ataque normal resta 1 bala.
    //Despeja la casilla seleccionada.
    
    //-- Falta codigo que despeje en el tablero
    
        this.municion--;
        
    }
    
    private boolean cambiarCasilla(int f, int c){
        boolean esPosible=true;
        /*
        Comprueba si la casilla seleccionada por el usuario ya ha sido diparada
        o no en caso de no haber sido disparada se cambia el contenido de la misma
        para que t.mostrarTablero() pueda funcionar.
        */
        
        if(this.tablero[f][c]=='B'){
            this.tablero[f][c]='x';
        }else if(this.tablero[f][c]=='A'){
            this.tablero[f][c]='y';
        }else if(this.tablero[f][c]=='x' || this.tablero[f][c]=='y'){
            System.out.println("No puedes disparar a una casilla ya descubierta");
            esPosible=false;
        }
        
        
        
        return esPosible;
    }
    
    public void disparoBarrena(){
    //descubre una fila o columna completa con un coste equivalente al tamaño de
    //la fila o columna + 2 balas
        int opt, total;
        System.out.println("¿Qué desea despejar?: ");
        System.out.println("1. Fila");
        System.out.println("2. Columna");
        opt=sc.nextInt();
        
        switch(opt){
            case 1: 
                //-- introducir codigo para despejar en el tablero
                /*System.out.println("¿Qué fila desea despejar?: (Del 0 al " + this.fila +")");
                opt=sc.nextInt();
                
                for(int i=0;i<this.tablero.length;i++){
                    for (int j=0;j<this.tablero[i].length;j++){
                        this.tablero[opt][j];
                    }
                }*/
                
                total=this.columna+2;//restamos el numero de columnas (Longitud de la fila) + 2
                
                if(this.municion<0){//se puede disparar siempre que quede municion
                    this.municion-=total;
                }else{
                    System.out.println("No tiene suficiente munición.");
                    bandera=false;
                }
                
                break;
                
            case 2:
                
                //-- introducir codigo para despejar en el tablero
                
                total=this.fila+2;//restamos el numero de filas (Longitud de la columna + 2
                
                if(this.municion<0){//se puede disparar siempre que quede municion
                    this.municion-=total;
                }else{
                    System.out.println("No tiene suficiente munición.");
                    bandera=false;
                }
                break;
            default:
                System.out.println("La opción seleccionada no existe.");
                
        }
    }
    
    public void disparoAtomico(){
    //descubre una celda y todas sus adyacentes con un coste de 10 balas
    
    //-- introducir codigo para despejar el tablero
        
        if(this.municion<0){//se puede disparar siempre que quede municion
            this.municion-=10;
        }else{
            System.out.println("No tiene suficiente munición.");
            bandera=false;
        }
    }
    
    public void pista(){
    //muestra una parte de un barco con un coste de 15 balas
    
    //-- implementar código que muestre una parte de un barco
    
        if(this.municion<0){//se puede usar siempre que quede municion
            this.municion-=15;
        }else{
            System.out.println("No tiene suficiente munición.");
            bandera=false;
        }
    }
    
    public void flash(){
    //muestra el tablero completo durante medio segundo y lo oculta inmediatamente, con
    //coste de 25 balas
    
    //-- implementar código el cual muestre todo el tablero como lo ve la máquina
    //durante 0.5s y luego haga una limpieza de la pantalla para que no se pueda volver a ver
        if(this.municion<0){//se puede usar siempre que quede municion
        
            this.municion-=25;
        }else{
            System.out.println("No tiene suficiente munición.");
            bandera=false;
        }
    }
}
