/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hundirlaflota2;
import java.util.Scanner;


/**
 *
 * @author juane
 */
public class HundirLaFlota2 {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        
        int columna, fila;
        int opt=0;
        int municion;
        boolean esPosible=true;
        
        System.out.println("Introduzca el ancho del tablero:");
        columna=sc.nextInt();
        System.out.println("Introduzca el largo del tablero:");
        fila=sc.nextInt();
        
        //Iniciamos el tablero
        
        Tablero t = new Tablero(fila, columna);
        
        esPosible=t.colocarBarcos(4);
        esPosible=t.colocarBarcos(3);
        esPosible=t.colocarBarcos(3);
        esPosible=t.colocarBarcos(2);
        esPosible=t.colocarBarcos(2);
        esPosible=t.colocarBarcos(1);
        esPosible=t.colocarBarcos(1);
        if(esPosible!=true){//si no es posible colocar los barcos interumpe la ejecución del programa si es posible la colocación colocará el agua
            System.out.println("No ha sido posible la colocación de los barcos. Partida Abortada");
            System.exit(0);
        }else{
            t.colocarAgua();
        }
        
        
        municion=t.getMunicion();
        
        //MENU DE ACCIONES
        
        while(municion>0){
            t.mostrarTablero();
            
            System.out.println("¿Qué desea hacer?: ");
            System.out.println("1. Disparo Normal");
            System.out.println("2. Bomba Barrena");
            System.out.println("3. Bomba Atomica");
            System.out.println("4. Pista");
            System.out.println("5. Flash");
            opt=sc.nextInt();
                       
            if(opt!=-1){
                switch(opt){
                case 1:
                    t.disparoNormal();
                    municion=t.getMunicion();
                     
                    break;
                case 2:
                    t.disparoBarrena();
                    municion=t.getMunicion();
                    
                    break;
                case 3:
                    t.disparoAtomico();
                    municion=t.getMunicion();
                     
                    break;
                case 4:
                    t.pista();
                    municion=t.getMunicion();
                     
                    break;
                case 5:
                    t.flash();
                    municion=t.getMunicion();
                     
                    break;
                default:
                    System.out.println("No existe. Vuelva a intentarlo");
                     
                }
            }else{
                System.out.println("Partida finalizada por el jugador");
                break;
            }
            
        }
        if (municion<0){
            System.out.println("Game Over: Te has quedado sin munición");
        }else if (opt==-1){
            System.out.println("Has salido de la partida");
        }else{
            System.out.println("Has Ganado. Hundiste todos los barcos");
        }
        
    }
}
