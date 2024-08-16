package com.javarush.lapkinu.ialandsim.action;

import com.javarush.lapkinu.ialandsim.entity.Entity;
import com.javarush.lapkinu.ialandsim.factory.EntityFactory;
import com.javarush.lapkinu.ialandsim.islandMap.MapManager;

import java.util.List;

public class Reproduction implements Action {

    @Override
    public void execute(MapManager mapManager, Entity entity) {
        int cellX = mapManager.getCellX(entity);
        int cellY = mapManager.getCellY(entity);
        List<Entity> animals = mapManager.getAnimalsInCell(cellX, cellY);
        if (animals.size() >= 2 && !isCellOverpopulated(mapManager, cellX, cellY)) {
            for (Entity animal : animals) {
                if (isContainsEntity(animals, animal)) {
                    Entity newEntity = EntityFactory.createEntity(animal);
                    if (cellX != -1 && cellY != -1) {
                        mapManager.addAnimalToCell(newEntity, cellX, cellY);
                        newEntity.setStartX(cellX);
                        newEntity.setStartY(cellY);
                        newEntity.setEndX(cellX);
                        newEntity.setEndY(cellY);
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

    public boolean isCellOverpopulated(MapManager mapManager, int cellX, int cellY) {
        return mapManager.getAnimalsInCell(cellX, cellY).size() > 2;
    }
}