package com.javarush.lapkinu.ialandsim.entity.animal.herbivore;

import com.javarush.lapkinu.ialandsim.entity.Entity;
import com.javarush.lapkinu.ialandsim.islandMap.PositionManager;

import java.awt.*;

public abstract class Herbivore implements Entity {
    private final int id;
    private double weight;
    private final double weightMax;
    private final int speed;
    private boolean alive = true;
    private final Image image;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private double currentX;
    private double currentY;
    private double prevX;
    private double prevY;

    protected Herbivore(int id, double weightMax, int speed, Image image) {
        this.id = id;
        this.weightMax = weightMax;
        this.speed = speed;
        this.weight = weightMax;
        this.image = image;
    }

    public double getPrevX() {
        return prevX;
    }

    public void setPrevX(double prevX) {
        this.prevX = prevX;
    }

    public double getPrevY() {
        return prevY;
    }

    public void setPrevY(double prevY) {
        this.prevY = prevY;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public double getWeightMax() {
        return weightMax;
    }

    @Override
    public boolean getAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void eat() {
        if (getWeight() < getWeightMax()) {
            setWeight(getWeight() + (getWeightMax() * 0.1));
        }
    }

    @Override
    public void hunger() {
        setWeight(getWeight() - (getWeightMax() * 0.02));
    }


    @Override
    public void move() {
    }

    @Override
    public void reproduce() {
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public double getStartX() {
        return startX;
    }

    @Override
    public void setStartX(double startX) {
        this.startX = startX;
        this.currentX = startX;
    }

    @Override
    public double getStartY() {
        return startY;
    }

    @Override
    public void setStartY(double startY) {
        this.startY = startY;
        this.currentY = startY;
    }

    @Override
    public double getEndX() {
        return endX;
    }

    @Override
    public void setEndX(double endX) {
        this.endX = endX;
    }

    @Override
    public double getEndY() {
        return endY;
    }

    @Override
    public void setEndY(double endY) {
        this.endY = endY;
    }

    @Override
    public double getCurrentX() {
        return currentX;
    }

    @Override
    public void setCurrentX(double currentX) {
        this.currentX = currentX;
    }

    @Override
    public double getCurrentY() {
        return currentY;
    }

    @Override
    public void setCurrentY(double currentY) {
        this.currentY = currentY;
    }

    @Override
    public void updatePosition(int fieldWidth, int fieldHeight) {
        PositionManager.updatePosition(this, fieldWidth, fieldHeight);
    }

    @Override
    public String toString() {
        return print();
    }

    @Override
    public String print() {
        String format = "|%s ID:%d вес:%.1f скорость:%d %b|";
        return String.format(format,
                this.getClass().getSimpleName(),
                this.getID(),
                this.getWeight(),
                this.getSpeed(),
                this.getAlive()
        );
    }

}