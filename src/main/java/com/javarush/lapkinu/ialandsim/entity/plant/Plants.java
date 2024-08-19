package com.javarush.lapkinu.ialandsim.entity.plant;

import com.javarush.lapkinu.ialandsim.entity.animal.herbivore.Herbivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.PLANTS;

public class Plants extends Herbivore {

    public Plants(double weight, int speed) {
        super(PLANTS.getId(), weight, speed, PLANTS.getImage());
    }
}
