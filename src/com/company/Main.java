package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner teclat = new Scanner(System.in);

    public static void main(String[] args) {

        Maze maze = null;

        int opt;
        long ti = 0, tf = 0; // Temps per a mesurar l'eficiencia de l'algorisme
        while (true)  // Sortirem del programa amb la opcio del menu
        {
            System.out.println("Quina operacio vols fer? Introdueix el nombre per escollir");
            System.out.println("0.- Escollir fitxer a carrega.");
            if (maze != null)
            {
                System.out.println("1.- Mostrar laberint");
                System.out.println("2.- Executar la solucio greedy sobre el laberint");
                System.out.println("3.- Executar la solucio de recursio per a trobar UN sol cami");
                System.out.println("4.- Executar la solució de recursió per a trobar tots els camins possibles");
                System.out.println("5.- Executar la solució de recursió amb ramificació i poda");
                System.out.println("6.- Sortir");
            }

            try
            {
                opt = teclat.nextInt();
                teclat.nextLine();  // Clean the buffer
                if (opt > 0 && maze == null) opt = -1;  // If we didn't load the maze we cant execute other options
                switch (opt) {
                    case 0:
                        System.out.print("Introdueix numero de laberint: ");
                        int numLaberint = teclat.nextInt();
                        teclat.nextLine();

                        ti = System.nanoTime();
                        maze = new Maze("maze" + numLaberint + ".txt");
                        tf = System.nanoTime();

                        break;
                    case 1:
                        ti = System.nanoTime();	// Inicialització
                        System.out.println(maze);
                        tf = System.nanoTime();	// Parem cronometre

                        break;
                    case 2:
                        ti = System.nanoTime();	// Inicialització
                        List<CellAuxiliar> possiblePathToExit = maze.greedy();     // Search a greedy path
                        tf = System.nanoTime();	// Parem cronometre

                        System.out.println(possiblePathToExit.toString());
                        break;
                    case 3:
                        ti = System.nanoTime();  // Inicialització
                        List<CellAuxiliar> oneOfAllPaths = maze.allPaths(true).get(0);  // Search only one path while recursively exploring all possibilities
                        tf = System.nanoTime();	 // Parem cronometre

                        for (CellAuxiliar cell : oneOfAllPaths)
                            System.out.print(cell);
                        break;
                    case 4:
                        ti = System.nanoTime();  // Inicialització
                        // Search all paths
                        List<List<CellAuxiliar>> allPaths = maze.allPaths(false);
                        tf = System.nanoTime();	 // Parem cronometre

                        for (int j = 0; j < allPaths.size(); j++)
                        {
                            System.out.println("Cami a la sortida numero " + (j + 1) + ":\n" + allPaths.get(j));
                        }
                        break;
                    case 5:

                        ti = System.nanoTime();	// Inicialització
                        //
                        tf = System.nanoTime();	// Parem cronometre
                        break;
                    case 6:
                        ti = System.nanoTime();	// Inicialització
                        System.exit(0); //termina l'aplicacio que s'esta executant al moment
                        tf = System.nanoTime();	// Parem cronometre
                        break;
                    default: System.out.println("\nAquesta opcio no esta a la llista... \n");
                        break;	//Funciona com una excepcio per a un valor numeric no acceptat
                }
                System.out.println("\nHa tardat " + (tf - ti) / Math.pow(10, 9)+ " segons");
            }
            catch (InputMismatchException e)
            {
                System.out.println("Exceptions.InputMismatchException: ERROR:Has introduit una opcio incorrecta, torna-ho a intentar \n");
                teclat.nextLine();
            }
        }
    }
}
