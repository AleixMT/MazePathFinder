package com.company;

public class Cell {
    private int value;
    /*
     * Operation can be +, -, *, /, or N
     */
    private char operation;

    public Cell(char operation, int value)
    {
        this.value = value;
        this.operation = operation;
    }

    public int getValue() {
        return value;
    }

    public char getOperation() {
        return operation;
    }
}
