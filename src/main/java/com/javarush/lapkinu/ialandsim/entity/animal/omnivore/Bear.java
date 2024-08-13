package com.javarush.lapkinu.ialandsim.entity.animal.omnivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.BEAR;

public class Bear extends Omnivore {

    public Bear(double weight, int speed) {
        super(BEAR.getId(),  weight, speed, BEAR.getImage());
    }
}
