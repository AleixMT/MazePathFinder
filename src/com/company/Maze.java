package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Maze {
    private Cell[][] maze;
    private int exitX, exitY, entryX, entryY, sizeX, sizeY;

    public Maze()
    {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            String line = bufferedReader.readLine();
            String[] values = line.split(" ");
            sizeX = Integer.parseInt(values[0]);
            sizeY = Integer.parseInt(values[1]);
            exitX = Integer.parseInt(values[2]);
            exitY = Integer.parseInt(values[3]);
            entryX = Integer.parseInt(values[4]);
            entryY = Integer.parseInt(values[5]);

            maze = new Cell[sizeX][sizeY];

            while ()



        }
        catch (IOException e)
        {
            System.out.println(e);
        }

    }
}
