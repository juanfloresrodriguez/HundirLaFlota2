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
        int [] tam= {4,3,3,2,2,1,1};
        boolean esPosible;
        boolean barcos=true;

        System.out.println("Introduzca el ancho del tablero:");
        columna=sc.nextInt();
        System.out.println("Introduzca el largo del tablero:");
        fila=sc.nextInt();
        
        //Iniciamos el tablero
        Tablero t = new Tablero(fila, columna);

        for(int i=0;i<tam.length;i++){//Colocación de barcos con la comprobación de si es posible correctamente aplicada
            esPosible=t.colocarBarcos(tam[i]);
            if(esPosible==false){//si no es posible colocar los barcos interumpe la ejecución del programa si es posible la colocación colocará el agua
                System.out.println(i);
                System.out.println("No ha sido posible la colocación de los barcos. Partida Abortada");
                System.exit(0);
            }
        }
        t.colocarAgua(); //Una vez colocado los barcos colocamos el agua en el tablero

        municion=t.getMunicion();
        
        //MENU DE ACCIONES
        
        while(municion>0 && barcos==true){
            //Se ejecuta siempre que el usuario tenga munición y queden barcos en caso de que alguna de las opciones
            //no se cumplan terminará la partida
            t.mostrarTablero();
            
            System.out.println("¿Qué desea hacer?: ");
            System.out.println("1. Disparo Normal (1 Bala)");
            System.out.println("2. Bomba Barrena (Tamaño de la fila o Columna + 2 Balas)");
            System.out.println("3. Bomba Atomica (10 Balas)");
            System.out.println("4. Misil Fragmentación (10 Balas)");
            System.out.println("5. Pista (15 Balas)");
            System.out.println("6. Flash (25 Balas)");
            System.out.println("Para salir introduzca -1");
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
                      t.misilFragmentacion();
                      municion=t.getMunicion();

                      break;
                case 5:
                    t.pista();
                    municion=t.getMunicion();

                    break;
                case 6:
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
            barcos=t.quedanBarcos();
        }
        if (municion<=0){ //Si pierdes por quedarte sin munición
            System.out.println("Game Over: Te has quedado sin munición");
        }else if (opt==-1){//Al finalizar la partida por decisión del jugador (Pulsando -1)
            System.out.println("Has salido de la partida");
        }else{ //Si ganaste hundiendo todos los barcos
            System.out.println("Has Ganado. Hundiste todos los barcos");
        }

    }
}
