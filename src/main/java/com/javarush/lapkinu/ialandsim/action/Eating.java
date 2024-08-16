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
        List<Entity> animals = mapManager.getAnimalsInCell(cellX, cellY);
        for (Entity animal : animals) {
            Entity bestAnimal = EntityProperties.getBestAnimalTable(animal, animals);
            if (bestAnimal != null
                    && EntityProperties.valueTable(EntityProperties.formEntity(animal),
                    EntityProperties.formEntity(bestAnimal)) > 0
                    && animal.getAlive()) {
                mapManager.removeAnimal(bestAnimal);
                animal.eat();
                System.out.println(animal.getClass().getSimpleName() + " ID " + animal.getID()
                        + " съел " + bestAnimal.getClass().getSimpleName() + " ID " + bestAnimal.getID());
            }
        }
    }

}
