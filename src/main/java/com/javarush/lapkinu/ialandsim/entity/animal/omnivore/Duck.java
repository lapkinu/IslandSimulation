package com.javarush.lapkinu.ialandsim.entity.animal.omnivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.DUCK;

public class Duck extends Omnivore {

    public Duck(double weight, int speed) {
        super(DUCK.getId(),  weight, speed, DUCK.getImage());
    }
}
