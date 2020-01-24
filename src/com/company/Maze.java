package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Arrays;

public class Maze {
    private Cell[][] maze;
    private int
            exitX, exitY,
            entryX, entryY,
            sizeX, sizeY;

    public Maze()
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data/maze.txt"));

            String line = bufferedReader.readLine();
            String[] values = line.split(" "), cellValue;
            sizeX = Integer.parseInt(values[0]);
            sizeY = Integer.parseInt(values[1]);
            exitX = Integer.parseInt(values[2]);
            exitY = Integer.parseInt(values[3]);
            entryX = Integer.parseInt(values[4]);
            entryY = Integer.parseInt(values[5]);

            maze = new Cell[sizeX][sizeY];

            for (int i = 0; i < sizeX; i++)
            {
                line = bufferedReader.readLine();
                values = line.split(" ");
                for (int j = 0; j < sizeY; j++)
                {
                    cellValue = values[j].split("");
                    try {
                        maze[i][j] = new Cell(cellValue[0].charAt(0), Integer.parseInt(cellValue[1]));


                    }
                    catch (NumberFormatException e)
                    {
                        maze[i][j] = new Cell(cellValue[0].charAt(0), 0);
                    }
                }
            }



        }
        catch (IOException e)
        {
            System.out.println(e);
        }

    }

    public void findAllPaths()
    {
        int numberOfNodes = sizeX + sizeY;
        //int dist[sizeX * ]
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" +
                "Entrada: (" + entryX + ", " + entryY + ")\n" +
                "Sortida: (" + exitX + ", " + exitY + ")");
        for (int i = 0; i < sizeX; i++)
        {
            stringBuilder.append("\n");
            for (int j = 0; j < sizeY; j++)
            {
                stringBuilder.append(maze[i][j].getOperation());
                stringBuilder.append(maze[i][j].getValue());
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
