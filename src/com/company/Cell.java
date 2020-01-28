package com.company;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int value;
    /*
     * Operation can be +, -, *, /, or N
     */
    private String operation;
    private int posX;
    private int posY;

    public Cell(String operation, int value, int x, int y)
    {
        this.value = value;
        this.operation = operation;
        this.posX = x;
        this.posY = y;
    }

    public int getValue() {
        return value;
    }

    public String getOperation() {
        return operation;
    }

    public int applyOperation(int points)
    {
        if (operation.equals("+"))
        {
            points += value;
        }
        else if (operation.equals("-"))
        {
            points -= value;
        }
        else if (operation.equals("*"))
        {
            points *= value;
        }
        else if (operation.equals("/"))
        {
            points /= value;
        }
        return points;
    }
}
