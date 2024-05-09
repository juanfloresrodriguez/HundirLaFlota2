/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hundirlaflota2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author juane
 */
public class Tablero {
    Scanner sc = new Scanner(System.in);
    
    private int columna;
    private int fila;
    private boolean bandera=true;//Cuando bandera sea false no se podrá seguir atacando y la partida habrá acabado
    private int municion;
    //Tablero iniciado staticamente para pruebas de disparos
    /*private char tablero[][] =  {
                                    {'A','A','B','B','B','B','A','A','A','A'}, 
                                    {'A','A','A','A','A','A','A','A','B','A'}, 
                                    {'A','A','A','A','A','A','A','B','A','A'}, 
                                    {'A','A','A','A','B','A','A','A','A','A'}, 
                                    {'A','A','B','A','A','B','A','A','A','A'}, 
                                    {'A','B','A','A','A','A','B','A','A','A'}, 
                                    {'B','A','A','A','A','A','A','A','B','A'}, 
                                    {'A','A','A','B','A','B','A','A','B','A'}, 
                                    {'A','A','A','A','A','A','A','A','A','A'},
                                    {'A','A','A','A','A','A','A','A','A','A'}
                                };
    */
    private char tablero[][];
    
    
    
    Tablero(int f, int c){
        this.columna= c;
        this.fila=f;
        
        this.municion=3000;//Munición temporal para poder hacer pruebas
        
        //this.municion=(columna*fila)/3;//Calculamos la munición disponible
        this.tablero = new char [f][c];
    }
    
    //GETTER
    public int getN(){
        return this.columna;
    }
    
    public int getM(){
        return this.fila;
    }
    
    public char[][] getTablero(){
        return this.tablero;
    }
    public int getMunicion(){
        return this.municion;
    }
    public boolean getBandera(){
        return this.bandera;
    }
    //SETTER
    public void setC(int c){
        this.columna=c;
    }
    
    public void setF(int f){
        this.fila=f;
    }
    
    public void setTablero(char[][] t){
        this.tablero=t;
    }
    
    
    public void mostrarTablero(){
    //Se mostrará el estado actual del tablero en blanco, conforme avanze la 
    //partida se irá modificando con las casillas bombardeadas
        
    //REPRESENTACIÓN MEDIANTE SWITCH(Cambio automático recomendado por NetBeans)
        for (int i = 0; i < tablero.length; i++) {
            System.out.print('|');
            for (int j = 0; j < tablero[i].length; j++) {
                switch (tablero[i][j]) {
                    case 'x':
                        //Cuando en el array haya una x será poque el ataque ha tocado un barco y mostrará B de barco
                        System.out.print("B");
                        System.out.print('|');
                        break;
                    case 'y':
                        //Cuando en el array haya una y será poque el ataque ha tocado agua y mostrará A de agua
                        System.out.print("A");
                        System.out.print('|');
                        break;
                    default:
                        //El resto significa que no han sido descubiertas esas casillas
                        System.out.print('~');
                        System.out.print('|');
                        break;
                }
            }
            System.out.println();
            
        }
        
        System.out.println("Munición restante: " + municion);
    
    }
    
    private void cambiarCasilla(int f, int c){
        /*
        Comprueba si la casilla seleccionada por el usuario ya ha sido diparada
        o no en caso de no haber sido disparada se cambia el contenido de la misma
        para que mostrarTablero() pueda funcionar.
        */
        
        if(f>=0 && c >= 0){ 
            if(this.tablero[f][c]=='B'){
                this.tablero[f][c]='x';
            }else if(this.tablero[f][c]=='A'){
                this.tablero[f][c]='y';
            }else if(this.tablero[f][c]=='x' || this.tablero[f][c]=='y'){
                System.out.println("No puedes disparar a una casilla ya descubierta"); 
            }
        }
    }
    
    //ATAQUES
    
