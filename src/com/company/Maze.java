package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            sizeX = Integer.parseInt(values[1]);  // columns
            sizeY = Integer.parseInt(values[0]);  // rows
            exitX = Integer.parseInt(values[3]);  // exit column
            exitY = Integer.parseInt(values[2]);  // exit row
            entryX = Integer.parseInt(values[5]);  // entry column
            entryY = Integer.parseInt(values[4]);  // entry row

            maze = new Cell[sizeY][sizeX];

            for (int i = 0; i < sizeY; i++)
            {
                line = bufferedReader.readLine();
                values = line.split(" ");
                for (int j = 0; j < sizeX; j++)
                {
                    cellValue = values[j].split("");
                    try
                    {
                        maze[i][j] = new Cell(cellValue[0], Integer.parseInt(cellValue[1]), i, j);
                    }
                    catch (NumberFormatException e)
                    {
                        maze[i][j] = new Cell(cellValue[0], 0, i, j);
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

    }

    double euclideanDistance(int v1x, int v1y, int v2x, int v2y)
    {
        return Math.sqrt((double)((v1x - v2x) * (v1x - v2x) + (v1y - v2y) * (v1y - v2y)));
    }

    public void greedy()
    {


        int currentX = entryX, currentY = entryY;
        int points = 1;  // Suponemos 1 punto inicial
        for (; points > 0;)
        {
            points = maze[currentY][currentX].applyOperation(points);
            List<Cell> neighbours = new ArrayList<>();
            List<Double> distances = new ArrayList<>();
            List<Integer> possibleFinalPoints = new ArrayList<>();
            if (currentX + 1 < sizeX && !maze[currentY][currentX + 1].getOperation().equals("N"))
            {
                neighbours.add(maze[currentY][currentX + 1]);
                distances.add(euclideanDistance(currentX + 1, currentY, exitX, exitY));
                possibleFinalPoints.add(maze[currentY][currentX + 1].applyOperation(points));
            }
            if (currentX - 1 > 0 && !maze[currentY][currentX - 1].getOperation().equals("N"))
            {
                neighbours.add(maze[currentY][currentX - 1]);
                distances.add(euclideanDistance(currentX - 1, currentY, exitX, exitY));
                possibleFinalPoints.add(maze[currentY][currentX - 1].applyOperation(points));

            }
            if (currentY + 1 < sizeY && !maze[currentY + 1][currentX].getOperation().equals("N"))
            {
                neighbours.add(maze[currentY + 1][currentX]);
                distances.add(euclideanDistance(currentX, currentY + 1, exitX, exitY));
                possibleFinalPoints.add(maze[currentY + 1][currentX].applyOperation(points));
            }
            if (currentY - 1 > 0 && !maze[currentY][currentX - 1].getOperation().equals("N"))
            {
                neighbours.add(maze[currentY - 1][currentX]);
                distances.add(euclideanDistance(currentX, currentY - 1, exitX, exitY));
                possibleFinalPoints.add(maze[currentY - 1][currentX].applyOperation(points));
            }


            for (int i = 0; i < neighbours.size(); i++)
            {
                if (possibleFinalPoints.get(i) <= 0)
                {
                }
            }


        }


    }




    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" +
                "Entrada: (" + entryX + ", " + entryY + ")\n" +
                "Sortida: (" + exitX + ", " + exitY + ")");
        for (int i = 0; i < sizeY; i++)
        {
            stringBuilder.append("\n");
            for (int j = 0; j < sizeX; j++)
            {
                stringBuilder.append(maze[i][j].getOperation());
                stringBuilder.append(maze[i][j].getValue());
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
