package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        Maze maze = new Maze("maze0.txt");
        List<List<CellAuxiliar>> allPaths = maze.allPaths(false);
        for (int i = 0; i < allPaths.size(); i++)
        {
            List<CellAuxiliar> currentPath = allPaths.get(i);
            System.out.println("Cami a la sortida numero " + (i + 1) + ":");
            for (int j = 0; j < currentPath.size(); j++)
            {
                System.out.print(currentPath.get(j));
            }
        }
        System.out.println("\n\n\n\n");
        maze = new Maze("maze1.txt");
	    /*System.out.println(maze.toString());
        List<CellAuxiliar> pathToExit = maze.greedy();
        System.out.println(pathToExit.toString());*/
        try
        {
            bufferedReader.readLine();
        }
        catch (IOException e)
        {

        }

	    allPaths = maze.allPaths(false);
        for (int i = 0; i < allPaths.size(); i++)
        {
            List<CellAuxiliar> currentPath = allPaths.get(i);
            System.out.println("Cami a la sortida numero " + (i + 1) + ":");
            for (int j = 0; j < currentPath.size(); j++)
            {
                System.out.print(currentPath.get(j));
            }
        }

	    /*boolean[][] array = new boolean[5][5];
        for (int i = 0; i < 5; i++)  // visited Matrix initialization
            for (int j = 0; j < 5; j++)
                array[i][j] = false;

        boolean[][] clonat = array.clone();

        array[2][1] = true;
        for (int i = 0; i < 5; i++)
        {
            System.out.print("\n");
            for (int j = 0; j < 5; j++)
            {
                System.out.print(array[i][j] + ", ");
            }
        }

        System.out.println("\ncopia: \n");
        for (int i = 0; i < 5; i++)
        {
            System.out.print("\n");
            for (int j = 0; j < 5; j++)
            {
                System.out.print(clonat[i][j] + ", ");
            }
        }*/

    }
}
