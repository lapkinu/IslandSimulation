// Обновленный метод updatePosition в классе PositionManager
package com.javarush.lapkinu.ialandsim.islandMap;

import com.javarush.lapkinu.ialandsim.entity.Entity;

public class PositionManager {
    private static double getRandomOffset(int speed) {
        return (Math.random() * speed * 2) - speed; // Смещение в диапазоне в одном направлении от 1 до +speed

    }

    public static void updatePosition(Entity entity, int fieldWidth, int fieldHeight) {
        int newX, newY;
        do {
            newX = (int) (entity.getStartX() + getRandomOffset(entity.getSpeed()));
            newY = (int) (entity.getStartY() + getRandomOffset(entity.getSpeed()));
        } while (newX < 0 || newX >= fieldWidth || newY < 0 || newY >= fieldHeight); // Проверка границ поля

        entity.setEndX(newX);
        entity.setEndY(newY);
    }
}