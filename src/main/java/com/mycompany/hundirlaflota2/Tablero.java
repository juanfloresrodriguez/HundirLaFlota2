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
    private char[][] tablero;
    
    
    
    Tablero(int f, int c){
        this.columna= c;
        this.fila=f;

        this.municion=(columna*fila)/3;//Calculamos la munición disponible
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
    
    
    public void mostrarTablero() {
    //Se mostrará el estado actual del tablero en blanco, conforme avance la
    //partida se irá modificando con las casillas bombardeadas

        System.out.print("  ");  // Espacio para la esquina superior izquierda
        for (int j = 0; j < tablero[0].length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < tablero.length; i++) {
            System.out.print(i + " ");  // Imprimir el número de fila
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print('|');
                switch (tablero[i][j]) {
                    case 'x':
                        // Cuando en el array haya una x será porque el ataque ha tocado un barco y mostrará B de barco
                        System.out.print("B");
                        break;
                    case 'y':
                        // Cuando en el array haya una y será porque el ataque ha tocado agua y mostrará A de agua
                        System.out.print("A");
                        break;
                    default:
                        // El resto significa que no han sido descubiertas esas casillas
                        System.out.print('~');
                        break;
                }
            }
            System.out.print('|');
            System.out.println();
        }
        System.out.println("Munición restante: " + municion);
    }

    private void cambiarCasilla(int f, int c) {
        /*
        Comprueba si la casilla seleccionada por el usuario ya ha sido disparada
        o no en caso de no haber sido disparada se cambia el contenido de la misma
        para que mostrarTablero() pueda funcionar.
        */
        if(!esDentro(f,c)) {

        }else if(f>=0 && c >= 0){
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
        
        if(municion>0 && esDentro(f,c)){
            this.municion--;
            cambiarCasilla(f,c);
        }else {
            System.out.println("No tiene suficiente munición O Has seleccionado una posición fuera del tablero.");
        }
    }
   
    
    public void disparoBarrena() {
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

                total=this.columna+2;//restamos el número de columnas (Longitud de la fila) + 2
                
                if((this.municion - total)>0 && esDentro(opt, 1)){//se puede disparar siempre que quede municion
                    
                    for(int i=0;i<tablero[opt].length;i++){//Recorremos la fila seleccionada
                    cambiarCasilla(opt, i);//llamamos al método el cual se encarga de cambiar los valores
                    }
                    
                    this.municion-=total;
                }else{
                    System.out.println("No tiene suficiente munición O Has seleccionado una posición fuera del tablero.");
                    bandera=false;
                }

                break;
                
            case 2: //COLUMNA

                System.out.println("¿Que columna deseas desas despejas?: *Las casillas empiezan en 0*");
                opt=sc.nextInt();
                
                total=this.fila+2;//restamos el número de filas (Longitud de la columna + 2
                
                if((this.municion - total)>0 && esDentro(1, opt)){//se puede disparar siempre que quede municion
                    this.municion-=total;
                    
                    for(int i=0;i<tablero.length;i++){
                        cambiarCasilla(i, opt);
                    }
                }else{
                    System.out.println("No tiene suficiente munición O Has seleccionado una posición fuera del tablero.");
                    bandera=false;
                }
                break;
            default:
                System.out.println("La opción seleccionada no existe.");
        }
    }
    
    public void disparoAtomico(){
    //Descubre una celda y todas sus adyacentes con un coste de 10 balas
        int f, c;

        System.out.println("¿Qué casilla desea atacar?: *Las casillas empiezan en 0*");
        System.out.println("Introduzca la horizontal: ");
        f=sc.nextInt();
        System.out.println("Introduzca la vertical: ");
        c=sc.nextInt();

        if((this.municion - 10)>0 && esDentro(f,c)){//se puede disparar siempre que quede municion
        //Preguntamos al usuario las casillas a disparar y cambiamos de valor las adyacentes y la disparada

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
        } else if (!esDentro(f,c)){
            System.out.println("Esa posición no esta dentro del tablero");
            bandera=false;
        } else{
            System.out.println("No tiene suficiente munición ");
            bandera=false;
        }
    }

    public void misilFragmentacion(){
        /*
        Esta arma devastadora se lanza
        sobre unas coordenadas concretas (Fila y Columna) y tiene un efecto parecido a la bomba atómica
        pero más amplio y diseminado.
         */
        int f, c;

        System.out.println("¿Qué casilla desea atacar?: *Las casillas empiezan en 0*");
        System.out.println("Introduzca la horizontal: ");
        f=sc.nextInt();
        System.out.println("Introduzca la vertical: ");
        c=sc.nextInt();

        if((this.municion - 10)>0 && esDentro(f,c)){//se puede disparar siempre que quede municion
            //Preguntamos al usuario las casillas a disparar y cambiamos de valor las adyacentes y la disparada

            cambiarCasilla(f,c-1);
            cambiarCasilla(f+2,c-2);

            cambiarCasilla(f+2,c);
            cambiarCasilla(f+2,c+2);
            cambiarCasilla(f,c+1);

            cambiarCasilla(f+2,c+2);
            cambiarCasilla(f-2,c-2);
            cambiarCasilla(f-2,c);
            cambiarCasilla(f-2,c+2);

            this.municion-=10;
        } else if (!esDentro(f,c)){
            System.out.println("Esa posición no esta dentro del tablero");
            bandera=false;
        } else{
            System.out.println("No tiene suficiente munición ");
            bandera=false;
        }
    }
    
    public void pista(){
    //Muestra una parte de un barco con un coste de 15 balas
        boolean esPosible=false;
    
        if((this.municion -15)>0){//se puede usar siempre que quede municion

            for(int i=0;i<tablero.length;i++){
                    
                for(int j=0;j<tablero[i].length;j++){
                    if (tablero[i][j]=='B'){
                        cambiarCasilla(i,j);
                        esPosible=true;
                        break;
                    }
                }
                if(esPosible==true){
                    break;
                }
            }
            
            this.municion-=15;
        }else{
            System.out.println("No tiene suficiente munición.");
            bandera=false;
        }
    }
    
    public void flash() throws InterruptedException{
    //Muestra el tablero completo durante medio segundo y lo oculta inmediatamente, con
    //coste de 25 balas
    
    //durante 0.5 s y luego haga una limpieza de la pantalla para que no se pueda volver a ver
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
            clearConsole();

            this.municion-=25;
        }else{
            System.out.println("No tiene suficiente munición.");
            bandera=false;
        }
    }
    
    
    //LIMPIAR CONSOLA
    public static void clearConsole(){
        try {
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
        catch (IOException | InterruptedException e) {
            System.out.println("Error al limpiar la pantalla"+e.getMessage());//si no es capaz de limpiar la pantalla salta este mensaje
        }
    }

    //CONTAR CUANTOS BARCOS QUEDAN PARA ACABAR PARTIDA
    public boolean quedanBarcos(){
        boolean quedan=false;
        int cont=0;

        for(int i=0;i<tablero.length;i++){
            for(int j=0;j<tablero[i].length;j++){
                if(tablero[i][j]=='B'){
                    cont++;
                }
            }
        }
        if(cont>0){
            quedan=true;
        }
        return quedan;
    }

    //COLOCACION AUTOMATICA DE LOS BARCOS
  
    public boolean comprobarColocacion(int fila, int columna) {
        //Comprueba la viabilidad de la colocación de los barcos en el tablero, comprobando si la posición seleccionada
        //automáticamente está dentro del tablero, si existe un barco en esa posición y por último comprueba
        //si hay barcos en las casillas adyacentes
        boolean esPosible=true;

        boolean alrededor;

        while(esPosible==true){

            esPosible=esDentro(fila,columna);
            if(esPosible==false){//Si es dentro es false(Esta fuera del tablero) rompe la ejecución
                break;
            }
            
            if(hayBarco(fila,columna)==true){//si hay barco en esa posición rompe la ejecución si no lo hubiera cambia es posible a true
                break;
            }else{
                esPosible=true;
            }
            
            alrededor=comprobarAlrededor(fila,columna);
            
            if(alrededor==false){
                esPosible=false;
            }

            if (esPosible==true){
                break;
            }
        }
        return esPosible;
    }
    
    private boolean hayBarco(int fila, int columna){
        //Comprueba si existe un barco en la casilla seleccionada
        boolean resul;

        if(this.tablero[fila][columna]=='B'){
            resul=false;
        }else{
            resul=true;
        }
        return resul;
    }

    private boolean esDentro(int fila, int columna){
        //Comprueba si está dentro del tablero
        boolean esDentro;
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
    
    private boolean comprobarAlrededor(int fila, int columna) {
        //comprueba la posibilidad de colocación en las casillas adyacentes
        boolean esPosible = true;

        //filas superior
        if (esDentro(fila-1, columna-1) && hayBarco(fila-1, columna-1)){
            esPosible=false;
        }
        //fila superior
        if(esPosible==true && esDentro(fila-1,columna) && hayBarco(fila-1,columna)){
            esPosible=false;
        }
        //fila superior
        if(esPosible==true && esDentro(fila-1, columna+1) && hayBarco(fila-1, columna+1)){
            esPosible = false;
        }
        //fila seleccionada
        if(esPosible==true && esDentro(fila, columna -1) && hayBarco(fila,columna-1)){
            esPosible = false;
        }

        //fila seleccionada
        if(esPosible==true && esDentro(fila,columna+1) && hayBarco(fila,columna+1)){
            esPosible = false;
        }

        //fila inferior
        if(esPosible==true && esDentro(fila+1, columna-1) && hayBarco(fila+1,columna-1)){
            esPosible = false;
        }

        //fila inferior
        if(esPosible == true && esDentro(fila + 1, columna) && hayBarco(fila + 1, columna)) {
            esPosible = false;
        }

        //fila inferior
        if(esPosible==true && esDentro(fila + 1, columna + 1) && hayBarco(fila+1,columna+1)) {
            esPosible = false;
        }
        return esPosible;
    }


    public boolean colocarBarcos(int size){
        /*
        Genera aleatoriamente una opción (Dirección de colocación), una columna y una fila.
        Se cuenta con 10 intentos, primero se comprueba la viabilidad de la colocación en caso de no ser posible se
        resta un intento y vuelve a generar otras opciones, en caso de poderse colocar se procede a colocar los barcos
        en el tablero.
         */
        int intento=10;
        boolean esPosible=true;
        boolean colocado=false;
        int nc,nf;
        
        while(intento>0 && colocado==false){
            int opt=(int) (Math.random()*4+1);
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
                        esPosible=comprobarColocacion(nf, nc);
                        
                        if(esPosible==true){
                            
                            nf+=1;
                        }else {
                            break;
                        }
                    }
                    
                    if(esPosible==true){
                        for(int i=0;i<size;i++){
                            this.tablero[fila][columna]='B';
                            fila+=1;
                        }
                        colocado=true;
                    }
                    
                    break;
//                case 3://Diagonal Derecha hacia arriba
//                    nc=columna;
//                    nf=fila;
//                    for(int i=0;i<size;i++){
//                        esPosible=comprobarColocacion(nf, nc);
//                        if(esPosible==true){
//
//                            nf-=1;
//                            nc-=1;
//                        }else{
//                            break;
//                        }
//                    }
//
//                    if(esPosible==true){
//                        for(int i=0;i<size;i++){
//                            this.tablero[fila][columna]='B';
//                            fila-=1;
//                            columna-=1;
//                        }
//                        colocado=true;
//                    }
//
//                    break;
//                case 4://Diagonal Derecha hacia abajo
//                    nc=columna;
//                    nf=fila;
//                    for(int i=0;i<size;i++){
//                        esPosible=comprobarColocacion(nf, nc);
//                        if(esPosible==true){
//
//                            nf+=1;
//                            nc+=1;
//                        }else {
//                            break;
//                        }
//                    }
//
//                    if(esPosible==true){
//                        for(int i=0;i<size;i++){
//                            this.tablero[fila][columna]='B';
//                            fila+=1;
//                            columna+=1;
//                        }
//                        colocado=true;
//                    }
//
//                    break;
//                case 5://Diagoanal Izquierda hacia arriba
//                    nc=columna;
//                    nf=fila;
//                    for(int i=0;i<size;i++){
//                        esPosible=comprobarColocacion(nf, nc);
//                        if(esPosible==true){
//
//                            nf-=1;
//                            nc+=1;
//                        }else{
//                            break;
//                        }
//                    }
//
//                    if(esPosible==true){
//                        for(int i=0;i<size;i++){
//                            this.tablero[fila][columna]='B';
//                            fila-=1;
//                            columna+=1;
//                        }
//                        colocado=true;
//                    }
//
//                    break;
//                case 6://Diagonal Izquierda hacia abajo
//                    nc=columna;
//                    nf=fila;
//                    for(int i=0;i<size;i++){
//                        esPosible=comprobarColocacion(nf, nc);
//                        if(esPosible==true){
//
//                            nf+=1;
//                            nc-=1;
//                        }else{
//                            break;
//                        }
//                    }
//
//                    if(esPosible==true){
//                        for(int i=0;i<size;i++){
//                            this.tablero[fila][columna]='B';
//                            fila+=1;
//                            columna-=1;
//                        }
//                        colocado=true;
//                    }
                    
//                    break;
                case 3://Hacia la Izquierda
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        esPosible=comprobarColocacion(nf, nc);
                        if(esPosible==true){
                            
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
                        colocado=true;
                    }
                    
                    break;
                case 4://Hacia la Derecha
                    nc=columna;
                    nf=fila;
                    for(int i=0;i<size;i++){
                        esPosible=comprobarColocacion(nf, nc);
                        if(esPosible==true){
                            
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
                        colocado=true;
                    }
                    
                    break;
            }
            
            if (esPosible==false){
                intento--;
            }
        }

        return esPosible;
    }
    
    //METODO COLOCACIÓN AGUA
    
    public void colocarAgua(){
        for (int i=0;i<tablero.length;i++){
            for (int j=0;j<tablero[i].length;j++){
                if(tablero[i][j]!='B'){
                    tablero[i][j]='A';
                }
            }
        }
    }
    
}
