package com.javarush.lapkinu.ialandsim.entity.animal.predator;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.BOA;

public class Boa extends Predator {

    public Boa(double weight, int speed) {
        super(BOA.getId(), weight, speed, BOA.getImage());
    }
}
