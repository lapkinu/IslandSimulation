package com.javarush.lapkinu.ialandsim.islandMap;


public class Test {
    static int speed = 1;
    static int fieldWidth = 10;
    static int fieldHeight = 10;
    static double startX;
    static double startY;
    static double currentX;
    static double currentY;

    public static void main(String[] args) {
        startX = 10;
        startY = 10;
        updatePosition(startX, startY, fieldWidth, fieldHeight);
        for (int i = 0; i < 100; i++) {
            updatePosition(currentX, currentY, fieldWidth, fieldHeight);
        }
    }

    private static double getRandomOffset(int speed) {
        return (Math.random() * speed * 2) - speed;
    }

    public static void updatePosition(double startX, double startY, int fieldWidth, int fieldHeight) {
        int newX, newY;
        do {
            newX = (int) Math.round(startX + getRandomOffset(speed));
            newY = (int) Math.round(startY + getRandomOffset(speed));
        } while (newX < 0 || newX >= fieldWidth || newY < 0 || newY >= fieldHeight);
        System.out.println("x - " + newX + "  y - " + newY);
        currentX = newX;
        currentY = newY;
    }
}
