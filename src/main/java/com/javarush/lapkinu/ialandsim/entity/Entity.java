package com.javarush.lapkinu.ialandsim.entity;

import java.awt.*;

public interface Entity {
    int getID();

    void setWeight(double weight);

    double getWeight();

    int getSpeed();

    double getWeightMax();

    boolean getAlive();

    void setAlive(boolean alive);

    void hunger();

    void eat();

    void move();

    void reproduce();

    Image getImage();

    double getStartX();

    void setStartX(double startX);

    double getStartY();

    void setStartY(double startY);

    double getEndX();

    void setEndX(double endX);

    double getEndY();

    void setEndY(double endY);

    double getCurrentX();

    void setCurrentX(double currentX);

    double getCurrentY();

    void setCurrentY(double currentY);

    void updatePosition(int fieldWidth, int fieldHeight);

    String print();

    void setPrevX(double endX);

    void setPrevY(double endY);

    double getPrevX();

    double getPrevY();

}
