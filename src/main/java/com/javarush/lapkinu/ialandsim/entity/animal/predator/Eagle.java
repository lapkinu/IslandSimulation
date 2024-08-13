package com.javarush.lapkinu.ialandsim.entity.animal.predator;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.EAGLE;

public class Eagle extends Predator {

    public Eagle(double weight, int speed) {
        super(EAGLE.getId(), weight, speed, EAGLE.getImage());
    }
}
