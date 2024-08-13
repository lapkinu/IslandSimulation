package com.javarush.lapkinu.ialandsim.entity.animal.predator;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.FOX;

public class Fox extends Predator {

    public Fox(double weight, int speed) {
        super(FOX.getId(), weight, speed, FOX.getImage());
    }
}
