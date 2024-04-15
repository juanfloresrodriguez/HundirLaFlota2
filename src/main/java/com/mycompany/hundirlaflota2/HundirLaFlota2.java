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
        
        
        System.out.println("Introduzca el ancho del tablero:");
        columna=sc.nextInt();
        System.out.println("Introduzca el largo del tablero:");
        fila=sc.nextInt();
        
        
        //Temporalmente mientras probamos
        //columna=10;
        //fila=10;
        
        
        Tablero t = new Tablero(columna, fila);//Iniciamos el tablero
        t.colocarBarcos(4);
        t.colocarBarcos(3);
        t.colocarBarcos(3);
        t.colocarBarcos(2);
        t.colocarBarcos(2);
        t.colocarBarcos(1);
        t.colocarBarcos(1);
        municion=t.getMunicion();
        
        
        
        System.out.println("Munición restante: "+municion);
        //a.flash();
        //municion=a.getMunicion();
        
        //MENU DE ACCIONES
        
        while(municion>0){
            t.mostrarTablero();
            
            System.out.println("¿Qué desea hacer?: ");
            System.out.println("1. Disparo Normal");
            System.out.println("2. Bomba Barrena");
            System.out.println("3. Bomba Átomica");
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
        
    }
}
