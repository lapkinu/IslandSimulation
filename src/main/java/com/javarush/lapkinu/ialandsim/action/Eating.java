package com.javarush.lapkinu.ialandsim.action;

import com.javarush.lapkinu.ialandsim.animalTable.EntityProperties;
import com.javarush.lapkinu.ialandsim.entity.Entity;
import com.javarush.lapkinu.ialandsim.islandMap.MapManager;

import java.util.List;

public class Eating  implements Action {


    @Override
    public void execute(MapManager mapManager, Entity entity) {
        int cellX = mapManager.getCellX(entity);
        int cellY = mapManager.getCellY(entity);
        List<Entity> ListEntity = mapManager.getAnimalsInCell(cellX, cellY);
        for (Entity hunterEntity : ListEntity) {
            Entity bestEntity = EntityProperties.getBestAnimalTable(hunterEntity, ListEntity);
            if (bestEntity != null
                    && EntityProperties.valueTable(EntityProperties.formEntity(hunterEntity),
                    EntityProperties.formEntity(bestEntity)) > 0
                    && hunterEntity.getAlive()) {


                if (EntityProperties.formEntity(bestEntity) == EntityProperties.PLANTS) {
                    bestEntity.hunger();
                    hunterEntity.eat();
                } else {
                    //съест с вероятностью EntityProperties.valueTable
                    if (Math.random() < EntityProperties.valueTable(EntityProperties.formEntity(hunterEntity),
                            EntityProperties.formEntity(bestEntity))) {
                        mapManager.removeAnimal(bestEntity);
                        hunterEntity.eat();
                    }
                }


                System.out.println(hunterEntity.getClass().getSimpleName() + " ID " + hunterEntity.getID()
                        + " съел " + bestEntity.getClass().getSimpleName() + " ID " + bestEntity.getID());
            }
        }
    }

}
