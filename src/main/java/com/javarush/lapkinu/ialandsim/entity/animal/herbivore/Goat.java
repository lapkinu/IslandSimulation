package com.javarush.lapkinu.ialandsim.entity.animal.herbivore;

import static com.javarush.lapkinu.ialandsim.animalTable.EntityProperties.GOAT;

public class Goat extends Herbivore  {

    public Goat(double weight, int speed) {
        super(GOAT.getId(), weight, speed, GOAT.getImage());
    }
}
