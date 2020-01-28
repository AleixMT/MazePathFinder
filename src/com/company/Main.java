package com.company;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	    Maze maze = new Maze();
	    System.out.println(maze.toString());
        List<CellAuxiliar> pathToExit = maze.greedy();
        System.out.println(pathToExit.toString());

    }
}
