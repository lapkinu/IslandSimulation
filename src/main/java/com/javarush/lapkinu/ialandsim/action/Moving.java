package com.javarush.lapkinu.ialandsim.action;

import com.javarush.lapkinu.ialandsim.entity.Entity;
import com.javarush.lapkinu.ialandsim.islandMap.MapManager;


public  class Moving implements Runnable {
    private final MapManager mapManager;
    private final Entity entity;

    public Moving(MapManager mapManager, Entity entity) {
        this.mapManager = mapManager;
        this.entity = entity;
    }

    @Override
    public void run() {
        entity.updatePosition(mapManager.getWidth(), mapManager.getHeight());
        mapManager.moveAnimal(entity, (int) entity.getEndX(), (int) entity.getEndY());
        if (entity.getWeight() <= (entity.getWeightMax() * 0.15)) {
            mapManager.removeAnimal(entity);
        } else {
            entity.hunger();
        }
    }

}
