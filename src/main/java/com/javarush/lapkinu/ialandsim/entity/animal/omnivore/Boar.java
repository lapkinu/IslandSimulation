package com.javarush.lapkinu.ialandsim.entity.animal.omnivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.BOAR;

public class Boar extends Omnivore {

    public Boar(double weight, int speed) {
        super(BOAR.getId(),  weight, speed, BOAR.getImage());
    }
}
