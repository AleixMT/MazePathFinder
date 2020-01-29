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

    public Maze(String textFile)
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("data/" + textFile));

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

    /*
     * Auxiliar function to deepcopy an array of booleans and return it.
     * Should be static function but does not cause conflict if being instance method
     */
    public boolean[][] deepCopy(boolean[][] array2d)
    {
        boolean[][] newArray = new boolean[array2d.length][array2d[0].length];
        for (int i = 0; i < array2d.length; i++)
        {
            for (int j = 0; j < array2d[0].length; j++)
            {
                newArray[i][j] = array2d[i][j];
            }
        }
        return newArray;
    }

    /*
     * Auxiliar function to calculate the euclidian distance between two cartesian 2d points.
     * Should be static function but does not cause conflict if being instance method
     */
    double euclideanDistance(int v1x, int v1y, int v2x, int v2y)
    {
        //System.out.println(Math.sqrt((double)((v1x - v2x) * (v1x - v2x) + (v1y - v2y) * (v1y - v2y))));
        return Math.sqrt((double)((v1x - v2x) * (v1x - v2x) + (v1y - v2y) * (v1y - v2y)));
    }

    /*
     * Recursive private function to locate all paths to find the exit in the maze
     */
    private void allPathsRec(int currentY, int currentX, List<CellAuxiliar> currentPath, int points, List<List<CellAuxiliar>> paths, boolean[][] visitedMatrix, WrapperInt stop)
    {
        if (exitX == currentX && exitY == currentY)  // If we reach the end of the maze, write down followed route and escape
        {
            currentPath.add(new CellAuxiliar(maze[currentY][currentX], currentY, currentX, euclideanDistance(currentX, currentY, exitX, exitY), maze[currentY][currentX].applyOperation(points)));  // Apuntem
            paths.add(currentPath);  // Add this route to the paths list
            if (stop.getValue() == 1) stop.setValue(2);  // Enviem senyal a la resta de fils per a que retornin
            return;  // End of recursivity because reached the exit
        }
        else
        {
            visitedMatrix[currentY][currentX] = true;  // Set current position as visited
            points = maze[currentY][currentX].applyOperation(points);  // Update points using the value of the current cell
            currentPath.add(new CellAuxiliar(maze[currentY][currentX], currentY, currentX, euclideanDistance(currentX, currentY, exitX, exitY), points));  // Add the current cell to the currentpath
        }
        if (points > 0)  // If we reach 0 points we can't move, so we will return the recursivity call
        {  // Recursive cases
            if (currentX + 1 < sizeX && !maze[currentY][currentX + 1].getOperation().equals("N") && !visitedMatrix[currentY][currentX + 1] && stop.getValue() != 2)
                allPathsRec(currentY, currentX + 1, (ArrayList)((ArrayList<CellAuxiliar>)currentPath).clone(), points, paths, deepCopy(visitedMatrix), stop);
            if (currentX - 1 > 0 && !maze[currentY][currentX - 1].getOperation().equals("N") && !visitedMatrix[currentY][currentX - 1] && stop.getValue() != 2)
                allPathsRec(currentY, currentX - 1, (ArrayList)((ArrayList<CellAuxiliar>)currentPath).clone(), points, paths, deepCopy(visitedMatrix), stop);
            if (currentY + 1 < sizeY && !maze[currentY + 1][currentX].getOperation().equals("N") && !visitedMatrix[currentY + 1][currentX] && stop.getValue() != 2)
                allPathsRec(currentY + 1, currentX, (ArrayList)((ArrayList<CellAuxiliar>)currentPath).clone(), points, paths, deepCopy(visitedMatrix), stop);
            if (currentY - 1 > 0 && !maze[currentY - 1][currentX].getOperation().equals("N") && !visitedMatrix[currentY - 1][currentX] && stop.getValue() != 2)
                allPathsRec(currentY - 1, currentX, (ArrayList)((ArrayList<CellAuxiliar>)currentPath).clone(), points, paths, deepCopy(visitedMatrix), stop);
        }

        // If we reach this part of the code means that one "thread" got trapped because can't visit other cells or because has not enough points
        // return without do nothing since it's a non-factible path
    }

    /*
     * This is the accessible method to calculate all paths. We did it like this because in the first iteration
     * we need to set up all the data structures to run the algorithm.
     * Memory is not optimized since we create copies of the objects in every iteration. When derreferenced
     * is work of the garbage collector
     */
    public List<List<CellAuxiliar>> allPaths(boolean stopWhenOnePathFound)
    {
        List<List<CellAuxiliar>> paths = new ArrayList<>();  // All possible paths to the exit. All threads will put their results in here
        List<CellAuxiliar> currentPath = new ArrayList<>();  // Path that is exploring the current thread
        int currentX = entryX, currentY = entryY, points = 1;  // Suponemos 1 punto inicial
        WrapperInt stop = new WrapperInt(0);
        boolean[][] visitedMatrix = new boolean[sizeY][sizeX];  // Matriz para tener en cuenta las celdas ya visitadas
        for (int i = 0; i < sizeY; i++)  // visited Matrix initialization
            for (int j = 0; j < sizeX; j++)
                visitedMatrix[i][j] = false;

        visitedMatrix[currentY][currentX] = true;  // set the current position as visited
        points = maze[currentY][currentX].applyOperation(points);
        currentPath.add(new CellAuxiliar(maze[currentY][currentX], currentY, currentX, euclideanDistance(currentX, currentY, exitX, exitY), points));  // Afegim acasella inicial + dades extra

        // Stop is a behaviour modificator parameter and also a flag to exit the method when one path is found
        if (stopWhenOnePathFound) stop.setValue(1);
        else stop.setValue(0);
        /*
         * DADES EN LA CRIDA RECURISVA
         * Passem referencia directa als camins (paths), ja que sempre que modifiquem aquesta llista sera per afegir, de forma que no es causa conflicte
         * Passem una shallow copy del current path perque així fem una copia de l'ordre que hi ha a la llista pero no dels objecte Cell (que no cal copiar perque son constants)
         * Passem una deepcopy de la matriu de visitats perque una shallow copy copiaria les referencies dels subarrays degut a que es un array2d, i el que volem son tots els valors
         */
        if (currentX + 1 < sizeX && !maze[currentY][currentX + 1].getOperation().equals("N") && stop.getValue() != 2 && points > 0)
            allPathsRec(currentY, currentX + 1, (ArrayList)((ArrayList<CellAuxiliar>)currentPath).clone(), points, paths, deepCopy(visitedMatrix), stop);
        if (currentX - 1 > 0 && !maze[currentY][currentX - 1].getOperation().equals("N") && stop.getValue() != 2 && points > 0)
            allPathsRec(currentY, currentX - 1, (ArrayList)((ArrayList<CellAuxiliar>)currentPath).clone(), points, paths, deepCopy(visitedMatrix), stop);
        if (currentY + 1 < sizeY && !maze[currentY + 1][currentX].getOperation().equals("N") && stop.getValue() != 2 && points > 0)
            allPathsRec(currentY + 1, currentX, (ArrayList)((ArrayList<CellAuxiliar>)currentPath).clone(), points, paths, deepCopy(visitedMatrix), stop);
        if (currentY - 1 > 0 && !maze[currentY - 1][currentX].getOperation().equals("N") && stop.getValue() != 2 && points > 0)
            allPathsRec(currentY - 1, currentX, (ArrayList)((ArrayList<CellAuxiliar>)currentPath).clone(), points, paths, deepCopy(visitedMatrix), stop);

        return paths;
    }

    public List<CellAuxiliar> greedy()
    {

        List<CellAuxiliar> path = new ArrayList<>();
        int currentX = entryX, currentY = entryY;
        int points = 1;  // Suponemos 1 punto inicial
        CellAuxiliar currentCell = null;
        path.add(new CellAuxiliar(maze[currentY][currentX], currentY, currentX, euclideanDistance(currentX, currentY, exitX, exitY), maze[currentY][currentX].applyOperation(points)));  // Afegim acasella inicial
        while (exitX != currentX || exitY != currentY)
        {
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

            int numberOfNeighbours = neighbours.size();  // guardem el nombre de veins en una variable fixa
            List<CellAuxiliar> sortedByDistance = new ArrayList<>();
            double maximum = Integer.MIN_VALUE;  // Minim valor
            int pos = -1;  // posicio del seguent element
            for (int i = 0; i < numberOfNeighbours; i++)
            {
                for (int j = 0; j < neighbours.size(); j++)
                {
                    if (neighbours.get(j).getDistance() > maximum)
                    {
                        maximum = neighbours.get(j).getDistance();
                        pos = j;
                    }
                }
                sortedByDistance.add(neighbours.remove(pos));
                maximum = Integer.MIN_VALUE;
            }


            boolean election = false;
            currentCell = null;
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