    public void disparoNormal(){
    //Ataque normal resta 1 bala.
    //Despeja la casilla seleccionada.
        int f, c; //Creamos variables para recoger las casillas que vamos a disparar
    
    
        System.out.println("¿Qué casilla desea atacar?: *Las casillas empiezan en 0*");
        System.out.println("Introduzca la horizontal: ");
        f=sc.nextInt();
        System.out.println("Introduzca la vertical: ");
        c=sc.nextInt();
        
        if(municion>0){
            this.municion--;
            cambiarCasilla(f,c);
        }else {
            System.out.println("No tienes sufuciente munición");
        }
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
            case 1: //FILA
                
                System.out.println("¿Que fila deseas desas despejas?: *Las casillas empiezan en 0*");
                opt=sc.nextInt();

                total=this.columna+2;//restamos el numero de columnas (Longitud de la fila) + 2
                
                if((this.municion - total)>0){//se puede disparar siempre que quede municion
                    
                    for(int i=0;i<tablero[opt].length;i++){//Recorremos la fila seleccionda
                    cambiarCasilla(opt, i);//llamamos al metodo el cual se encarga de cambiar los valores
                    }
                    
                    this.municion-=total;
                }else{
                    System.out.println("No tiene suficiente munición.");
                    bandera=false;
                }
                
                break;
                
            case 2: //COLUMNA

                System.out.println("¿Que fila deseas desas despejas?: *Las casillas empiezan en 0*");
                opt=sc.nextInt();
                
                total=this.fila+2;//restamos el numero de filas (Longitud de la columna + 2
                
                if((this.municion - total)>0){//se puede disparar siempre que quede municion
                    this.municion-=total;
                    
                    for(int i=0;i<tablero.length;i++){
                        cambiarCasilla(i, opt);
                    }
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
 
        if((this.municion - 10)>0){//se puede disparar siempre que quede municion    
        //Preguntamos al usario las casillas a disparar y cambiamos de valor las adyacentes y la disparada
            
            int f, c;
    
            System.out.println("¿Qué casilla desea atacar?: *Las casillas empiezan en 0*");
            System.out.println("Introduzca la horizontal: ");
            f=sc.nextInt();
            System.out.println("Introduzca la vertical: ");
            c=sc.nextInt();
            
            cambiarCasilla(f-1,c-1);//filas superior
            cambiarCasilla(f-1,c);//fila superior
            cambiarCasilla(f-1,c+1);//fila superior
            
            cambiarCasilla(f,c-1);//fila seleccionada
            cambiarCasilla(f,c);//fila seleccionada
            cambiarCasilla(f,c+1);//fila seleccionada
            
            cambiarCasilla(f+1,c-1);//fila inferior
            cambiarCasilla(f+1,c);//fila inferior
            cambiarCasilla(f+1,c+1);//fila inferior

            this.municion-=10;
        }else{
            System.out.println("No tiene suficiente munición.");
            bandera=false;
        }
    }
    
    public void pista(){
    //muestra una parte de un barco con un coste de 15 balas
    
        boolean esPosible=false;
    
        if((this.municion -15)>0){//se puede usar siempre que quede municion
            
            for(int i=0;i<tablero.length;i++){
                while (esPosible!=true){
                    for(int j=0;j<tablero[i].length;j++){
                        if (tablero[i][j]=='B'){
                          cambiarCasilla(j,i);
                          esPosible=true;
                          break;
                        }
                    }
                }   
            }
            
            this.municion-=15;
        }else{
            System.out.println("No tiene suficiente munición.");
            bandera=false;
        }
    }
    
    public void flash() throws InterruptedException{
    //muestra el tablero completo durante medio segundo y lo oculta inmediatamente, con
    //coste de 25 balas
    
    //durante 0.5s y luego haga una limpieza de la pantalla para que no se pueda volver a ver
        if((this.municion -25)>0){//se puede usar siempre que quede municion
                
            for (int i = 0; i < tablero.length; i++) {
                System.out.print('|');
                for (int j = 0; j < tablero[i].length; j++) {
                
                    System.out.print(tablero[i][j]);
                    System.out.print('|');
                }
                System.out.println();
            }
            
            
            Thread.sleep(500);//Paraliza en milisegundos el tiempo establecido
            //--Implementar sistema que limpie la pantalla
            clearConsole();
            
            
            this.municion-=25;
        }else{
            System.out.println("No tiene suficiente munición.");
            bandera=false;
        }
    }
    
    
    //LIMPIAR CONSOLA
    public final static void clearConsole(){
          try 
        {
            String sistemaOperativo = System.getProperty("os.name");
            ArrayList<String> comando= new ArrayList<>();
            if(sistemaOperativo.contains("Windows"))
            {        
                comando.add("cmd");
                comando.add("/C");
                comando.add("cls");  
            } 
            else 
                {
                    comando.add("clear");
                } 
            
            ProcessBuilder pb = new ProcessBuilder(comando);
            Process iniciarProceso = pb.inheritIO().start();
            iniciarProceso.waitFor();   
        }
        catch (IOException | InterruptedException e) 
        {
            System.out.println("Error al limpiar la pantalla"+e.getMessage());//si no es capaz de limpiar la pantalla salta este mensaje
        }
    }
    
    //COLOCACIÓN AUTOMÁTICA DE LOS BARCOS
  
    public boolean comprobarColocacion(int fila, int columna){
        boolean esPosible=false;
        boolean esDentro=false;
        int intento=10;
        
        while(intento>0 && esPosible!=true){
            //-- CREAR FUNCION para espacios entre medias
            
            esPosible=comprobarAlrededor(fila,columna);
            
            esDentro=esDentro(fila,columna);
            if(esDentro==false){//Si es dentro es false(Esta fuera del tablero) rompe la ejecución
                break;
            }
            
            esPosible=hayBarco(fila,columna);
            
            if(hayBarco(fila,columna)==true){//si hay barco en esa posición rompe la ejecución si no lo hubiera cambia es posible a true
                break;
            }else{
                esPosible=true;
            }
            
            
            /*if(this.tablero[fila][columna]!='B'){
                esPosible=true;
            }else{
                esPosible=false;
                break;
            }*/
            
        }
        
        return esPosible;
    }
    
    private boolean hayBarco(int fila, int columna){
        boolean hayBarco=false;
        
        if(this.tablero[fila][columna]!='B'){
                hayBarco=true;
            }else{
                hayBarco=false;
            }
        
        return hayBarco;
    }
    private boolean esDentro(int fila, int columna){//Comprueba si esta dentro del tablero
        boolean esDentro=false;
        if(columna>=0 && fila>=0){
            esDentro=true;
        }else{
            esDentro=false;       
        }
        if(columna<this.columna && fila<this.fila && esDentro!=false){
            esDentro=true;
        }else{
            esDentro=false;
        }
        
        return esDentro;
    }
    
    private boolean comprobarAlrededor(int fila, int columna){
        boolean esPosible=true;
        
        
        //filas superior
        if(esPosible==true){
            esPosible=esDentro(fila-1, columna-1);
            
        }
        if(esPosible==true){
            esPosible=hayBarco(fila-1,columna-1);
        }
        
        //fila superior
        if (esPosible == true) {
            esPosible = esDentro(fila - 1, columna);

        }
        if (esPosible == true) {
            esPosible = hayBarco(fila - 1, columna);
        }
        
        //fila superior
        if(esPosible==true){
            esPosible=esDentro(fila-1, columna+1);
            
        }
        if(esPosible==true){
            esPosible=hayBarco(fila-1,columna+1);
        }
        

        //fila seleccionada
        if(esPosible==true){
            esPosible=esDentro(fila, columna-1);
            
        }
        if(esPosible==true){
            esPosible=hayBarco(fila,columna-1);
        }
        
        //fila seleccionada / CASILLA SELECCIONADA
        if(esPosible==true){
            esPosible=esDentro(fila, columna);
            
        }
        if(esPosible==true){
            esPosible=hayBarco(fila,columna);
        }
        
        //fila seleccionada
        if(esPosible==true){
            esPosible=esDentro(fila, columna+1);
            
        }
        if(esPosible==true){
            esPosible=hayBarco(fila,columna+1);
        }

        //fila inferior
        if(esPosible==true){
            esPosible=esDentro(fila+1, columna-1);
            
        }
        if(esPosible==true){
            esPosible=hayBarco(fila+1,columna-1);
        }
        
        //fila inferior
        if(esPosible==true){
            esPosible=esDentro(fila+1, columna);
            
        }
        if(esPosible==true){
            esPosible=hayBarco(fila+1,columna);
        }
        
        //fila inferior
        if(esPosible==true){
            esPosible=esDentro(fila+1, columna+1);
            
        }
        if(esPosible==true){
            esPosible=hayBarco(fila+1,columna+1);
        }
        
        
        return esPosible;
    }
    
    public boolean colocarBarcos(int size){
        
        int intento=10;
        boolean esPosible=true;
        boolean colocado=false;
                int nc,nf;
        
        while(intento>0 && colocado==false){
            int opt=1;//(int) (Math.random()*8+1);
            int columna=(int) (Math.random()*(this.columna-1));
            int fila=(int) (Math.random()*(this.fila-1));

            
        
            switch(opt){

                case 1: //Colocación hacia arriba
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        esPosible=comprobarColocacion(nf, nc);
                        if(esPosible==true){
                            
                            nf-=1;
                        }else{
                            break;
                        }
                    }
                    
                    if(esPosible==true){
                        for(int i=0;i<size;i++){
                            this.tablero[fila][columna]='B';
                            fila-=1;
                        }
                        colocado=true;
                    }
                    
                    break;
                case 2://Colocación hacia abajo
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        if(esPosible==true){
                            esPosible=comprobarColocacion(nf, nc);
                            nf+=1;
                        }else{
                            break;
                        }
                    }
                    
                    if(esPosible==true){
                        for(int i=0;i<size;i++){
                            this.tablero[fila][columna]='B';
                            fila+=1;
                        }
                    }
                    
                    break;
                case 3://Diagonal Derecha hacia arriba
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        if(esPosible==true){
                            esPosible=comprobarColocacion(nf, nc);
                            nf-=1;
                            nc-=1;
                        }else{
                            break;
                        }
                    }
                    
                    if(esPosible==true){
                        for(int i=0;i<size;i++){
                            this.tablero[fila][columna]='B';
                            fila-=1;
                            columna-=1;
                        }
                    }
                    
                    break;
                case 4://Diagonnal Derecha hacia abajo
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        if(esPosible==true){
                            esPosible=comprobarColocacion(nf, nc);
                            nf+=1;
                            nc+=1;
                        }else{
                            break;
                        }
                    }
                    
