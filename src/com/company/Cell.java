package com.company;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private int value;
    /*
     * Operation can be +, -, *, /, or N
     */
    private String operation;


    public Cell(String operation, int value)
    {
        this.value = value;
        this.operation = operation;
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
             return points + value;
        }
        else if (operation.equals("-"))
        {
            return points - value;
        }
        else if (operation.equals("*"))
        {
            return points * value;
        }
        else if (operation.equals("/"))
        {
            return points / value;
        }
        return points;
    }
}
