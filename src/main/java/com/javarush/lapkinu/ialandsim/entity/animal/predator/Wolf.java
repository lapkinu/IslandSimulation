package com.javarush.lapkinu.ialandsim.entity.animal.predator;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.WOLF;

public class Wolf extends Predator {

    public Wolf(double weight, int speed) {
        super(WOLF.getId(), weight, speed, WOLF.getImage());
    }
}
