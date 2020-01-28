package com.company;

public class CellAuxiliar {
    private Cell cell;
    private int posX;
    private int posY;
    private double distance;
    private int possiblePoints;

    public CellAuxiliar(Cell cell, int posY, int posX)
    {
        this.posX = posX;
        this.posY = posY;
        this.cell = cell;
    }

    public CellAuxiliar(Cell cell, int posY, int posX, double distance, int possiblePoints) {
        this.cell = cell;
        this.posX = posX;
        this.posY = posY;
        this.distance = distance;
        this.possiblePoints = possiblePoints;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getPossiblePoints() {
        return possiblePoints;
    }

    public void setPossiblePoints(int possiblePoints) {
        this.possiblePoints = possiblePoints;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public String toString() {
        return "cell" + cell +
                ", (" + posY +
                ", " + posX + ")" +
                ", distance=" + distance +
                ", possiblePoints=" + possiblePoints + "\n";
    }
}
