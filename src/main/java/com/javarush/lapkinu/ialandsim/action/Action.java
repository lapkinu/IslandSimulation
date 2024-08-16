package com.javarush.lapkinu.ialandsim.action;

import com.javarush.lapkinu.ialandsim.entity.Entity;
import com.javarush.lapkinu.ialandsim.islandMap.MapManager;

public interface Action {
    void execute(MapManager mapManager, Entity entity);
}