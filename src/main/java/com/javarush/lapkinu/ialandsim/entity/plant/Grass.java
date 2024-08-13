package com.javarush.lapkinu.ialandsim.entity.plant;

public class Grass {
    private static int grassCountID = 1;
    private final int id;
    private int pointX;
    private int pointY;
    private double height;
    private final double heightMax;
    public Grass() {
        this.pointX = 0;
        this.pointY = 0;
        this.height = 20;
        this.heightMax = height;
        this.id = grassCountID;
        grassCountID++;
    }
    public Grass(int pointX, int pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.height = 20;
        this.heightMax = height;
        this.id = grassCountID;
        grassCountID++;
    }
    public Grass(int pointX, int pointY, double height) {
        this.pointX = pointX;
        this.pointY = pointY;
        this.height = height;
        this.heightMax = height;
        this.id = grassCountID;
        grassCountID++;
    }

    public int getID() {
        return id;
    }
    public int getPointX() {
        return pointX;
    }
    public void setPointX(int pointX) {
        this.pointX = pointX;
    }
    public int getPointY() {
        return pointY;
    }
    public void setPointY(int pointY) {
        this.pointY = pointY;
    }
    private void setHeight(double height) {
        this.height = height;
    }
    public double getHeight() {
        return height;
    }
    public void cut() {
        //if...
        setHeight(getHeight() - 3);
    }

    @Override
    public String toString() {
        return print();
    }
    public String print() {
        String format = "%s ID:%d высота:%.1f";
        return String.format(format,
                this.getClass().getSimpleName(), getID(), this.getHeight());
    }
}