                    if(esPosible==true){
                        for(int i=0;i<size;i++){
                            this.tablero[fila][columna]='B';
                            fila+=1;
                            columna+=1;
                        }
                    }
                    
                    break;
                case 5://Diagoanal Izquierda hacia arriba
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        if(esPosible==true){
                            esPosible=comprobarColocacion(nf, nc);
                            nf-=1;
                            nc+=1;
                        }else{
                            break;
                        }
                    }
                    
                    if(esPosible==true){
                        for(int i=0;i<size;i++){
                            this.tablero[fila][columna]='B';
                            fila-=1;
                            columna+=1;
                        }
                    }
                    
                    break;
                case 6://Diagonal Izquierda hacia abajo
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        if(esPosible==true){
                            esPosible=comprobarColocacion(nf, nc);
                            nf+=1;
                            nc-=1;
                        }else{
                            break;
                        }
                    }
                    
                    if(esPosible==true){
                        for(int i=0;i<size;i++){
                            this.tablero[fila][columna]='B';
                            fila+=1;
                            columna-=1;
                        }
                    }
                    
                    break;
                case 7://Hacia la Izquierda
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        if(esPosible==true){
                            esPosible=comprobarColocacion(nf, nc);
                            nc+=1;
                        }else{
                            break;
                        }
                    }
                    
                    if(esPosible==true){
                        for(int i=0;i<size;i++){
                            this.tablero[fila][columna]='B';
                            
                            columna+=1;
                        }
                    }
                    
                    break;
                case 8://Hacia la Derecha
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        if(esPosible==true){
                            esPosible=comprobarColocacion(nf, nc);
                            nc-=1;
                        }else{
                            break;
                        }
                    }
                    
                    if(esPosible==true){
                        for(int i=0;i<size;i++){
                            this.tablero[fila][columna]='B';
                            
                            columna-=1;
                        }
                    }
                    
                    break;
            }
            
            if (esPosible==false){
                intento--;
            }
        }
        colocarAgua();
        
        return esPosible;
    }
    
    //METODO COLOCACIÓN AGUA
    
    private void colocarAgua(){
        for (int i=0;i<tablero.length;i++){
            for (int j=0;j<tablero[i].length;j++){
                if(tablero[i][j]!='B'){
                    tablero[i][j]='A';
                }
            }
        }
    }
    
}
