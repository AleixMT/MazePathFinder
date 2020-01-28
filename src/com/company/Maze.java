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
            exitX = Integer.parseInt(values[5]);  // exit column
            exitY = Integer.parseInt(values[4]);  // exit row
            entryX = Integer.parseInt(values[3]);  // entry column
            entryY = Integer.parseInt(values[2]);  // entry row

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
                        maze[i][j] = new Cell(cellValue[0], Integer.parseInt(cellValue[1]));
                    }
                    catch (NumberFormatException e)
                    {
                        maze[i][j] = new Cell(cellValue[0], 0);
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

    public List<CellAuxiliar> greedy()
    {

        List<CellAuxiliar> path = new ArrayList<>();
        int currentX = entryX, currentY = entryY;
        int points = 1;  // Suponemos 1 punto inicial
        path.add(new CellAuxiliar(maze[currentY][currentX], currentY, currentX));  // Afegim acasella inicial
        for (; exitX != currentX && exitY != currentY;)
        {
            System.out.println("CurrentY: " + currentY + "currentX: " + currentX);
            points = maze[currentY][currentX].applyOperation(points);  // arribem a la casella i ens apliquem op

            List<CellAuxiliar> neighbours = new ArrayList<>();  // Veins del current cell
            // Afegim els veins possibles a la llista
            if (currentX + 1 < sizeX && !maze[currentY][currentX + 1].getOperation().equals("N"))
            {
                neighbours.add(new CellAuxiliar(maze[currentY][currentX + 1], currentY, currentX + 1, euclideanDistance(currentX + 1, currentY, exitX, exitY), maze[currentY][currentX + 1].applyOperation(points)));
            }
            if (currentX - 1 > 0 && !maze[currentY][currentX - 1].getOperation().equals("N"))
            {
                neighbours.add(new CellAuxiliar(maze[currentY][currentX - 1], currentY, currentX - 1, euclideanDistance(currentX - 1, currentY, exitX, exitY), maze[currentY][currentX - 1].applyOperation(points)));
            }
            if (currentY + 1 < sizeY && !maze[currentY + 1][currentX].getOperation().equals("N"))
            {
                neighbours.add(new CellAuxiliar(maze[currentY + 1][currentX], currentY + 1, currentX, euclideanDistance(currentX, currentY + 1, exitX, exitY), maze[currentY + 1][currentX].applyOperation(points)));
            }
            if (currentY - 1 > 0 && !maze[currentY - 1][currentX].getOperation().equals("N"))
            {
                neighbours.add(new CellAuxiliar(maze[currentY - 1][currentX], currentY - 1, currentX, euclideanDistance(currentX, currentY - 1, exitX, exitY), maze[currentY - 1][currentX].applyOperation(points)));
            }

            System.out.println("NEIGHBOURS: " + neighbours);
            int numberOfNeighbours = neighbours.size();  // guardem el nombre de veins en una variable fixa
            List<CellAuxiliar> sortedByDistance = new ArrayList<>();
            double minimum = Float.MIN_VALUE;  // Minim valor
            int pos = -1;  // posicio del seguent element
            for (int i = 0; i < numberOfNeighbours; i++)
            {
                for (int j = 0; j < neighbours.size(); j++)
                {
                    if (neighbours.get(j).getDistance() > minimum)
                    {
                        minimum = neighbours.get(j).getDistance();
                        pos = j;
                    }
                }
                sortedByDistance.add(neighbours.remove(pos));
                minimum = Float.MIN_VALUE;
            }

            System.out.println("\nsortedbyDistance " + sortedByDistance);

            boolean election = false;
            CellAuxiliar currentCell = null;
            while (!election)
            {
                try {
                    currentCell = sortedByDistance.remove(sortedByDistance.size() - 1);  // Treiem el seguent mes proper
                    if (currentCell.getPossiblePoints() > 0) election = true;  // si podem anar sense morir escollim aquesta casella

                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    break;  // SI no queden candidats vol dir que hem mort fem el que fem per tant hem de sortir
                }
            }
            if (!election)
            {
                System.out.println("isDead");
                return null;
            }
            else
            {
                currentX = currentCell.getPosX();
                currentY = currentCell.getPosY();
                //System.out.println(currentY + " " + currentX);
                path.add(currentCell);
            }



        }

        return path;


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
