package com.javarush.lapkinu.ialandsim.action;

import com.javarush.lapkinu.ialandsim.animalTable.EntityProperties;
import com.javarush.lapkinu.ialandsim.entity.Entity;
import com.javarush.lapkinu.ialandsim.factory.EntityFactory;
import com.javarush.lapkinu.ialandsim.islandMap.MapManager;

import java.util.List;

public class Reproduction implements Runnable {

    private final MapManager mapManager;
    private final int cellX;
    private final int cellY;

    public Reproduction(MapManager mapManager, int cellX, int cellY) {
        this.mapManager = mapManager;
        this.cellX = cellX;
        this.cellY = cellY;
    }

    @Override
    public void run() {
        List<Entity> animals = mapManager.getAnimalsInCell(cellX, cellY);
        if (animals.size() >= 2 && !isCellOverpopulated(cellX, cellY)) {
            for (Entity animal : animals) {
                if (isContainsEntity(animals, animal)) {
                    Entity newEntity = EntityFactory.createEntity(animal);
                    int x = mapManager.getCellX(animal);
                    int y = mapManager.getCellY(animal);
                    if (cellX != -1 && cellY != -1) {
                        mapManager.addAnimalToCell(newEntity, x, y);
                        newEntity.setStartX(x);
                        newEntity.setStartY(y);
                        newEntity.setEndX(x);
                        newEntity.setEndY(y);
                        System.out.println("Родился новый житель: " + newEntity.getClass().getSimpleName() + " ID " + newEntity.getID());
                    }
                }
            }
        }
    }

    private boolean isContainsEntity(List<Entity> animals, Entity entity) {
        for (Entity animal : animals) {
            if (animal.getClass().getSimpleName().equals(entity.getClass().getSimpleName())
                    && animal.getID() != entity.getID()) {
                return true;
            }
        }
        return false;
    }


    // метод: если в клетке больше 30 то вернуть false
    public boolean isCellOverpopulated(int cellX, int cellY) {
        return mapManager.getAnimalsInCell(cellX, cellY).size() > 2;
    }


}
