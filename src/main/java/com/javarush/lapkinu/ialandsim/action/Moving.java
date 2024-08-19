package com.javarush.lapkinu.ialandsim.action;

import com.javarush.lapkinu.ialandsim.entity.Entity;
import com.javarush.lapkinu.ialandsim.islandMap.MapManager;

public  class Moving implements Action {

    @Override
    public void execute(MapManager mapManager, Entity entity) {
        entity.updatePosition(mapManager.getWidth(), mapManager.getHeight());
        mapManager.moveAnimal(entity, (int) entity.getEndX(), (int) entity.getEndY());
        if (entity.getWeight() <= (entity.getWeightMax() * 0.15)) {
            mapManager.removeAnimal(entity);
        } else {
            entity.hunger();
        }
    }

}
